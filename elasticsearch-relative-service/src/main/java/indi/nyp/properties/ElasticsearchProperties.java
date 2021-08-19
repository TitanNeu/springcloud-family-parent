package indi.nyp.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * @program: springcloud-family-parent
 * @description: ES常量
 * @author: Mr.Niu
 * @create: 2021-08-19 21:49
 **/

@Component
public class ElasticsearchProperties {

    /**
     * 请求协议
     */
    @Value("${elasticsearch.config.schema}")
    private String schema;

    /**
     * 集群名称
     */
    @Value("${elasticsearch.config.clusterName}")
    private String clusterName;

    /**
     * 集群节点
     */
    @Value("${elasticsearch.config.clusterNodes}")
    @NotNull(message = "集群节点不允许为空")
    private String clusterNodes;

    /**
     * 连接超时时间(毫秒)
     */
    @Value("${elasticsearch.config.connectTimeout}")
    private Integer connectTimeout;

    /**
     * socket 超时时间
     */
    @Value("${elasticsearch.config.socketTimeout}")
    private Integer socketTimeout;

    /**
     * 连接请求超时时间
     */
    @Value("${elasticsearch.config.connectionRequestTimeout}")
    private Integer connectionRequestTimeout;

    /**
     * 每个路由的最大连接数量
     */
    @Value("${elasticsearch.config.maxConnectPerRoute}")
    private Integer maxConnectPerRoute;

    /**
     * 最大连接总数量
     */
    @Value("${elasticsearch.config.maxConnectTotal}")
    private Integer maxConnectTotal;

    /**
     * 分片数
     */
    @Value("${elasticsearch.config.numberOfShards}")
    private Integer numberOfShards;

    /**
     * 备份数
     */
    @Value("${elasticsearch.config.numberOfReplicas}")
    private Integer numberOfReplicas;

    /**
     * 认证用户
     */
    @Value("${elasticsearch.config.username}")
    private String username;

    /**
     * 认证密码
     */
    @Value("${elasticsearch.config.password}")
    private String password;

    @Value("${elasticsearch.config.keepAliveTime}")
    private Long keepAliveTime;

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getClusterNodes() {
        return clusterNodes;
    }

    public void setClusterNodes(String clusterNodes) {
        this.clusterNodes = clusterNodes;
    }

    public Integer getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public Integer getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(Integer socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public Integer getConnectionRequestTimeout() {
        return connectionRequestTimeout;
    }

    public void setConnectionRequestTimeout(Integer connectionRequestTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
    }

    public Integer getMaxConnectPerRoute() {
        return maxConnectPerRoute;
    }

    public void setMaxConnectPerRoute(Integer maxConnectPerRoute) {
        this.maxConnectPerRoute = maxConnectPerRoute;
    }

    public Integer getMaxConnectTotal() {
        return maxConnectTotal;
    }

    public void setMaxConnectTotal(Integer maxConnectTotal) {
        this.maxConnectTotal = maxConnectTotal;
    }

    public Integer getNumberOfShards() {
        return numberOfShards;
    }

    public void setNumberOfShards(Integer numberOfShards) {
        this.numberOfShards = numberOfShards;
    }

    public Integer getNumberOfReplicas() {
        return numberOfReplicas;
    }

    public void setNumberOfReplicas(Integer numberOfReplicas) {
        this.numberOfReplicas = numberOfReplicas;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getKeepAliveTime() {
        return keepAliveTime;
    }

    public void setKeepAliveTime(Long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }
}

