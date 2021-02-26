import cxf.service.JetcdDistributedLock;
import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.Watch;
import io.etcd.jetcd.kv.GetResponse;
import io.etcd.jetcd.lease.LeaseGrantResponse;
import io.etcd.jetcd.lock.LockResponse;
import io.etcd.jetcd.lock.UnlockResponse;
import io.etcd.jetcd.options.PutOption;
import io.etcd.jetcd.options.WatchOption;
import io.etcd.jetcd.watch.WatchEvent;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Test2 {
    public static final Charset UTF_8 = Charset.forName("UTF-8");
    public static void main(String[] args) {
        //Client client = Client.builder().endpoints("http://127.0.0.1:2379").build();

        try {



            String urlStr = "http://127.0.0.1:2379";
            String keyStr = "/dolphinscheduler/lock2/master";
            ByteSequence key = ByteSequence.from(keyStr, StandardCharsets.UTF_8);
            ByteSequence value = ByteSequence.from(keyStr, StandardCharsets.UTF_8);
            ByteSequence value1 = ByteSequence.from(keyStr+"wqw", StandardCharsets.UTF_8);

            Long leaseId = null;
            try {

                //Client client = Client.builder().endpoints("http://192.168.246.128:2379","http://192.168.246.128:12379","http://192.168.246.128:22379").build();
                //long leaseID = client.getLeaseClient().grant(5).get().getID();

                Client client = Client.builder().endpoints(urlStr).build();


                /*Watch.Listener listener = Watch.listener(response -> {

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                    for (WatchEvent event : response.getEvents()) {
                       String str =String.format("time=:%s,type=%s key=%s, value=%s",simpleDateFormat.format(new Date()),
                               event.getEventType().name(),
                               Optional.ofNullable(event.getKeyValue().getKey())
                                       .map(bs -> bs.toString(UTF_8))
                                       .orElse(""),
                               Optional.ofNullable(event.getKeyValue().getValue()
                               ).map(bs -> bs.toString(UTF_8)).orElse(""));
                        System.out.println(str);


                    }
                });
                Watch.Watcher watcher = client.getWatchClient().watch(key, WatchOption.newBuilder().withPrefix(key).build(), listener);*/
                /*LeaseGrantResponse leaseGrantResponse1 = client.getLeaseClient().grant(90L).get(30, TimeUnit.SECONDS);

                Long id1 = leaseGrantResponse1.getID();

                client.getKVClient().put(key, value,PutOption.newBuilder().withLeaseId(id1).build()).get();
                System.out.println("put key si :" + client.getKVClient().get(key).get().getKvs().get(0).getKey().toString(StandardCharsets.UTF_8));

                GetResponse future = client.getKVClient().get(key).get();
                System.out.println("get key is :" + future.getKvs().get(0).getKey().toString(StandardCharsets.UTF_8));*/
                //Thread.sleep(6000);
                //System.out.println(client.getKVClient().get(key).get().getCount());
                LeaseGrantResponse leaseGrantResponse = client.getLeaseClient().grant(90L).get(30, TimeUnit.SECONDS);

                Long id = leaseGrantResponse.getID();
                CompletableFuture<LockResponse> lock = client.getLockClient().lock(key, id);
                LockResponse lockResponse = lock.get(1,TimeUnit.SECONDS);

                /*LeaseGrantResponse leaseGrantResponse2 = client.getLeaseClient().grant(90L).get(30, TimeUnit.SECONDS);

                Long id2 = leaseGrantResponse2.getID();

                client.getKVClient().put(key, value1,PutOption.newBuilder().withLeaseId(id2).build()).get();*/
                System.out.println("locking :" + keyStr);
                //UnlockResponse unlockResponse = client.getLockClient().unlock(key).get(2,TimeUnit.SECONDS);
                //System.out.println("unlock :" + keyStr);
                client.getLeaseClient().revoke(id);
            } catch (TimeoutException e) {

                System.out.println(e);

            }

            /*JetcdDistributedLock lock = new JetcdDistributedLock(urlStr);
            // 获取租约,到期后将会移除(防止死锁)
            LeaseGrantResponse leaseGrantResponse = lock.getLeaseClient().grant(1).get();
            long id = 20L;// leaseGrantResponse.getID();
            lock.setLock(keyStr,id);
            System.out.println("lock keyStr is :" + keyStr);
            lock.releaseLock(keyStr,id);
            System.out.println("releaseLock keyStr is :" + keyStr);
            lock.getLeaseClient().revoke(id);*/
        } catch (Exception e) {

        }
    }

}
