package com.yixue.content.api;

import com.yixue.base.mode.PageParams;
import com.yixue.content.mode.dto.PageResult;
import com.yixue.content.mode.dto.QueryCourseParamsDto;
import com.yixue.content.mode.po.CourseBase;
import com.yixue.content.service.CourseBaseInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * CourseBaseInfoController$
 *
 * @author liy
 * @date 2023/11/5$
 */
@Api(value = "课程信息编辑接口",tags = "课程信息编辑接口")
@RestController
@RequestMapping("/course")
public class CourseBaseInfoController {

    @Autowired
    public CourseBaseInfoService courseBaseInfoService;
    @ApiOperation("课程查询接口")
    @PostMapping("/list")
    public PageResult<CourseBase> list(PageParams pageParams, @RequestBody(required = false) QueryCourseParamsDto queryCourseParamsDto) {
        return courseBaseInfoService.queryCourseBaseList(pageParams,queryCourseParamsDto);
    }
}
