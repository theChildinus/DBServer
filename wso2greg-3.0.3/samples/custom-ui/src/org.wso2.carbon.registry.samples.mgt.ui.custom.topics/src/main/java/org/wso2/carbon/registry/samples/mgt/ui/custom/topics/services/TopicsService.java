package org.wso2.carbon.registry.samples.mgt.ui.custom.topics.services;

import org.wso2.carbon.registry.samples.mgt.ui.custom.topics.beans.TopicBean;
import org.wso2.carbon.registry.samples.mgt.ui.custom.topics.util.TopicUtil;

public class TopicsService {

    public TopicBean getTopicBean(String path) throws Exception {
        return TopicUtil.getTopicBean(path);       
    }
}
