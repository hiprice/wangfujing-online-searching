package com.wfj.search.online.web.es.impl;

import com.wfj.search.online.index.pojo.PropertyIndexPojo;
import com.wfj.search.online.web.es.PropertyEsIao;
import com.wfj.search.utils.es.EsUtil;
import org.elasticsearch.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * <p>create at 16-3-28</p>
 *
 * @author liufl
 * @since 1.0.19
 */
@Component("propertyEsIao")
public class PropertyEsIaoImpl implements PropertyEsIao {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final String TYPE = "property";
    @Autowired
    private Client esClient;
    @Value("${es.index}")
    private String index;

    @Override
    public PropertyIndexPojo get(String propertyId) {
        try {
            EsUtil.get(esClient, propertyId, index, TYPE, PropertyIndexPojo.class);
        } catch (Exception e) {
            logger.warn("GET属性[{}]信息失败", propertyId, e);
        }
        return null;
    }
}
