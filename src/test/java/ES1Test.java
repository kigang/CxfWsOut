import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import cxf.dto.Input;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
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

import java.util.Map;

public class ES1Test {

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
            String INDEX = "test_index";

            /*String[] s = new String[3];
            s[0] = "object.taskCode";*/

            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

            //BoolQueryBuilder boolQueryBuilder =
            BoolQueryBuilder builder2 = QueryBuilders.boolQuery();
            BoolQueryBuilder builder3 = QueryBuilders.boolQuery();


            //builder3.should(QueryBuilders.termQuery("instanceId", "88945523515576478"));
            //builder3.should(QueryBuilders.termQuery("id", 2226));taskInstanceId 88958658423227232

            //builder2.must(builder3);
            //builder2.should(QueryBuilders.existsQuery("value.source"));
            //builder2.should(QueryBuilders.existsQuery("value.target"));
            builder2.must(QueryBuilders.termQuery("taskInstanceId", "88958658423227232"));
            //builder2.should(QueryBuilders.existsQuery("value.data.hours.hours"));

            //builder1.queryName("object.taskCode");
            sourceBuilder.query(builder2);
            sourceBuilder.size(1000);

            String[] includes = {"resultSize","result"};

            String[] excludes = {"result","value"};

            sourceBuilder.fetchSource(null,excludes);

            SearchRequest searchRequest = new SearchRequest();
            searchRequest.indices(INDEX);
            searchRequest.source(sourceBuilder);



            SearchResponse  searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

            SearchHit[] searchHits = searchResponse.getHits().getHits();
            for (SearchHit it : searchHits) {
                //System.out.println("------------"+it.getSourceAsString());
                Map<String, Object> m = JSON.parseObject(it.getSourceAsString(), Map.class);
                System.out.println(m.toString());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
