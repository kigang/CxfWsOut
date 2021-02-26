import cxf.dto.Input;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class FileLocks {
    public static void main(String[] args) throws Exception {
        /*RandomAccessFile raf = new RandomAccessFile("D:\\tmp\\test\\idoc\\application.yml", "rw");
        FileChannel fileChannel = raf.getChannel();
        FileLock lock = fileChannel.lock();
        System.out.println(lock);
        lock.release();*/

        Input input = new Input();
        input.setId(0L);
        input.setName("00");

        String sss = "123";
        input.setName(sss);
        sss = "124";
        System.out.println(input.getName());


        Input input1 = new Input();
        input1.setId(1L);
        input1.setName("11");

        Input input2 = new Input();
        input2.setId(1L);
        input2.setName("11");

        Input input4 = new Input();

        input4.setName("44");

        List<Input> list = new ArrayList<>();
        list.add(input);;
        //list.add(input1);;
        list.add(input2);;
        //list.add(input4);;

        List<Input> list3 = new ArrayList<>();
        list3.add(input);;
        //list3.add(input1);;
        list3.add(input2);;
        list3.add(input4);;

        list.removeAll(list3);

        System.out.println(list.toString());

        Map<String,String> map = new HashMap<>();
        map.put("AA","11");
        map.put("B","2");

        Set<String> listM = map.keySet();

        System.out.println(listM);

        /*List<Input> list1 = list.stream().filter(i -> i.getId()==null || i.getId() == 0L).collect(Collectors.toList());
        List<Input> list2 = list.stream().filter(i -> !(i.getId()==null || i.getId() == 0L)).collect(Collectors.toList());

        System.out.println(list1.size());
        System.out.println(list2.size());

        Set<String> esKeys = new HashSet<>();

        System.out.println( "------------->>>"+ new ArrayList<>(esKeys));*/

    }
}
