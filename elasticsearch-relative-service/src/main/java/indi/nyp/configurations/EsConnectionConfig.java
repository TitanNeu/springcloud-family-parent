package indi.nyp.configurations;

import indi.nyp.properties.ElasticsearchProperties;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

import java.util.Arrays;

/**
 * @program: springcloud-family-parent
 * @description: es配置类
 * @author: Mr.Niu
 * @create: 2021-08-19 22:29
 **/

@Configuration
public class EsConnectionConfig {
    @Autowired
    ElasticsearchProperties properties;

    @Bean(value = "dick es client")
    public RestHighLevelClient getClient() {
        String[] hostStringList = properties.getClusterNodes().split(",");

        HttpHost[] httpHosts = Arrays.stream(hostStringList).map(s -> {
            Assert.notNull(s, "node info must not null");
            String[] info = s.split(":");
            Assert.state(info.length == 2, "node must be defined as 'host:port'");
            return new HttpHost(info[0], Integer.parseInt(info[1]));
        }).toArray(HttpHost[]::new);

        RestClientBuilder builder = RestClient.builder(httpHosts);
//        String user = "elastic";
//        String pwd = "chinadick";
//        String auth = Base64.encodeBase64String((user + ":" + pwd).getBytes());
//        //条件basic auth认证
//        builder.setDefaultHeaders(new BasicHeader[]{new BasicHeader("Authorization", "basic " + auth)});
        return getRestHighLevelClient(builder, properties);
    }

    //构建builder
    private RestHighLevelClient getRestHighLevelClient(RestClientBuilder builder, ElasticsearchProperties elasticsearchProperties) {

        // Callback used the default {@link RequestConfig} being set to the {@link CloseableHttpClient}
        builder.setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder.setConnectTimeout(elasticsearchProperties.getConnectTimeout());
            requestConfigBuilder.setSocketTimeout(elasticsearchProperties.getSocketTimeout());
            requestConfigBuilder.setConnectionRequestTimeout(elasticsearchProperties.getConnectionRequestTimeout());
            return requestConfigBuilder;
        });

        // Callback used to customize the {@link CloseableHttpClient} instance used by a {@link RestClient} instance.
        builder.setHttpClientConfigCallback(httpClientBuilder -> {

            ConnectionKeepAliveStrategy connectStrategy = (response, context) -> {
                HeaderElementIterator it = new BasicHeaderElementIterator(response.headerIterator(HTTP.CONN_KEEP_ALIVE));
                while (it.hasNext()) {
                    HeaderElement he = it.nextElement();
                    String param = he.getName();
                    String value = he.getValue();
                    if (value != null && param.equalsIgnoreCase("timeout")) {
                        return Long.parseLong(value);
                    }
                }
                return elasticsearchProperties.getKeepAliveTime();//set default keep alive time
            };
            httpClientBuilder.setKeepAliveStrategy(connectStrategy);
            httpClientBuilder.setMaxConnTotal(elasticsearchProperties.getMaxConnectTotal());
            httpClientBuilder.setMaxConnPerRoute(elasticsearchProperties.getMaxConnectPerRoute());
            // Callback used the basic credential auth
            if (!StringUtils.isEmpty(elasticsearchProperties.getUsername()) && !StringUtils.isEmpty(elasticsearchProperties.getUsername())) {
                final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
                credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(elasticsearchProperties.getUsername(), elasticsearchProperties.getPassword()));
                httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            }
            return httpClientBuilder;
        });
        return new RestHighLevelClient(builder);
    }


}
