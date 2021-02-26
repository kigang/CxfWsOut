import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.WrapperQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class ES2Test {

    public static void main(String[] args) {



        try {
            RestClientBuilder builder = RestClient.builder(new HttpHost("localhost", 9200, "http"));
            RestHighLevelClient client = new RestHighLevelClient(builder);
            String INDEX = "hitf-trace-log";

            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            QueryBuilder queryBuilder = QueryBuilders.matchAllQuery(); //QueryBuilders.termQuery("id", 1111L);

            sourceBuilder.query(queryBuilder);

            SearchRequest searchRequest = new SearchRequest();
            searchRequest.indices(INDEX);
            searchRequest.source(sourceBuilder);
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

            SearchHit[] searchHits = searchResponse.getHits().getHits();
            BufferedWriter out = new BufferedWriter(new FileWriter("D:\\tmp\\11.txt"));
            StringBuilder builder1 = new StringBuilder();
            for (SearchHit i : searchHits) {
                builder1.append(i.getSourceAsString());

            }
            out.write(builder1.toString());
            System.out.println("---------------------------------ok---------");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
