package com.atguigu.model;

import lombok.Data;

@Data
public class StartupReportLogs extends BasicLog {
    private String appVersion;
    private Long startTimeInMs;
    private Long activeTimeInMs;
    private String city;
}
