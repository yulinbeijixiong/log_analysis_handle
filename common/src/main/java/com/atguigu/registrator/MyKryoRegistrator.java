package com.atguigu.registrator;

import com.atguigu.model.StartupReportLogs;
import com.esotericsoftware.kryo.Kryo;
import org.apache.spark.serializer.KryoRegistrator;

/**
 * 实现spark 序列化接口
 */
public class MyKryoRegistrator implements KryoRegistrator {
    public void registerClasses(Kryo kryo) {
        kryo.register(StartupReportLogs.class);
    }
}
