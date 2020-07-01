package com.atguigu.until

import java.text.SimpleDateFormat
import java.util.Date

object DateUntil {
  def dateToString(date:Date):String={
    val format = new SimpleDateFormat("yyyy-MM-dd")
    val str = format.format(date)
    str
  }
}
