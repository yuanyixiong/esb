package com.sinoif.esbimpl.port.config.monodb;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;
import com.sinoif.esb.constants.PortalConstants;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Monodb Index Config
 *
 * @ClassName: IndexConfig
 * @Author： yuanyixiong
 * @Date： 2019/11/21 下午1:52
 * @Description： TODO
 * @Version： 1.0
 */
@Component
public class IndexConfig implements InitializingBean {

    @Autowired
    private MongoDatabase mongoDatabase;

    @Override
    public void afterPropertiesSet() throws Exception {
        mongoDatabase.getCollection(PortalConstants.COLLECTION_INVOKE_LOG_DATA).createIndex(Indexes.descending("invoke_time", "complete_time"));
        mongoDatabase.getCollection(PortalConstants.COLLECTION_INVOKE_LOG_DATA).createIndex(Indexes.descending("invoke_time"));
        mongoDatabase.getCollection(PortalConstants.COLLECTION_INVOKE_LOG_DATA).createIndex(Indexes.descending("complete_time"));
        mongoDatabase.getCollection(PortalConstants.COLLECTION_INVOKE_EXCEPTION).createIndex(Indexes.descending("invoke_time", "complete_time"));
        mongoDatabase.getCollection(PortalConstants.COLLECTION_INVOKE_EXCEPTION).createIndex(Indexes.descending("invoke_time"));
        mongoDatabase.getCollection(PortalConstants.COLLECTION_INVOKE_EXCEPTION).createIndex(Indexes.descending("complete_time"));
        mongoDatabase.getCollection(PortalConstants.COLLECTION_INVOKE_LOG).createIndex(Indexes.descending("invoke_time", "complete_time"));
        mongoDatabase.getCollection(PortalConstants.COLLECTION_INVOKE_LOG).createIndex(Indexes.descending("invoke_time"));
        mongoDatabase.getCollection(PortalConstants.COLLECTION_INVOKE_LOG).createIndex(Indexes.descending("complete_time"));
    }
}
