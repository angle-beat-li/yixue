package com.yixue.content.mode.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * EditCourseDto$
 *
 * @author liy
 * @date 2023/11/8$
 */
@Data
@ApiModel(value = "EditCourseDto",description = "更改课程类")
public class EditCourseDto extends AddCourseDto{
    @ApiModelProperty(value = "课程id", required = true)
    private Long id;

}
