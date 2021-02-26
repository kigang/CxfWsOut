import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.kv.GetResponse;
import io.etcd.jetcd.lease.LeaseGrantResponse;
import io.etcd.jetcd.lease.LeaseKeepAliveResponse;
import io.etcd.jetcd.lock.LockResponse;
import io.etcd.jetcd.options.DeleteOption;
import io.etcd.jetcd.options.PutOption;
import io.grpc.stub.StreamObserver;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class Test4 {

    public static void main(String[] args) {
        //Client client = Client.builder().endpoints("http://127.0.0.1:2379").build();

        try {



            String urlStr = "http://127.0.0.1:2379";
            String keyStr = "/dolphinscheduler/lock2/master";
            String keyStr1 = "/dolphinscheduler/lock2/master1";
            //String keyStr1 = "/dolphinscheduler/lock2";
            ByteSequence key = ByteSequence.from(keyStr, StandardCharsets.UTF_8);
            ByteSequence key2 = ByteSequence.from(keyStr1, StandardCharsets.UTF_8);
            ByteSequence key1 = ByteSequence.from(keyStr1, StandardCharsets.UTF_8);
            ByteSequence value = ByteSequence.from(keyStr1, StandardCharsets.UTF_8);
            ByteSequence value1 = ByteSequence.from(keyStr, StandardCharsets.UTF_8);
            //"http://192.168.246.128:2379","http://192.168.246.128:12379","http://192.168.246.128:22379"
            Long leaseId = null;
            try {
                //Client client = Client.builder().endpoints("http://192.168.246.128:2379","http://192.168.246.128:12379","http://192.168.246.128:22379").build();
                //long leaseID = client.getLeaseClient().grant(5).get().getID();

                Client client = Client.builder().endpoints(urlStr).build();
                /*LeaseGrantResponse leaseGrantResponse = client.getLeaseClient().grant(25L).get(10, TimeUnit.SECONDS);

                leaseId = leaseGrantResponse.getID();
                System.out.println("leaseId :" + leaseId);
                client.getKVClient().put(key, value, PutOption.newBuilder().withLeaseId(leaseId).build()).get();
                System.out.println("put key si :" + client.getKVClient().get(key).get().getKvs().get(0).getKey().toString(StandardCharsets.UTF_8));
                *//*client.getLeaseClient().keepAlive(leaseId, new StreamObserver<LeaseKeepAliveResponse>() {
                    @Override
                    public void onNext(LeaseKeepAliveResponse leaseKeepAliveResponse) {
                        System.out.println("LeaseKeepAliveResponse value1:" + leaseKeepAliveResponse.getID());
                        *//*try {
                            Thread.sleep(3000);
                        } catch (Exception e) {

                        }
                        client.getLeaseClient().revoke(leaseKeepAliveResponse.getID());*//*

                    }
                    @Override
                    public void onError(Throwable throwable) {
                        System.out.println("onError value1");
                    }
                    @Override
                    public void onCompleted() {
                        System.out.println("onCompleted value1");
                    }
                });*/

                //LeaseGrantResponse leaseGrantResponse1 = client.getLeaseClient().grant(13L).get(10, TimeUnit.SECONDS);
               // leaseId = leaseGrantResponse1.getID();
                //System.out.println("leaseId :" + leaseId);
                client.getKVClient().put(key, value).get();
                GetResponse future1 = client.getKVClient().get(key2).get();
                System.out.println("get getLease is :" + future1.getKvs().get(0).getLease());
                System.out.println("get CreateRevision is :" + future1.getKvs().get(0).getCreateRevision());
                System.out.println("get ModRevision is :" + future1.getKvs().get(0).getModRevision());

                GetResponse future = client.getKVClient().get(key).get();
                System.out.println("get getLease is :" + future.getKvs().get(0).getLease());
                System.out.println("get CreateRevision is :" + future.getKvs().get(0).getCreateRevision());
                System.out.println("get ModRevision is :" + future.getKvs().get(0).getModRevision());
                //System.out.println("get key is :" + future.getKvs().get(0).getKey().toString(StandardCharsets.UTF_8));
                //client.getKVClient().get(key);

               /* client.getLeaseClient().keepAlive(leaseId, new StreamObserver<LeaseKeepAliveResponse>() {
                    @Override
                    public void onNext(LeaseKeepAliveResponse leaseKeepAliveResponse) {
                        System.out.println("LeaseKeepAliveResponse value2:" + leaseKeepAliveResponse.getID());
                        //client.getLeaseClient().revoke(leaseKeepAliveResponse.getID());
                    }
                    @Override
                    public void onError(Throwable throwable) {
                        System.out.println("onError ");
                    }
                    @Override
                    public void onCompleted() {
                        System.out.println("onCompleted ");
                    }
                });*/

                //Thread.sleep(6000);
                //System.out.println(client.getKVClient().get(key).get().getCount());
                //LeaseGrantResponse leaseGrantResponse = client.getLeaseClient().grant(60L).get(30, TimeUnit.SECONDS);


            } catch (Exception e) {
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
