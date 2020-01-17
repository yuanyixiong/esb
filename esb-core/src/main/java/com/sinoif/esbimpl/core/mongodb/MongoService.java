package com.sinoif.esbimpl.core.mongodb;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.*;
import com.mongodb.client.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.Sorts;
import com.sinoif.esb.constants.CoreConstants;
import com.sinoif.esb.constants.PortalConstants;
import com.sinoif.esb.port.bean.Interface;
import com.sinoif.esb.port.bean.InvokeResult;
import com.sinoif.esb.utils.sequence.SequenceUtil;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.mongodb.client.model.Filters.*;

/**
 * mongodb服务，esb 集成了core模块对mongodb的所有操作
 */
@Component
public class MongoService {
    Logger logger = Logger.getLogger(MongoService.class);
    @Autowired
    private MongoDatabase mongoDatabase;

    Pattern contionPattern = Pattern.compile("(or|and)+");
    Pattern operPattern = Pattern.compile("(>=|<=|=|>|<|between|like|leftLike|rightLike)+");
    Pattern valuePatter = Pattern.compile("(\\w|\\d)+");
    SimpleDateFormat sdf = new SimpleDateFormat(CoreConstants.DATETIME_FORMAT);

    /**
     * 接条件在mongodb查询业务数据
     *
     * @param topic  Collection名称
     * @param params 参数列表 key = 参数名， value 表达式
     * @return 返回查询json
     */
    public String queryByCondition(String topic, HashMap<String, String> params) throws Exception {
        List<Bson> conditionList = buildCondition(params);
        FindIterable<Document> findIterable;
        if (conditionList.size() == 0) {
            findIterable = mongoDatabase.getCollection(topic).find();
        } else {
            findIterable = mongoDatabase.getCollection(topic).find(and(conditionList));
        }
        List<Document> documents = new ArrayList<>();
        for (Document document : findIterable) {
            documents.add(document);
        }
        return JSON.toJSONString(documents);
    }

    /**
     * 查询待审核数据列表
     *
     * @param topic    接口topic
     * @param params   参数
     * @param pageSize 分页记录数量
     * @param lastKey  上一页的最后的id
     * @return 接口数据
     */
    public String queryApproveData(String topic, HashMap<String, String> params, int pageSize, long lastKey) throws Exception {
        params.put("_id", ">" + lastKey);
        List<Bson> conditionList = buildCondition(params);
        BasicDBObject sortBson = new BasicDBObject();
        sortBson.put("_id", -1);
        FindIterable<Document> findIterable;
        findIterable = mongoDatabase.getCollection(topic).find(and(conditionList)).sort(sortBson).limit(pageSize);
        List<Document> documents = new ArrayList<>();
        for (Document document : findIterable) {
            documents.add(document);
        }
        return JSON.toJSONString(documents);
    }

    /**
     * 瀑布流分页
     *
     * @param topic      Collection名称
     * @param params     条件参数
     * @param pagingSize 每页显示的数据条数
     * @param asc        瀑布流分页顺序
     * @param lastKey    瀑布流分页的列
     * @param lastValue  瀑布流分页的值
     * @return 返回查询json
     */
    public String queryByCondition(String topic, HashMap<String, String> params, Integer pagingSize, boolean asc,
                                   String lastKey, String lastValue) throws Exception {
        List<Bson> conditionList = buildCondition(params);
        if (!StringUtils.isEmpty(lastValue)) {
            if (asc) {
                conditionList.add(gt(lastKey, lastValue));
            } else {
                conditionList.add(lt(lastKey, lastValue));
            }
        }
        long totalCounts = 0;
        FindIterable<Document> findIterable;
        MongoCollection collection = mongoDatabase.getCollection(topic);
        if (conditionList.size() == 0) {
            findIterable = collection
                    .find()
                    .sort(asc ?
                            Sorts.ascending(lastKey) :
                            Sorts.descending(lastKey)
                    )
                    .limit(pagingSize);
            totalCounts = collection.countDocuments();
        } else {
            findIterable = collection
                    .find(and(conditionList))
                    .sort(asc ?
                            Sorts.ascending(lastKey) :
                            Sorts.descending(lastKey)
                    )
                    .limit(pagingSize);
            totalCounts = collection.countDocuments(and(conditionList));
        }
        List<Document> documents = new ArrayList<>();
        for (Document document : findIterable) {
            documents.add(document);
        }
        return JSON.toJSONString(documents);
    }

