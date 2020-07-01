package com.atguigu.until

import java.util.Properties

import org.apache.hadoop.hbase.{HBaseConfiguration, TableName}
import org.apache.hadoop.hbase.client.{ConnectionFactory, Table}


object HBaseUntil {
 def getHBaseTable(properties:Properties):Table={
  //获取配置对象
    val configuration = HBaseConfiguration
      .create()
    configuration.set("hbase.zookeeper.property.clientPort",PropertiesUntil.getPropertiesValue("hbase.zookeeper.property.clientPort"))
    configuration.set("hbase.zookeeper.quorum",PropertiesUntil.getPropertiesValue("hbase.zookeeper.quorum"))
    //创建连接
    val connection = ConnectionFactory.createConnection(configuration)
    //获取表
    val table = connection.getTable(TableName.valueOf("online_city_click_count"))
    table
 }
}
