package indi.nyp.properties;

import indi.nyp.params.Params;
import indi.nyp.utils.AESUtils;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
@Data
public class ElasticsearchProperties2 implements InitializingBean {

    /**
     * 请求协议
     */
    private String schema = "http";

    /**
     * 集群名称
     */
    private String clusterName = "dick-hunter";

    /**
     * 集群节点
     */
    private String clusterNodes = "es-cn-2r42s4mpp001ew9k6.public.elasticsearch.aliyuncs.com:9200";

    /**
     * 连接超时时间(毫秒)
     */
    private Integer connectTimeout = 1000;

    /**
     * socket 超时时间
     */
    private Integer socketTimeout = 30000;

    /**
     * 连接请求超时时间
     */
    private Integer connectionRequestTimeout = 5000;

    /**
     * 每个路由的最大连接数量
     */
    private Integer maxConnectPerRoute = 100;

    /**
     * 最大连接总数量
     */
    private Integer maxConnectTotal = 300;

    /**
     * 分片数
     */
    private Integer numberOfShards = 1;

    /**
     * 备份数
     */
    private Integer numberOfReplicas = 1;

    /**
     * 认证用户
     */
    private String username = "elastic";

    private String password;

    private Long keepAliveTime = 600000L;


    @Override
    public void afterPropertiesSet() throws Exception {
        password = AESUtils.decrypt(Params.ES_PASSWD, Params.MAGIC_NUM);
    }
}

