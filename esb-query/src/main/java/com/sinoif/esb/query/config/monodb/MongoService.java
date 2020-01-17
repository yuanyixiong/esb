package com.sinoif.esb.query.config.monodb;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.*;
import com.mongodb.client.model.BsonField;
import com.sinoif.esb.constants.PortalConstants;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Accumulators.first;
import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.exclude;
import static com.mongodb.client.model.Projections.fields;


/**
 * @author 袁毅雄
 * @description MongoDb 基础操作
 * @date 2019/11/1
 */
@Component
public class MongoService {

    private final Logger logger = Logger.getLogger(getClass());

    private final Pattern operPattern = Pattern.compile("(>=|<=|=|>|<|between|like|leftLike|rightLike)+");

    @Autowired
    private MongoDatabase mongoDatabase;

    /**
     * 瀑布流分页
     *
     * @param topic      Collection名称
     * @param params     条件参数
     * @param pagingSize 每页显示的数据条数
     * @param asc        瀑布流分页顺序
     * @return 返回查询json
     */
    public Object[] queryByCondition(String topic, HashMap<String, Object> params,
                                     int pagingSize, int pageNo, boolean asc) {
        List<Bson> conditionList = buildCondition(params);
        FindIterable<Document> findIterable;
        MongoCollection collection = mongoDatabase.getCollection(topic);
        long totalCounts = 0l;
        if (conditionList.size() > 0) {
            findIterable = collection.find(and(conditionList));
            totalCounts = collection.countDocuments(and(conditionList));
        } else {
            findIterable = collection.find();
            totalCounts = collection.countDocuments();
        }
        findIterable = findIterable.skip((pageNo - 1) * pagingSize).limit(pagingSize);
        List<Document> documents = new ArrayList<>();
        for (Document document : findIterable) {
            documents.add(document);
        }
        return new Object[]{JSON.toJSONString(documents), pageNo, totalCounts};
    }

    /**
     * 聚合查询
     *
     * @param topic      topic
     * @param params     查询参数
     * @param groupMap   需要聚合的字段
     * @param pagingSize 分页数量
     * @param asc        升、降序
     * @param lastKey    最大分页key
     * @param lastValue  分页key的值
     * @return 查询结果（json）
     */
    public Object[] aggregateQuery(String topic, String groupKey, HashMap<String, Object> params,
                                   HashMap<String, String> groupMap, int pageNo, int pagingSize, String countField) {
        ArrayList<Document> documents = new ArrayList<>();
        ArrayList<Bson> bsons = new ArrayList<>();
        if (params != null) {
            List<Bson> conditionList = buildCondition(params);
            bsons.add(match(and(conditionList)));
        }
        bsons.add(group("$" + groupKey, buildGroupCondition(groupMap, countField)));
        bsons.add(project(fields(exclude("_id"))));
        Block<Document> printBlock = document -> {
            System.out.println(document.toJson());
            documents.add(document);
        };
        mongoDatabase.getCollection(topic).aggregate(
                bsons
        ).forEach(printBlock);
        return pageable(documents, pageNo, pagingSize);
    }

    /**
     * 构建查询的 BsonFields
     *
     * @param groupFields 聚合字段
     * @param countField 计数字段
     * @return BsonField数组
     */
    private BsonField[] buildGroupCondition(HashMap<String, String> groupFields, String countField) {
        List<BsonField> lists = groupFields.keySet().stream().map(s -> first(s, "$" + s)).collect(Collectors.toList());
        lists.add(sum("cnt", countField == null ? 1 : "$" + countField));
        BsonField[] fields = new BsonField[lists.size()];
        lists.toArray(fields);
        return fields;
    }


    /**
     * 构建查询条件
     *
     * @param params
     * @return
     */
    private List<Bson> buildCondition(HashMap<String, Object> params) {
        final Bson[] condition = new Bson[1];
        List<Bson> conditionList = new ArrayList<>();
        params.forEach((k, v) -> {
            Object[] conditionValue = getConditionValue(k, v);
            switch (conditionValue[0].toString()) {
                case "<":
                    condition[0] = lt(k, conditionValue[1]);
                    break;
                case "<=":
                    condition[0] = lte(k, conditionValue[1]);
                    break;
                case ">":
                    condition[0] = gt(k, conditionValue[1]);
                    break;
                case ">=":
                    condition[0] = gte(k, conditionValue[1]);
                    break;
                case "=":
                    condition[0] = eq(k, conditionValue[1]);
                    break;
                case "between":
                    String[] between = conditionValue[1].toString().split("and");
                    condition[0] = and(gt(k, between[0].trim()), lte(k, between[1].trim()));
                    break;
                case "like":
                    condition[0] = regex(k, Pattern.compile(String.format("^.*%s.*$", conditionValue[1]), Pattern.CASE_INSENSITIVE));
                    break;
                case "leftLike":
                    condition[0] = regex(k, Pattern.compile(String.format("^.*%s$", conditionValue[1]), Pattern.CASE_INSENSITIVE));
                    break;
                case "rightLike":
                    condition[0] = regex(k, Pattern.compile(String.format("^%s.*$", conditionValue[1]), Pattern.CASE_INSENSITIVE));
                    break;
            }
            conditionList.add(condition[0]);
        });
        return conditionList;
    }

    /**
     * 解析表达式，提取操作符与数值
     *
     * @param expression 表达式
     * @return 数据 0：操作符 1：值
     */
    private Object[] getConditionValue(String key, Object expression) {
        Matcher operMathcer = operPattern.matcher(expression.toString());
        String oper = null;
        String value = null;
        if (operMathcer.find()) {
            oper = operMathcer.group();
        }
        value = expression.toString().replace(oper, "");
        if ("_id".equals(key.trim())) {
            return new Object[]{oper.trim(), Long.parseLong(value.trim())};
        } else {
            return new Object[]{oper.trim(), value.trim()};
        }
    }

