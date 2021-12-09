package com.example.concurrent.zkSample;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

/**
 * <pre>
 *      Zookeeper 例子
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2021/12/09 16:57  修改内容:
 * </pre>
 */
public class ZookeeperSample {

    public static void main(String[] args) {
        ZkClient client = new ZkClient("localhost:2181");
        client.setZkSerializer(new MyZkSerializer());
        client.subscribeDataChanges("/zk-test", new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                System.out.println("监听到节点数据改变!");
            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                System.out.println("监听到节点数据被删除了");
            }
        });

        try {
            Thread.sleep(1000 * 60 * 2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
