package com.zbcn.demo.data.clean;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Data;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 车源同步crmId
 *
 * @author likun
 * @date 2018/11/6 18:22
 */
public class CrmUserIdUtils {



    //获取httpClient
    private static PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
    private static CloseableHttpClient httpClient = HttpClients.createSystem();

    //获取httpClient
    private static MongoCredential credential = MongoCredential.createCredential("vehicle_rw", "admin", "Rfvbhu#!7620".toCharArray());
    private static MongoClient mongoClient = new MongoClient(new ServerAddress("10.3.9.32", 27017), credential, MongoClientOptions
            .builder().build());
    private static MongoDatabase vehicle = mongoClient.getDatabase("vehicle");

    /**
     * jsonNode 的操作类
     */
    private static ObjectMapper objectMapper = new ObjectMapper();


    public static void main(String[] args){
        getexperedVehicle();
    }

    private static void getexperedVehicle(){
        JsonNode vehicles = getVehicles("inventory");
        JsonNode at = vehicles.at("/content");
        List<String> list = new ArrayList();
        if(at.isArray()){
            for (JsonNode v : at){
                String id = v.at("id").toString();
                VehicleExpiredTips vehicleExpiredTips = new VehicleExpiredTips();
//                vehicleExpiredTips.setId(new ObjectId(id));
//                vehicleExpiredTips.setStatus(0);
//                vehicleExpiredTips.setModifyAt(DateTime.now());

        //list.add()
    }
}


        // JsonNode at = vehicles.at("/unifiedNumber");
        MongoCollection<Document> vehicle_expired_tips = vehicle.getCollection("vehicle_expired_tips");
        BsonDocument bsonDocument = new BsonDocument();
       // bsonDocument.put()

    }


    private void updateDaysConfForCity(){

        MongoCollection<Document> days_conf_for_city = vehicle.getCollection("days_conf_for_city");

        final Integer pageSize = 10000;
        Integer page = 0;
        FindIterable<Document> skip = days_conf_for_city.find().limit(pageSize).skip(0);

    }


    /**
     * 依据userId获取crmUserId
     * @param userId
     * @param token
     * @return
     */
    public String getCrmIdByUserId(String userId,String token){
        //tocken
        String server = "https://p-api.kanche.com/v1";
        String url = String.format("%s/users/get_by_id?id=%",server,userId);;
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Authorization", token);

        try {
            CloseableHttpResponse execute = httpClient.execute(httpGet);
            String entity = EntityUtils.toString(execute.getEntity());
            JsonNode response = objectMapper.readTree(entity);
            execute.close();
            boolean ok = response.at("/ok").asBoolean();
            if (!ok) {
                System.out.println(response);
            }
            return response.at("/data").at("crmUserId").toString();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("获取crmUserId错误："+userId);
        }
        return null;

    }

    private static JsonNode getVehicles(String reCollectionType) {
        String url = "https://p-api.kanche.com/v1/search/vehicles?page=0&size=1000&exists=status.tips&reCollectionType="+ reCollectionType;
        return requestApi(url);
    }

    private static JsonNode requestApi(String url) {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Authorization", "fb92a1f143f748dba394534877aeb577");
        try {
            CloseableHttpResponse execute = httpClient.execute(httpGet);
            JsonNode response = objectMapper.readTree(EntityUtils.toString(execute.getEntity()));
            execute.close();
            boolean ok = response.at("/ok").asBoolean();
            if (!ok) {
                System.out.println(response);
            }
            return response.at("/data");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return MissingNode.getInstance();
    }






}

@Data
@org.springframework.data.mongodb.core.mapping.Document(collection = "vehicle_expired_tips")
class VehicleExpiredTips {
    //@Id
    private ObjectId id;

    /**
     * 车源统一编码
     */
    private String unifiedNumber;

    /**
     * 提示语
     */
    private String tips;

    /* 状态 */
    private Integer status;

    /* 创建时间 */
    private DateTime createdAt;

    /* 修改时间 */
    private DateTime modifyAt;

}