    /**
     * 构建查询条件
     *
     * @param params
     * @return
     */
    private List<Bson> buildCondition(HashMap<String, String> params) throws Exception {
        final Bson[] condition = new Bson[1];
        List<Bson> conditionList = new ArrayList<>();
//        params.forEach((k, v) -> {
        for (Map.Entry<String, String> ent : params.entrySet()) {
            String k = ent.getKey();
            String v = ent.getValue();
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
                    //TODO 正则有点问题需要处理
                    condition[0] = regex(k, Pattern.compile(String.format("^.*%s.*$", conditionValue[1]), Pattern.CASE_INSENSITIVE));
                    break;
                case "leftLike":
                    //TODO 正则有点问题需要处理
                    condition[0] = regex(k, Pattern.compile(String.format("^.*%s$", conditionValue[1]), Pattern.CASE_INSENSITIVE));
                    break;
                case "rightLike":
                    //TODO 正则有点问题需要处理
                    condition[0] = regex(k, Pattern.compile(String.format("^%s.*$", conditionValue[1]), Pattern.CASE_INSENSITIVE));
                    break;
            }
            conditionList.add(condition[0]);
        }
        return conditionList;
    }

    /**
     * 解析表达式，提取操作符与数值
     *
     * @param expression 表达式
     * @return 数据 0：操作符 1：值
     */
    private Object[] getConditionValue(String key, String expression) throws Exception {
        try {
            Matcher operMathcer = operPattern.matcher(expression);
            String oper = null;
            String value = null;
            if (operMathcer.find()) {
                oper = operMathcer.group();
            }
//        if (Lists.newArrayList("like", "leftLike", "rightLike").contains(oper)) {
            value = expression.replace(oper, "");
//        } else {
//            Matcher valueMatcher = valuePatter.matcher(expression);
//            if (valueMatcher.find()) {
//                value = valueMatcher.group();
//            }
//        }
//            if("_id".equals(key.trim())){
//                return new Object[]{oper.trim(), Long.parseLong(value.trim())};
//            }else {
            return new Object[]{oper.trim(), value.trim()};
//            }
        } catch (Exception e) {
            throw new Exception("参数错误,支持的查询参数值为:(>=|<=|=|>|<|between|like|leftLike|rightLike)value");
        }
    }

    /**
     * 往mongodb中写入数据
     *
     * @param topic 接口消息对映的topic, topic 通常是mongodb的表名，或者是表名的一部份
     * @param jsonData 要保存mongodb中的数据
     * @param keyProperty 数据的id属性的名称
     * @param parentId 父表的id, 在异常明细表记录指向异常主表
     * @param time 数据插入时间
     * @param extraField 除jsonData中的字段外，附加的字段名称值（变参数的总长度为偶数，厅数位是属性名称，偶数位为奇数属性对应的值）
     * @return 写入执行结果
     */
    public InvokeResult saveData(String topic, String jsonData, String keyProperty, long parentId, String time, Object... extraField) {
        try {
            MongoCollection collection = mongoDatabase.getCollection(topic);
            if (jsonData.startsWith("\"")) {
                jsonData = jsonData.substring(1, jsonData.length() - 1);
                jsonData = jsonData.replace("\\", "");
            }
            if (jsonData.startsWith("[")) {
                JSONArray jsonArray = (JSONArray) JSONArray.parse(jsonData);
                Iterator iterator = jsonArray.iterator();
                while (iterator.hasNext()) {
                    JSONObject json = (JSONObject) iterator.next();
                    saveObject(json, collection, keyProperty, parentId, time, extraField);
                }
            } else {
                saveObject(JSONObject.parseObject(jsonData), collection, keyProperty, parentId, time, extraField);
            }
        } catch (Exception e) {
            if (e.getMessage().equals(PortalConstants.KEY_PROPERTY_ERROR)) {
                return InvokeResult.success(null, PortalConstants.KEY_PROPERTY_ERROR);
            } else {
                return InvokeResult.fail(null, e.getMessage());
            }
        }
        return InvokeResult.success(null, "success");
    }


    /**
     * 将接口数据存储
     *
     * @param json       接口数据
     * @param collection MongoCollection
     */
    private void saveObject(JSONObject json, MongoCollection collection, String keyProperty, long parentId, String time, Object... extraField) throws Exception {
        String keyValue;
        if(keyProperty==null||json.getString(keyProperty)==null){
            keyValue = SequenceUtil.getId()+"";
        }else{
            keyValue = json.getString(keyProperty);
        }
        json.put("_id", keyValue);
        json.put("_time", time == null ? sdf.format(new Date()) : time);
        try {
            Document doc = Document.parse(json.toString());
            doc.append("_id", keyProperty == null ? SequenceUtil.getId() : json.get(keyProperty));
            if (parentId > 0) {
                doc.append("_parent", parentId);
            } else {
                doc.append("_parent", "");
            }
            if (extraField.length > 0) {
                for (int i = 0; i < extraField.length; i = i + 2) {
                    doc.append(extraField[i].toString(), extraField[i + 1]);
                }
            }
            collection.insertOne(doc);
        } catch (MongoWriteException e) {
            logger.debug("write mondodb 错误：" + e.getMessage());
        }
    }

    /**
     * 获取一个collection（topic）中的(updateTimeField)例，的最大值
     * 在增量更新时会用到
     *
     * @param topic 表名
     * @param updateTimeField 排序字段
     * @return 最大值
     */
    public String getMaxDateOfFetchedData(String topic, String updateTimeField) {
        FindIterable findIterable = mongoDatabase.getCollection(topic).find().sort(new BasicDBObject(updateTimeField, -1)).limit(1);
        MongoCursor cursor = findIterable.iterator();
        if (cursor.hasNext()) {
            return ((Document) cursor.next()).getString(updateTimeField);
        } else {
            return null;
        }
    }

    /**
     * 更新mongo数据库记录值
     *
     * @param topic           collection名
     * @param updateData      key：要更新字段的名称；value: 要更新字段的值
     * @param queryConditions 查询条件， key 字段名； value 字段值
     */
    public long update(String topic, HashMap<String, String> updateData, HashMap<String, Object> queryConditions) {
        BasicDBObject updateFields = new BasicDBObject();
        updateData.forEach(updateFields::append);
        BasicDBObject setQuery = new BasicDBObject();
        setQuery.append("$set", updateFields);

        BasicDBObject searchQuery = new BasicDBObject();
        queryConditions.forEach((k, v) -> {
            searchQuery.append(k, v.toString());
        });
        return mongoDatabase.getCollection(topic).updateOne(searchQuery, setQuery).getMatchedCount();
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

    public void createCollectionAndIndex(Interface esbInterface) {
        if(esbInterface.getTopic()!=null && StringUtils.isNotEmpty(esbInterface.getKeyProperty())){
            mongoDatabase.getCollection(esbInterface.getTopic()).createIndex(Indexes.descending(esbInterface.getKeyProperty()));
        }
    }


    public static void main(String[] args) {
//        String p = "(>=|<=|=|>|<)+";
//        Pattern pattern = Pattern.compile(p);
//        Matcher matcher = pattern.matcher("=8");
//        if (matcher.find()) {
//            System.out.println(matcher.group());
//        }

//        MongoCredential credential = MongoCredential.createCredential("root", "admin", "example".toCharArray());
//        MongoClientSettings settings = MongoClientSettings.builder()
//                .credential(credential)
//                .applyToClusterSettings(builder ->
//                        builder.hosts(Arrays.asList(new ServerAddress("localhost", 27017))))
//                .applyToSocketSettings(builder -> builder.connectTimeout(60, TimeUnit.SECONDS))
//                .applyToConnectionPoolSettings(builder -> builder.maxConnectionIdleTime(60, TimeUnit.SECONDS)
//                        .maxConnectionLifeTime(60, TimeUnit.SECONDS))
//                .build();
//        MongoClient client =  MongoClients.create(settings);
//        FindIterable findIterable = client.getDatabase("esb").getCollection("esb-branch-query").find().sort(new BasicDBObject("ts",-1)).limit(1);
//        MongoCursor cursor = findIterable.iterator();
//        if(cursor.hasNext()){
//            System.out.println(">>>>>>"+((Document)cursor.next()));
//        }

        // 聚合查询
        MongoCredential credential = MongoCredential.createCredential("root", "admin", "example".toCharArray());
        MongoClientSettings settings = MongoClientSettings.builder()
                .credential(credential)
                .applyToClusterSettings(builder ->
                        builder.hosts(Arrays.asList(new ServerAddress("192.168.2.125", 27017))))
                .applyToSocketSettings(builder -> builder.connectTimeout(60, TimeUnit.SECONDS))
                .applyToConnectionPoolSettings(builder -> builder.maxConnectionIdleTime(60, TimeUnit.SECONDS)
                        .maxConnectionLifeTime(60, TimeUnit.SECONDS))
                .build();
        MongoClient client = MongoClients.create(settings);
        MongoCollection mongoCollection = client.getDatabase("esb").getCollection(PortalConstants.COLLECTION_INVOKE_LOG_DATA + "_comp_org");
        System.out.println(mongoCollection.countDocuments(eq("_interface_id", "2016")));
//        FindIterable<Document> findIterable= client.getDatabase("esb").getCollection(PortalConstants.COLLECTION_APPROVE_INFO).find(
//                eq("response_status","SUCCESS")
//        );
//        for (Document d : findIterable) {
//            System.out.println(">>>>>>" + d);
//        }

//        MongoCredential credential = MongoCredential.createCredential("root", "admin", "example".toCharArray());
//        MongoClientSettings settings = MongoClientSettings.builder()
//                .credential(credential)
//                .applyToClusterSettings(builder ->
//                        builder.hosts(Arrays.asList(new ServerAddress("localhost", 27017))))
//                .applyToSocketSettings(builder -> builder.connectTimeout(60, TimeUnit.SECONDS))
//                .applyToConnectionPoolSettings(builder -> builder.maxConnectionIdleTime(60, TimeUnit.SECONDS)
//                        .maxConnectionLifeTime(60, TimeUnit.SECONDS))
//                .build();
//        MongoClient client = MongoClients.create(settings);
//        MongoCollection mongoCollection = client.getDatabase("esb").getCollection("ttt");
//
//        MongoService mongoService = new MongoService();
//        JSONObject jsonObject = (JSONObject) JSONObject.parse("{\n" +
//                "  \"c1\": \"v1\",\n" +
//                "  \"c2\": \"v3\"\n" +
//                "}");
//        mongoService.saveObject(jsonObject, mongoCollection, "c1",-1);

//        MongoCredential credential = MongoCredential.createCredential("root", "admin", "example".toCharArray());
//        MongoClientSettings settings = MongoClientSettings.builder()
//                .credential(credential)
//                .applyToClusterSettings(builder ->
//                        builder.hosts(Arrays.asList(new ServerAddress("localhost", 27017))))
//                .applyToSocketSettings(builder -> builder.connectTimeout(60, TimeUnit.SECONDS))
//                .applyToConnectionPoolSettings(builder -> builder.maxConnectionIdleTime(60, TimeUnit.SECONDS)
//                        .maxConnectionLifeTime(60, TimeUnit.SECONDS))
//                .build();
//        MongoClient client = MongoClients.create(settings);
//        boolean find = client.getDatabase("esb").getCollection(PortalConstants.COLLECTION_INVOKE_EXCEPTION)
//                .find(and(eq("interface_id","2050"))).iterator().hasNext();
//        System.out.println(find);


//        MongoCredential credential = MongoCredential.createCredential("root", "admin", "example".toCharArray());
//        MongoClientSettings settings = MongoClientSettings.builder()
//                .credential(credential)
//                .applyToClusterSettings(builder ->
//                        builder.hosts(Arrays.asList(new ServerAddress("localhost", 27017))))
//                .applyToSocketSettings(builder -> builder.connectTimeout(60, TimeUnit.SECONDS))
//                .applyToConnectionPoolSettings(builder -> builder.maxConnectionIdleTime(60, TimeUnit.SECONDS)
//                        .maxConnectionLifeTime(60, TimeUnit.SECONDS))
//                .build();
//        MongoClient client = MongoClients.create(settings);
//        MongoCollection collation = client.getDatabase("esb").getCollection(PortalConstants.COLLECTION_INVOKE_EXCEPTION);
//
//        BasicDBObject updateFields = new BasicDBObject();
//        updateFields.append("_id","370287456941256704");
//        BasicDBObject setQuery = new BasicDBObject();
//        setQuery.append("$set", updateFields);
//
//        BasicDBObject searchQuery = new BasicDBObject();
//        queryConditions.forEach(searchQuery::append);
//        mongoDatabase.getCollection(topic).updateOne(searchQuery, setQuery);
    }
}
