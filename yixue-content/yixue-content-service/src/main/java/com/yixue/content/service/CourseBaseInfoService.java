package com.yixue.content.service;

import com.yixue.base.mode.PageParams;
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
    /*
* @description 课程查询接口
* @param pageParams 分页参数
* @param queryCourseParamsDto 条件条件
* @returnPageResult<com.xuecheng.content.model.po.Cou
rseBase>
* @author Mr.M
* @date 2022/9/6 21:44
*/
    PageResult<CourseBase> queryCourseBaseList(PageParams pageParams,
                                               QueryCourseParamsDto queryCourseParamsDto);
}
