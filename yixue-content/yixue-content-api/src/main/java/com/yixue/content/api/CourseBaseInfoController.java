package com.yixue.content.api;

import com.yixue.base.mode.PageParams;
import com.yixue.content.mode.dto.AddCourseDto;
import com.yixue.content.mode.dto.CourseBaseInfoDto;
import com.yixue.content.mode.dto.PageResult;
import com.yixue.content.mode.dto.QueryCourseParamsDto;
import com.yixue.content.mode.po.CourseBase;
import com.yixue.content.service.CourseBaseInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RequestMapping("/course")
public class CourseBaseInfoController {

    @Autowired
    public CourseBaseInfoService courseBaseInfoService;

    /**
     * @description: 课程查询接口

     * @return: 课程分页查询结果

     */
    @ApiOperation("课程查询接口")
    @PostMapping("/list")
    public PageResult<CourseBase> list(PageParams pageParams, @RequestBody(required = false) QueryCourseParamsDto queryCourseParamsDto) {
        return courseBaseInfoService.queryCourseBaseList(pageParams,queryCourseParamsDto);
    }
    /**
     * @description: 新增课程

     * @return: 课程的基本信息

     */
    @PostMapping
    @ApiOperation("新增课程接口")
    public CourseBaseInfoDto createCourseBase(@RequestBody AddCourseDto addCourseDto){
        CourseBaseInfoDto courseBase = courseBaseInfoService.createCourseBase(1232141425L, addCourseDto);
        return courseBase;
    }
}