    /**
     * 保存或修改
     *
     * @param topic
     * @param json
     * @param keyProperty
     * @param keyValue
     */
    public void mongoDbSaveOrUpdate(String topic, String json, String keyProperty, String keyValue) {
        if (json.startsWith("[")) {
            logger.error("SaveOrUpdate 只能操作一个对象");
            return;
        }
        MongoCollection collection = mongoDatabase.getCollection(topic);
        Document doc = Document.parse(json);
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.append(keyProperty, keyValue);
        Object result = collection.findOneAndReplace(searchQuery, doc);
        if (result == null) {
            collection.insertOne(doc);
        } else {
            Map<String, String> data = JSON.parseObject(json, Map.class);
            BasicDBObject updateFields = new BasicDBObject();
            data.entrySet().forEach(e -> updateFields.append(e.getKey(), e.getValue()));
            BasicDBObject setQuery = new BasicDBObject();
            setQuery.append("$set", updateFields);
            collection.updateOne(searchQuery, setQuery);
        }
    }

    public Object[] queryInvokeStatistic(List<String> ids, int pageNo, int pageSize,String begin,String end) {
        Document filter1 = new Document("$match", new Document("interface_id", new Document("$in", ids)));
        Document filter2 = new Document("$match", new Document("invoke_time", new Document("$gt", begin)));
        Document filter3 = new Document("$match", new Document("invoke_time", new Document("$lt", end)));
        Document firstGroup = new Document("$group",
                new Document("_id",
                        new Document("interfaceId", "$interface_id")
                                .append("status", "$response_status")).append("sum", new Document("$sum", 1)
                ).append("interfaceName", new Document("$first", "$interface_name")));
        List<Document> pipeline = Arrays.asList(filter1, filter2, filter3, firstGroup);
        AggregateIterable<Document> cursor = mongoDatabase.getCollection(PortalConstants.COLLECTION_INVOKE_LOG).aggregate(pipeline);
        ArrayList<Document> documents = new ArrayList<>();
        for (Document document : cursor) {
            documents.add(document);
        }
        return pageable(documents, pageNo, pageSize);
    }

    /**
     * 分页
     *
     * @param documents 要分页的查询结果
     * @param pageNo 页数
     * @param pagingSize 单数记录数量
     * @return 返回查询结果
     */
    private Object[] pageable(ArrayList<Document> documents, int pageNo, int pagingSize) {
        if ((pageNo - 1) * pagingSize >= documents.size()) {
            return new Object[]{"[]", 0, 0};
        } else {
            return new Object[]{JSON.toJSONString(documents.subList((pageNo - 1) * pagingSize, Math.min(documents.size(), pageNo * pagingSize)),
                    SerializerFeature.WriteNonStringKeyAsString
            ), (long) documents.size(), pageNo};
        }
    }


    /**
     * 单独对以上的查询方法进行测试
     *
     * @param args
     */
    public static void main(String args[]) {
        MongoCredential credential = MongoCredential.createCredential("root", "admin", "example".toCharArray());
        MongoClientSettings settings = MongoClientSettings.builder()
                .credential(credential)
                .applyToClusterSettings(builder ->
                        builder.hosts(Arrays.asList(new ServerAddress("localhost", 27017))))
                .applyToSocketSettings(builder -> builder.connectTimeout(60, TimeUnit.SECONDS))
                .applyToConnectionPoolSettings(builder -> builder.maxConnectionIdleTime(60, TimeUnit.SECONDS)
                        .maxConnectionLifeTime(60, TimeUnit.SECONDS))
                .build();
        MongoClient client = MongoClients.create(settings);

        Block<Document> printBlock = new Block<Document>() {
            @Override
            public void apply(final Document document) {
                System.out.println(document.toJson());
            }
        };

//        client.getDatabase("esb")
//                .getCollection(PortalConstants.COLLECTION_INVOKE_LOG).aggregate(
//                Arrays.asList(
//                        match(Filters.eq("handled", "false")),
//                        group("$interface_name", first("inputSystem", "$input_system"),
//                                first("time", "$_time"),
//                                sum("cnt", 1), first("interface_name", "$interface_name")),
//                        project(fields(exclude("_id")))
//                )
//        ).forEach(printBlock);

//        FindIterable<Document> findIterable= client.getDatabase("esb").getCollection(PortalConstants.COLLECTION_INVOKE_LOG_DATA+"_sinoif_org_list").find(
//                and(eq("_interface_id","3"))
//        );
//        for (Document d : findIterable) {
//            System.out.println(">>>>>>" + d);
//        }


        ArrayList<String> ids = new ArrayList<>();
        ids.add("3");
        ids.add("2061");
        Document filter1 = new Document("$match", new Document("interface_id", new Document("$in", ids)));
        Document filter2 = new Document("$match", new Document("invoke_time", new Document("$gt", "2019")));
        Document filter3 = new Document("$match", new Document("invoke_time", new Document("$lt", "2020")));

        Document firstGroup = new Document("$group",
                new Document("_id",
                        new Document("interface", "$interface_id")
                                .append("status", "$response_status"))
                        .append("sum", new Document("$sum", 1))
                        .append("interfaceName", new Document("$first", "$interface_name"))
        );
        List<Document> pipeline = Arrays.asList(filter1, filter2, filter3, firstGroup);
        AggregateIterable<Document> cursor = client.getDatabase("esb").getCollection(PortalConstants.COLLECTION_INVOKE_LOG).aggregate(pipeline);

        for (Document doc : cursor) {
            System.out.println(">>>>>>>>>>>>>>>:" + doc);
        }
    }

}
