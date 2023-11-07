package com.yixue.content.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yixue.content.mode.dto.CourseCategoryTreeDto;
import com.yixue.content.mode.po.CourseCategory;

import java.util.List;

/**
 * <p>
 * 课程分类 Mapper 接口
 * </p>
 *
 * @author liy
 */
public interface CourseCategoryMapper extends BaseMapper<CourseCategory> {
     /**
      * @description: 根据课程id查询课程分类的树状结构

      * @return: 课程的树状结果集

      */
     List<CourseCategoryTreeDto> selectTreeNode(String id);
}
