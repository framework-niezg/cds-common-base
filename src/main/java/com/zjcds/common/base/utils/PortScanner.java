package com.zjcds.common.base.utils;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author zhegang.nie
 * @date 2015/11/13 0013
 */
public class PortScanner {

    private final static int maxThreads = 20;

    private static ExecutorService pool = Executors.newFixedThreadPool(maxThreads);

    private List<Integer> result = Collections.synchronizedList(new ArrayList<Integer>());

    private final static Integer MinPort = 1;

    private final static Integer MaxPort = 65535;

    public List<Integer> scanPort(String ip) {
        int perTaskForPorts = MaxPort / maxThreads;
        Task task = null;
        Set<Future> futureSet = new HashSet<Future>();
        Integer startLimit = 0;
        Integer endLimit = 0;
        for(int i = 1 ; i <= maxThreads;i++){
            startLimit = (i - 1)* perTaskForPorts + 1;
            //最后一个任务包含所有
            if( i == maxThreads){
                endLimit = MaxPort;
            }
            else {
                endLimit = i * perTaskForPorts;
            }
            System.out.println("端口任务分配:"+startLimit+"~"+endLimit);
            task = new Task(ip, startLimit, endLimit, result );
            futureSet.add(pool.submit(task));
        }

        while (!isCompleted(futureSet)){
            ThreadUtils.sleep(2000);
        }
        return result;
    }

    private boolean isCompleted(Set<Future> futures){
        boolean isCompleted = true;
        for (Future future : futures){
            if(!future.isDone()){
                isCompleted = false;
                break;
            }
        }
        return isCompleted;
    }

    /**
     * 扫描任务
     */
    public class Task implements Runnable{

        private String hostip;

        private Integer minPort;

        private Integer maxPort;

        private List<Integer> result;

        public Task(String hostip,Integer minPort,Integer maxPort,List<Integer> result){
            this.result = result;
            this.hostip = hostip;
            this.minPort = minPort;
            this.maxPort = maxPort;
        }
        @Override
        public void run() {
            result.addAll(scanPort(hostip, minPort, maxPort));
        }
    }

    private List<Integer> scanPort(String ip,Integer minPort,Integer maxPort) {
        List<Integer> ret = new ArrayList<Integer>();
        List<Integer> ports = new ArrayList<Integer>();

        for(int i=minPort ; i <=maxPort ; i++){
            ports.add(i);
        }
        Socket client = null;

        for (Integer p : ports) {
            try {
                client = new Socket();
                client.connect(new InetSocketAddress(ip, p), 30);

                ret.add(p);

            } catch (Exception ex) {
            } finally {
                try {
                    if (client.isConnected())
                        client.close();
                } catch (Exception ex) {
                }
            }
        }
        return ret;
    }
}
