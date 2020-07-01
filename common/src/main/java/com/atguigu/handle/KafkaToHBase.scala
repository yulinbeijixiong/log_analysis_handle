package com.atguigu.handle

import java.time.Duration
import java.{lang, util}
import java.util.concurrent.TimeUnit
import java.util.{Arrays, Date, Properties}

import com.atguigu.model.StartupReportLogs
import com.atguigu.until.{DateUntil, HBaseUntil, JSONUntil}
import kafka.common.TopicAndPartition
import org.apache.hadoop.hbase.client.Table
import org.apache.hadoop.hbase.util.Bytes
import org.apache.kafka.clients.consumer.{ConsumerRecord, ConsumerRecords, KafkaConsumer}
import org.apache.kafka.common.{Cluster, PartitionInfo, TopicPartition}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies, LocationStrategy, OffsetRange, PerPartitionConfig}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka010._

import scala.collection.immutable.HashMap
import scala.collection.mutable
import scala.collection.JavaConversions._

object KafkaToHBase {
  def main(args: Array[String]): Unit = {
    //设置checkPoint 路径
    val path = "ck/kafkaTohBase/ka"
    //得到kafkaStreaming 对象

    val properties = new Properties()
    //创建sparkconf
    val sparkConf:SparkConf = new SparkConf().setMaster("local[*]").setAppName("kafkaToHBase")
    // sparkstreming 查询一次从kafka一个分区消费的最大信息数
    sparkConf.set("spark.streaming.maxRatePerPartition","100")
    //配置kryo 序列化方式
    sparkConf.set("spark.serializer","org.apache.spark.serializer.KryoSerializer")
    sparkConf.set("spark.kryo.registrator", "com.atguigu.registrator.MyKryoRegistrator")
    //获取streaming程序入口
    val ssc = new StreamingContext(sparkConf,Seconds(3))
    ssc.checkpoint(path)
    //kafka参数声明
    val set = new mutable.HashSet[String]()
    set.+=("log-analysis")
    //分组id
    val groupId = "g1"
    //kafka 参数封装
    val kafkaParams=mutable.HashMap[String,String]()
    //必须添加以下参数，否则会报错
    kafkaParams.put("bootstrap.servers" ,"192.168.1.130:9092")
    kafkaParams.put("group.id", groupId)
    kafkaParams.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    kafkaParams.put("value.deserializer" , "org.apache.kafka.common.serialization.StringDeserializer")
    import org.apache.kafka.common.TopicPartition

    val inputRDDStream: InputDStream[ConsumerRecord[String, String]] = KafkaUtils.createDirectStream(ssc,LocationStrategies.PreferConsistent,ConsumerStrategies.Subscribe[String,String](set,kafkaParams))

    val message: DStream[String] = inputRDDStream.map(_.value())
    message.filter(x=>{
//      println(x)
      if(x.contains("city")){
        true
      }else{
        false
      }

    })
    message.print()
    print(message.count())
    //将数据写入hbase
//    message.map(a=>{
//      val logs: StartupReportLogs = JSONUntil.json2ToStartupLog(a)
//      val city: String = logs.getCity
//      print(city)
//      val ms: lang.Long = logs.getStartTimeInMs
//      val date: String = DateUntil.dateToString(new Date(ms))
//      val rowKey =date+city
//      val table: Table = HBaseUntil.getHBaseTable(properties)
//      table.incrementColumnValue(Bytes.toBytes(rowKey),Bytes.toBytes("info"),Bytes.toBytes("count"),1L)
//    })
    //启动ssc
    ssc.start()
    ssc.awaitTermination()
  }
  
}
