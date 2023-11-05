package com.yixue.content.mode.dto;

import lombok.Data;
import lombok.ToString;

/**
 * QueryCourseParamsDto$
 *
 * @author liy
 * @date 2023/11/5$
 */
@Data
@ToString
public class QueryCourseParamsDto {
    //审核状态
    private String auditStatus;
    //课程名称
    private String courseName;
    //发布状态
    private String publishStatus;
}
