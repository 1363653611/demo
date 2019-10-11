package com.zbcn.demo.mongodb;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.bson.BsonDateTime;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.Document;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.stream.Collectors.toMap;

/**
 * @author
 * 2018/10/31
 */
public class ExportInventory {
    private static PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
    private static CloseableHttpClient httpClient = HttpClients.createSystem();
    private static String token = "401cbd7940d74835ad017238093b4312";
    private static String vehicleUrl = "https://p-api.kanche.com/v1";
    //    private static MongoClient mongoClient = new MongoClient("localhost");
    private static MongoCredential credential = MongoCredential.createCredential("vehicle_rw", "admin", "Rfvbhu#!7620".toCharArray());
    private static MongoClient mongoClient = new MongoClient(new ServerAddress("10.3.9.32", 27017), credential, MongoClientOptions.builder().build());
    private static MongoDatabase vehicleDB = mongoClient.getDatabase("vehicle");
    private static MongoCollection<Document> vehicle_inventory = vehicleDB.getCollection("vehicle_inventory");
    private static MongoCollection<Document> inventory_config = vehicleDB.getCollection("vehicle_inventory_config");
    private static ObjectMapper objectMapper = new ObjectMapper();
    //    private static String cities = cities();
    private static SXSSFWorkbook workbook = new SXSSFWorkbook(500);
    private static ExecutorService executorService = new ThreadPoolExecutor(31, 60, 60L, TimeUnit.SECONDS, new LinkedBlockingDeque<>());
    private static DateTimeFormatter formatter = ISODateTimeFormat.dateTimeParser();
    private static LinkedBlockingQueue<Event> events = new LinkedBlockingQueue<>();
    private static LinkedBlockingQueue<Future> futures = new LinkedBlockingQueue<>();
    private static Map<SXSSFSheet, AtomicInteger> hashMap = new HashMap<>();
    private static Document empty = new Document();

