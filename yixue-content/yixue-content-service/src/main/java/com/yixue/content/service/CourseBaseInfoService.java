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
    PageResult<CourseBase> queryCourseBaseList(PageParams pageParams,
                                               QueryCourseParamsDto queryCourseParamsDto);
    CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto addCourseDto);
}
