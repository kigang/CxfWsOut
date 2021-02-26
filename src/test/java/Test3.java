import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.kv.GetResponse;
import io.etcd.jetcd.lease.LeaseGrantResponse;
import io.etcd.jetcd.lease.LeaseKeepAliveResponse;
import io.etcd.jetcd.lock.LockResponse;
import io.grpc.stub.StreamObserver;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Test3 {

    public static void main(String[] args) {
        //Client client = Client.builder().endpoints("http://127.0.0.1:2379").build();
        ScheduledExecutorService heartPopService;
        try {


            String urlStr = "http://127.0.0.1:2379";
            String keyStr = "/dolphinscheduler/lock2/master11";
            ByteSequence key = ByteSequence.from(keyStr, StandardCharsets.UTF_8);
            ByteSequence value = ByteSequence.from(keyStr, StandardCharsets.UTF_8);
            //"http://192.168.246.128:2379","http://192.168.246.128:12379","http://192.168.246.128:22379"
            Client client = Client.builder().endpoints("http://127.0.0.1:2379").build();
            Long leaseId = null;
            Long id = null;
            try {
                //Client client = Client.builder().endpoints("http://192.168.246.128:2379","http://192.168.246.128:12379","http://192.168.246.128:22379").build();
                //long leaseID = client.getLeaseClient().grant(5).get().getID();



                /*client.getKVClient().put(key, value).get();
                System.out.println("put key si :" + client.getKVClient().get(key).get().getKvs().get(0).getKey().toString(StandardCharsets.UTF_8));
*/

                //GetResponse future = client.getKVClient().get(key).get();
                //System.out.println("get key is :" + future.getKvs().get(0).getKey().toString(StandardCharsets.UTF_8));
                //Thread.sleep(6000);
                //System.out.println(client.getKVClient().get(key).get().getCount());
                LeaseGrantResponse leaseGrantResponse = client.getLeaseClient().grant(12L).get(30, TimeUnit.SECONDS);

                id = leaseGrantResponse.getID();
                CompletableFuture<LockResponse> lock = client.getLockClient().lock(key, id);

                LockResponse lockResponse = lock.get(1, TimeUnit.SECONDS);

                System.out.println("lock key is :" + lockResponse.getKey().toString(StandardCharsets.UTF_8));

                /*client.getLeaseClient().keepAlive(id, new StreamObserver<LeaseKeepAliveResponse>() {
                    @Overrideresponse = {LockResponse@2661} "header {\n  cluster_id: 14841639068965178418\n  member_id: 10276657743932975437\n  revision: 288\n  raft_term: 13\n}\nkey: "/dolphinscheduler/lock2/master11/694d73b8b5b84819"\n"
                    public void onNext(LeaseKeepAliveResponse leaseKeepAliveResponse) {
                        System.out.println("LeaseKeepAliveResponse value:" + leaseKeepAliveResponse.getTTL());
                    }
                    @Override
                    public void onError(Throwable throwable) {
                        System.out.println("onError ");
                    }
                    @Override
                    public void onCompleted() {
                        System.out.println("onCompleted ");
                    }
                });
*/
                //Thread.sleep(60000);
                System.out.println("locking :" + keyStr);
            } catch (Exception e) {
                int t = 0;
                do {
                    Thread.sleep(2000);
                    t++;
                    try {
                        LockResponse lockResponse = client.getLockClient().lock(key, id).get(1, TimeUnit.SECONDS);
                        if (null != lockResponse) {
                            System.out.println("get lock !");
                            client.getLeaseClient().revoke(id);
                            return;
                        }
                    } catch (Exception e1) {
                        System.out.println(e1);
                    }
                    // 启动定时任务续约，心跳周期和初次启动延时计算公式
                    long period = 12 / 2;
                    heartPopService = Executors.newSingleThreadScheduledExecutor();
                    Long finalGrantId = id;
                    heartPopService.scheduleAtFixedRate(() -> {
                        System.out.println("续约 ：" + finalGrantId);
                        client.getLeaseClient().keepAliveOnce(finalGrantId);
                    }, period, period, TimeUnit.SECONDS);


                    /*client.getLeaseClient().keepAlive(id, new StreamObserver<LeaseKeepAliveResponse>() {
                        @Override
                        public void onNext(LeaseKeepAliveResponse leaseKeepAliveResponse) {
                            System.out.println("LeaseKeepAliveResponse value:" + leaseKeepAliveResponse.getTTL());
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

                    System.out.println("---------- :" + t);

                } while (t < 1);
                System.out.println("revoke---------- :" + id);
                client.getLeaseClient().revoke(id);
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
