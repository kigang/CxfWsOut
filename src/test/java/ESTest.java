import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import cxf.dto.Input;
import cxf.dto.TransferResult;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ESTest {

    public static void main(String[] args) {

        /*final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("elastic", "123456"));  //es账号密码（默认用户名为elastic）
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http"))
                        .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                                httpClientBuilder.disableAuthCaching();
                                return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                            }
                        }));*/

        try {
            RestClientBuilder builder = RestClient.builder(new HttpHost("localhost", 9200, "http"));
            RestHighLevelClient client = new RestHighLevelClient(builder);
            //插入

            String INDEX = "test_index";
            List<TransferResult> list = new ArrayList<>();
            for (int i=0;i<10;i++) {
                TransferResult result = new TransferResult();
                result.setMessage("大数据测试大数据测试大数据测试大数据测试");
                result.setShaCode("大数据测试大数据测试");
                result.setBatchId("大数据测试大数据测试大数据测试大数据测试");
                list.add(result);
            }
            /*JSONObject object2 = new JSONObject();
            object2.put("tre",object);
            object2.put("tre",object1);*/
            //input.setObject(object1);

            JSONArray array = new JSONArray();
            array.addAll(list);

            IndexRequest indexRequest = new IndexRequest(INDEX);
            indexRequest.source(array, XContentType.JSON);
            IndexResponse response =  client.index(indexRequest, RequestOptions.DEFAULT);
            System.out.println("-----------状态-----------------:"+response.status());
            //查询
            /*SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            QueryBuilder queryBuilder = QueryBuilders.termQuery("id", 1111L);
            sourceBuilder.query(queryBuilder);

            SearchRequest searchRequest = new SearchRequest();
            searchRequest.indices(INDEX);
            searchRequest.source(sourceBuilder);
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

            SearchHit[] searchHits = searchResponse.getHits().getHits();
            if (searchHits.length > 0) {
                Input input1 = JSON.parseObject(searchHits[0].getSourceAsString(), Input.class);
                System.out.println(input1.toString());
            }*/

            /*SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            QueryBuilder builder1 = QueryBuilders.termQuery("taskInstanceId", 88958658423223837L);
            sourceBuilder.query(builder1);
            QueryBuilders.existsQuery("");

            SearchRequest searchRequest = new SearchRequest();
            searchRequest.indices(INDEX);
            searchRequest.source(sourceBuilder);


            SearchResponse  searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

            SearchHit[] searchHits = searchResponse.getHits().getHits();
            for (SearchHit it : searchHits) {
                System.out.println(it.getSourceAsString());
            }*/


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
