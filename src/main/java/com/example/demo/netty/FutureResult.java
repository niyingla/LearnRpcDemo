package com.example.demo.netty;

import com.example.demo.dto.SyncResult;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: demo
 * @description:
 * @author: xiaoye
 * @create: 2019-08-12 16:19
 **/
public class FutureResult {

    public static ConcurrentHashMap<String, SyncResult> concurrentHashMap = new ConcurrentHashMap();

    /**
     * 获取结果
     *
     * @param requestId
     * @return
     */
    public static Object getResult(String requestId) {
        SyncResult syncResult = new SyncResult();
        concurrentHashMap.put(requestId, syncResult);
        return syncResult.getData();
    }

    /**
     * 放入结果
     *
     * @param requestId
     * @param result
     * @return
     */
    public static void putResult(String requestId, Object result) {
        for (; ; ) {
            SyncResult syncResult = concurrentHashMap.get(requestId);
            if (syncResult != null) {
                syncResult.setData(result);
                syncResult.setRead(true);
            }
            break;
        }
    }
}
