package com.sinoif.esbimpl.port.config.monodb;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.mongodb.client.model.Filters.*;


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
     * @param lastKey    瀑布流分页的列
     * @param lastValue  瀑布流分页的值
     * @return 返回查询json
     */
    public String queryByCondition(String topic, HashMap<String, String> params, Integer pagingSize, boolean asc, String lastKey, String lastValue) {
        List<Bson> conditionList = buildCondition(params);
        if (!StringUtils.isEmpty(lastValue)) {
            if (asc) {
                conditionList.add(gt(lastKey, lastValue));
            } else {
                conditionList.add(lt(lastKey, lastValue));
            }
        }
        FindIterable<Document> findIterable;
        if (conditionList.size() == 0) {
            findIterable = mongoDatabase.getCollection(topic)
                    .find()
                    .sort(asc ?
                            Sorts.ascending(lastKey) :
                            Sorts.descending(lastKey)
                    )
                    .limit(pagingSize);
        } else {
            findIterable = mongoDatabase.getCollection(topic)
                    .find(and(conditionList))
                    .sort(asc ?
                            Sorts.ascending(lastKey) :
                            Sorts.descending(lastKey)
                    )
                    .limit(pagingSize);
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
    private List<Bson> buildCondition(HashMap<String, String> params) {
        final Bson[] condition = new Bson[1];
        List<Bson> conditionList = new ArrayList<>();
        params.forEach((k, v) -> {
            String[] conditionValue = getConditionValue(v);
            switch (conditionValue[0]) {
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
                    String[] between = conditionValue[1].split("and");
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
    private String[] getConditionValue(String expression) {
        Matcher operMathcer = operPattern.matcher(expression);
        String oper = null;
        String value = null;
        if (operMathcer.find()) {
            oper = operMathcer.group();
        }
        value = expression.replace(oper, "");
        return new String[]{oper.trim(), value.trim()};
    }

    /**
     * 保存或修改
     *
     * @param topic 操作topic的对应的数据库
     * @param json json格式的数据
     * @param keyProperty 数据id属性名称
     * @param keyValue 数据id值
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
}
