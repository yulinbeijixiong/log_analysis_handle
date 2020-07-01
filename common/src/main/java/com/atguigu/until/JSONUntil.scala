package com.atguigu.until

import com.atguigu.model.StartupReportLogs
import org.codehaus.jackson.map.ObjectMapper

object JSONUntil {
  def json2ToStartupLog(josn:String):StartupReportLogs={
    val mapper = new ObjectMapper()
    val logs = mapper.convertValue(josn,classOf[StartupReportLogs])
    logs
  }
}
