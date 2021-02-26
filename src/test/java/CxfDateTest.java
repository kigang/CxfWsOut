import com.google.gson.JsonObject;
import cxf.dto.Input;
import cxf.dto.OutPut;
import cxf.service.ICxfService;
import cxf.service.JetcdDistributedLock;
import cxf.service.impl.CxfServiceImpl;
import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.KeyValue;
import io.etcd.jetcd.kv.GetResponse;
import io.etcd.jetcd.kv.PutResponse;
import io.etcd.jetcd.lease.LeaseGrantResponse;
import io.etcd.jetcd.lock.LockResponse;
import io.etcd.jetcd.options.GetOption;
import org.apache.cxf.common.util.CollectionUtils;
import org.apache.cxf.common.util.StringUtils;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Endpoint;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CxfDateTest {

    protected CxfDateTest() throws Exception {
        System.out.println("Starting Server");
        CxfServiceImpl implementor = new CxfServiceImpl();
        String address = "http://localhost:8080/cxf";
        Endpoint.publish(address, implementor);
    }

    public static List groupListByQuantity(List list, int quantity) {
        if (list == null || list.size() == 0) {
            return list;
        }
        if (quantity <= 0) {
            throw  new IllegalArgumentException("分组大小参数异常。");
        }
        List groupList = new ArrayList();
        int count = 0;
        while (count < list.size()) {
            groupList.add(list.subList(count, (count + quantity) > list.size() ? list.size() : count + quantity));
            count += quantity;
        }
        return groupList;
    }

    public static void main(String[] args) {
        //Client client = Client.builder().endpoints("http://127.0.0.1:2379").build();
        try {
            String wpp = "ftp02.source\":\"${global.ftp02.value[0].source}\",\"ftp02.target\":\"${global.ftp02.value[0].target}\"}";
            // 匹配方式
            Pattern p = Pattern.compile("\\$\\{(.*?)}");
            // 匹配
            Matcher matcher = p.matcher(wpp);
            // 处理匹配到的值
            while (matcher.find()) {
                String pp = matcher.group();
                System.out.println("woo: " + pp);
                //System.out.println(pp.indexOf("."));
                String pp1 = pp.substring(pp.indexOf(".")+1,pp.length()-1);
                System.out.println("woo: " + pp1);
                StringBuilder builder = new StringBuilder();
                boolean f = false;
                for (char i : pp1.toCharArray()) {
                    if (f) {
                        if (i == ']') {
                            f = false;
                        }
                        continue;
                    }
                    if (i != '[') {
                        builder.append(i);
                    } else {
                        f = true;
                    }
                }
                System.out.println(builder.toString());
            }


            /*new CxfDateTest();
            System.out.println("Server ready...");

            Thread.sleep(5 * 60 * 1000);
            System.out.println("Server exiting");
            System.exit(0);*/

            /*OutPut outPut = new OutPut();
            GregorianCalendar nowGregorianCalendar =new GregorianCalendar();
            //yyyy-MM-dd HH:mm:ss
            SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy");
            String dateTimeString=simpleDateFormat.format(nowGregorianCalendar.getTime());
            nowGregorianCalendar.setTime(simpleDateFormat.parse(dateTimeString));
            XMLGregorianCalendar xmlDatetime= DatatypeFactory.newInstance().newXMLGregorianCalendar(nowGregorianCalendar);
            outPut.setData(xmlDatetime);*/

            /*Charset UTF_8 = StandardCharsets.UTF_8;
            ByteSequence key = ByteSequence.from("/dolphinscheduler", StandardCharsets.UTF_8);
            ByteSequence value = ByteSequence.from("/value1", StandardCharsets.UTF_8);
            List<KeyValue> list = client.getKVClient().get(key,GetOption.newBuilder().withPrefix(key).build()).get().getKvs();
            CompletableFuture<PutResponse> completableFuture = client.getKVClient().put(key, value);


            Iterator it = list.iterator();
            while (it.hasNext()) {
                KeyValue kv = (KeyValue) it.next();
                System.out.println(kv.getKey().toString(UTF_8));
            }*/

            /*String urlStr = "http://127.0.0.1:2379";
            String keyStr = "/dolphinscheduler";
            ByteSequence key = ByteSequence.from(keyStr, StandardCharsets.UTF_8);
            Client client = Client.builder().endpoints("http://127.0.0.1:2379").build();
            Long leaseId = null;
            try {
                Long id = client.getLeaseClient().grant(10).get(20, TimeUnit.SECONDS).getID();
                CompletableFuture<LockResponse> future = client.getLockClient().lock(key, id);
                LockResponse lockResponse = future.get(5,TimeUnit.SECONDS);
                System.out.println("locking :" + keyStr);
            } catch (Exception e) {
                System.out.println(e);
            }*/

            /*JSONObject jsonObject = new JSONObject();
            jsonObject.put(FIELD_TASK_ID, orchTaskInstance.getTaskId());
            jsonObject.put(FIELD_TASK_NAME, orchTaskInstance.getTaskName());
            jsonObject.put(FIELD_STATUS_CODE, orchTaskInstance.getStatusCode());*/
            //JSONArray jsonArray = JSONArray.toJSONStringWithDateFormat(list,"");
            //JSONArray jsonArray = new JSONArray(list);


            /*Map<Long, List<Input>> respGroup = inputList.stream().collect(Collectors.groupingBy(Input::getId));

            for (Long key : respGroup.keySet()) {
                System.out.println(key + " : " + respGroup.get(key));
            }

            List<Input> inputList1 = new ArrayList<>();
            List<List<Input>> listList = groupListByQuantity(inputList, 1);
            listList.forEach(l -> {
                l.get(0).setId(1111111111111L);
                System.out.println("---->" + l.toString());
            });
            inputList.forEach( k -> {
                System.out.println("---->" + k.toString());
            });*/
            /*System.out.println(input.toString());
            System.out.println(input1.toString());


            String s1 = "/dolphinscheduler/dead-servers/m@aster_192.168.198.1:15678";
            String s2 = "master_192.168.198.1:15678";

            System.out.println(s1.substring(s1.indexOf("@")+1));
            String[] f = s1.split("/");
            System.out.println(f[f.length-2] +"/"+ f[f.length-1]);*/


            //completableFuture.get().hasPrevKv()
            /*if (CollectionUtils.isEmpty(list)) {
                System.out.println(0);
            } else {

                System.out.println(list.get(0).getValue().toString(Charset.forName("UTF-8")));
                System.out.println(1);
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //client.close();
        }


    }
}
