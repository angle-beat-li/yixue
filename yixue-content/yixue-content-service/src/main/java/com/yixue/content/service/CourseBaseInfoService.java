package com.yixue.content.service;

import com.yixue.base.mode.PageParams;
import com.yixue.content.mode.dto.AddCourseDto;
import com.yixue.content.mode.dto.CourseBaseInfoDto;
import com.yixue.content.mode.dto.PageResult;
import com.yixue.content.mode.dto.QueryCourseParamsDto;
import com.yixue.content.mode.po.CourseBase;

/**
 * CourseBaseInfoService$
 *
 * @author liy
 * @date 2023/11/5$
 */
public interface CourseBaseInfoService {
    /**
     * @description: 课程查询分页

     * @return:课程分页信息

     */
    PageResult<CourseBase> queryCourseBaseList(PageParams pageParams,
                                               QueryCourseParamsDto queryCourseParamsDto);

    /**
     * @description: 新增和修改课程

     * @return: 课程基本信息

     */
    CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto addCourseDto);
}
