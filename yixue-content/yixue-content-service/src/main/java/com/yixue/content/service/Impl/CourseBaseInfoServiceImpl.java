package com.yixue.content.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yixue.base.mode.PageParams;
import com.yixue.content.mode.dto.PageResult;
import com.yixue.content.mode.dto.QueryCourseParamsDto;
import com.yixue.content.mapper.CourseBaseMapper;
import com.yixue.content.mode.po.CourseBase;
import com.yixue.content.service.CourseBaseInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CourseBaseInfoServiceImpl$
 *
 * @author liy
 * @date 2023/11/5$
 */
@Service
public class CourseBaseInfoServiceImpl implements CourseBaseInfoService {

    @Autowired
    public CourseBaseMapper courseBaseMapper;
    @Override
    public PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto queryCourseParamsDto) {
        LambdaQueryWrapper<CourseBase> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(queryCourseParamsDto.getCourseName()),CourseBase::getName,queryCourseParamsDto.getCourseName());
        queryWrapper.eq(StringUtils.isNotEmpty(queryCourseParamsDto.getAuditStatus()),CourseBase::getAuditStatus,queryCourseParamsDto.getAuditStatus());
        queryWrapper.eq(StringUtils.isNotEmpty(queryCourseParamsDto.getPublishStatus()),CourseBase::getPic,queryCourseParamsDto.getPublishStatus());
        Page<CourseBase> page = new Page<>(pageParams.getPageNo(), pageParams.getPageSize());
        Page<CourseBase> pageResult = courseBaseMapper.selectPage(page,queryWrapper);
        List<CourseBase> list = pageResult.getRecords();
        PageResult<CourseBase> result = new PageResult<>(list,pageResult.getTotal(), pageParams.getPageNo(), pageParams.getPageSize());
        return result;
    }
}