    static {
        connectionManager.setDefaultMaxPerRoute(50);
        workbook.setCompressTempFiles(true); //压缩临时文件，很重要，否则磁盘很快就会被写满
    }

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        LinkedList<Thread> threads = new LinkedList<>();
        for (int i = 0; i < 1; i++) {
            EventWrite eventWrite = new EventWrite();
            Thread thread = new Thread(eventWrite);
            thread.start();
            threads.add(thread);
        }
        System.out.println("threading");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<String> headers = Arrays.asList("盘点周期", "城市", "市场", "门店", "车源ID", "VIN", "车型", "采集人", "上架时间", "在售状态", "已售时间", "下架操作人", "是否盘点", "盘点结果",
                "盘点时间", "盘点人");
        BsonDocument bsonDocument = new BsonDocument();
        bsonDocument.append("type", new BsonString("default"));
        bsonDocument.append("status", new BsonString("inactive"));
        for (Document config : inventory_config.find(bsonDocument).sort(Sorts.descending("_id")).limit(1)) {
            Date startTime = config.getDate("cycleStartTime");
            String cycleStartTime = simpleDateFormat.format(startTime);
//            Date endTime = config.getDate("cycleEndTime");
            String cycleEndTime = simpleDateFormat.format(config.getDate("cycleEndTime"));
            String title = cycleStartTime + "至" + cycleEndTime;
            SXSSFSheet sheet = workbook.createSheet(title);
            SXSSFRow row = sheet.createRow(0);
            int column = 0;
            for (String h : headers) {
                SXSSFCell cell = row.createCell(column++);
                cell.setCellValue(new XSSFRichTextString(h));
            }
            hashMap.put(sheet, new AtomicInteger(1));

            BsonDocument inventoryQuery = new BsonDocument();
            inventoryQuery.append("cycleStartTime", new BsonDateTime(startTime.getTime()));
            vehicle_inventory.find(inventoryQuery).forEach((Block<? super Document>) (inventory) -> {
                List<Document> vehicleInventoryDetails = inventory.get("vehicleInventoryDetails", new  ArrayList());
                if (vehicleInventoryDetails.isEmpty()) {
                    return;
                }
                Future<?> submit = executorService.submit(() -> {
                    Map<String, Document> documentMap = vehicleInventoryDetails.stream().collect(toMap(document -> document.getString("vehicleId"), document -> document));
                    Set<String> ids = documentMap.keySet();
                    JsonNode vehicles = getVehicles(ids);
                    JsonNode content = vehicles.at("/content");
                    content.forEach(node -> {
                        String vehicleId = node.get("id").asText();
                        Event event = new Event(title, sheet, node, documentMap.get(vehicleId));
                        try {
                            events.put(event);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                });
                try {
                    futures.put(submit);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            System.out.println("starting");
        }

        Thread.sleep(5000);
        while (!futures.isEmpty()) {
            System.out.println(futures.size());
            Future peek = futures.take();
            peek.get();
        }
        System.out.println("finish");
        while (!events.isEmpty()) {
            Thread.sleep(500);
        }
        ExportInventory.executorService.shutdownNow();
        threads.forEach(Thread::interrupt);

        FileOutputStream fileOutputStream = new FileOutputStream("D:\\inventory\\车源盘点_.xlsx");
        workbook.write(fileOutputStream);
        workbook.close();
        fileOutputStream.close();
    }

    private static JsonNode getVehicle(String id) {
        String url = vehicleUrl + "/search/vehicles/" + id;
        return requestApi(url);
    }

    private static JsonNode getVehicles(Set<String> ids) {
        String url = vehicleUrl + "/search/vehicles?page=0&size=" + ids.size() + 1 + "&id=" + String.join(",", ids);
        return requestApi(url);
    }

//    private static void vehicles(SXSSFSheet sheet, int page, int size, String title, DateTime startTime, DateTime endTime) throws IOException, InterruptedException {
//        String auditTime = startTime.minusDays(3).withZone(DateTimeZone.UTC).toString();
//        String url = String.format("https://p-api.kanche.com/v1/search/vehicles?page=%s&size=%s&saleStatus=on_sale,sold," +
//                "off_sale&businessGroupId=%s&auditTimeTo=%s&createdAtFrom=2018-07-27T18:00:00.000Z", page, size, cities, auditTime);
//        HttpGet httpGet = new HttpGet(url);
//        httpGet.setHeader("Authorization", token);
//        CloseableHttpResponse execute = httpClient.execute(httpGet);
//        JsonNode response = objectMapper.readTree(EntityUtils.toString(execute.getEntity()));
//        if (!response.get("ok").asBoolean()) {
//            System.err.println("error:" + response.toString());
//        }
//        JsonNode data = response.get("data");
//        JsonNode content = data.get("content");
//        for (JsonNode v : content) {
//            JsonNode status = v.get("status");
//            JsonNode inventoryStatus = status.get("inventoryStatus");
////            if (inventoryStatus == null || inventoryStatus.isMissingNode() || inventoryStatus.isNull()) {
////                continue;
////            }
//
//            DateTime operatedAt = formatter.parseDateTime(status.get("operatedAt").asText());
//            //下架时间必须小于周期开始时间
//            if (!status.get("status").asText().equals("offline") && startTime.getMillis() < operatedAt.getMillis()) {
//                continue;
//            }
//            BsonDocument bsonDocument = new BsonDocument();
//            bsonDocument.put("cycleStartTime", new BsonDateTime(startTime.getMillis()));
//            bsonDocument.put("merchantId", new BsonString(v.get("owner").get("ownerId").asText()));
//            FindIterable<Document> limit = vehicle_inventory.find(bsonDocument).limit(1);
//            Document document = limit.iterator().tryNext();
//            if (document != null) {
//                List<Document> vehicleInventoryDetails = document.get("vehicleInventoryDetails", List.class);
//                if (vehicleInventoryDetails != null) {
//                    boolean flag = true;
//                    for (Document detail : vehicleInventoryDetails) {
//                        if (detail.getString("vehicleId").equals(v.get("id").asText())) {
//                            Event event = new Event(title, sheet, v, detail);
//                            events.put(event);
//                            flag = false;
//                        }
//                    }
//                    //周期内下架 不需要盘点
//                    if (status.get("status").asText().equals("offline") && endTime.getMillis() > operatedAt.getMillis()) {
//                        continue;
//                    }
//                    if (flag) {
//                        Event event = new Event(title, sheet, v, empty);
//                        events.put(event);
//                        System.out.println(v.get("serialNumber").asText() + " ;" + title);
//                    }
//
//                } else {
//                    //周期内下架 不需要盘点
//                    if (status.get("status").asText().equals("offline") && endTime.getMillis() > operatedAt.getMillis()) {
//                        continue;
//                    }
//                    System.out.println("vehicleInventoryDetails is null; " + v.get("serialNumber").asText() + " ;" + title);
//
//                    Event event = new Event(title, sheet, v, empty);
//                    events.put(event);
//                }
//            } else {
//                //周期内下架 不需要盘点
//                if (status.get("status").asText().equals("offline") && endTime.getMillis() > operatedAt.getMillis()) {
//                    continue;
//                }
//                System.out.println("document==null; " + v.get("serialNumber").asText() + " ;" + title);
//                Event event = new Event(title, sheet, v, empty);
//                events.put(event);
//            }
//        }
//        execute.close();
//        int totalPages = data.get("totalPages").asInt();
//        page += 1;
//        if (page == 1 && totalPages > 1) {
////            int i1 = Math.min(10, totalPages);
//            for (int i = page; i <= totalPages; i++) {
//                int finalI = i;
//                Future<?> submit = executorService.submit(() -> {
//                    try {
//                        vehicles(sheet, finalI, size, title, startTime, endTime);
//                    } catch (IOException | InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                });
//                futures.put(submit);
//            }
//        }
//    }

    private static JsonNode requestApi(String url) {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Authorization", token);
        try {
            CloseableHttpResponse execute = httpClient.execute(httpGet);
            JsonNode response = objectMapper.readTree(EntityUtils.toString(execute.getEntity()));
            execute.close();
            boolean ok = response.at("/ok").asBoolean();
            if (!ok) {
                System.err.println(response);
            }
            return response.at("/data");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return MissingNode.getInstance();
    }

    public static class EventWrite implements Runnable {

        @Override
        public void run() {
            try {
                while (true) {
                    Event event = events.take();
                    SXSSFSheet sheet = event.sheet;
                    AtomicInteger integer = hashMap.get(sheet);
                    write(event.title, sheet, integer.getAndIncrement(), event.vehicle, event.detail);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }

        }
    }

    @Data
    @AllArgsConstructor
    private static class Event {

        private final String title;
        private final SXSSFSheet sheet;
        private final JsonNode vehicle;


        private final Document detail;

    }

    private static void write(String title, SXSSFSheet sheet, int rowNumber, JsonNode vehicle, Document detail) {
        if (vehicle.isMissingNode()) {
            System.out.println(detail);
            return;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SXSSFRow row = sheet.createRow(rowNumber);
        JsonNode status = vehicle.get("status");
        JsonNode owner = vehicle.get("owner");
        JsonNode address = owner.get("address");
        String location = address.get("provinceName").asText() + "-" + address.get("cityName").asText();
        String vinCode = vehicle.get("vin").get("code").asText();
        JsonNode spec = vehicle.get("spec");
        String brand = spec.get("brand").asText();
        String series = spec.get("series").asText();
        String saleName = spec.get("saleName").asText();
        String model = MessageFormat.format("{0} {1} {2}", brand, series, saleName);
        String name = vehicle.get("createdBy").get("person").get("name").asText();
        if (status.get("auditTime") == null) {
            System.out.println(vehicle);
            return;
        }
        String auditTime = status.get("auditTime").asText();
        String operatedAt = null;
        String operatedName = null;
        if (status.get("status").asText().equals("offline")) {
            operatedAt = status.get("operatedAt").asText();
            if (status.get("saleStatus").asText().equals("sold")) {
                operatedName = getName(status.get("operatedBy"));
            }
        }
        String inventoryStatus_ = "否";
        String inventoryAt = "";
        String inventoryBy = "";
        if ("completed".equals(detail.getString("status"))) {
            inventoryStatus_ = "是";
            inventoryAt = dateFormat.format(detail.getDate("createAt"));
            inventoryBy = detail.get("createBy", empty).get("person", empty).getString("name");
        }
        String result = "";
        if (detail.containsKey("result")) {
            result = detail.get("result", empty).getString("value");
        }
        row.createCell(0).setCellValue(title);
        row.createCell(1).setCellValue(location);
        row.createCell(2).setCellValue(address.get("market").asText());
        row.createCell(3).setCellValue(owner.get("ownerName").asText());
        row.createCell(4).setCellValue(vehicle.get("unifiedNumber").asText());
        row.createCell(5).setCellValue(vinCode);
        row.createCell(6).setCellValue(model);
        row.createCell(7).setCellValue(name);
        row.createCell(8).setCellValue(auditTime);
        row.createCell(9).setCellValue(status.get("saleStatusLabel").asText());
        row.createCell(10).setCellValue(operatedAt);
        row.createCell(11).setCellValue(operatedName);
        row.createCell(12).setCellValue(inventoryStatus_);
        row.createCell(13).setCellValue(result);
        row.createCell(14).setCellValue(inventoryAt);
        row.createCell(15).setCellValue(inventoryBy);
    }

    private static String cities() {
        HttpGet httpGet = new HttpGet("https://p-api.kanche.com/v1/groups/cities");
        httpGet.setHeader("Authorization", token);
        try (CloseableHttpResponse execute = httpClient.execute(httpGet)) {
            String text = EntityUtils.toString(execute.getEntity());
            return String.join(",", new HashSet<>(objectMapper.readTree(text).at("/data").findValuesAsText("id")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getName(JsonNode node) {
        return node.get("person").get("name").asText();
    }
}
