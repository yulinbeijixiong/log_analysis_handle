package com.atguigu.until

import java.util.Properties

object PropertiesUntil {
  def getPropertiesUntil():Properties={

    val properties = new Properties()
    val stream = PropertiesUntil.getClass.getClassLoader.getResourceAsStream("config/hbase.properties")
    properties.load(stream)
    properties
  }
  def getPropertiesValue(key:String):String={
    val properties = new Properties()
    val stream = PropertiesUntil.getClass.getClassLoader.getResourceAsStream("config/hbase.properties")
    properties.load(stream)
    val str = properties.getProperty(key)
    str
  }
}
