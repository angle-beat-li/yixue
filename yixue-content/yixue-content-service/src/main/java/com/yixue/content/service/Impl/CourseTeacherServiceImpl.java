package com.yixue.content.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yixue.base.exception.YixueException;
import com.yixue.content.mapper.CourseBaseMapper;
import com.yixue.content.mapper.CourseTeacherMapper;
import com.yixue.content.mode.po.CourseBase;
import com.yixue.content.mode.po.CourseTeacher;
import com.yixue.content.service.CourseTeacherService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * CourseTeacherServiceImpl$
 *
 * @author liy
 * @date 2023/11/8$
 */
@Service
public class CourseTeacherServiceImpl implements CourseTeacherService {

    @Autowired
    private CourseTeacherMapper courseTeacherMapper;

    @Autowired
    private CourseBaseMapper courseBaseMapper;

    @Override
    public List<CourseTeacher> list(Long courseId) {
        LambdaQueryWrapper<CourseTeacher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CourseTeacher::getCourseId,courseId);
        List<CourseTeacher> courseTeachers = courseTeacherMapper.selectList(queryWrapper);
        return courseTeachers;
    }

    @Override
    @Transactional
    public CourseTeacher modifyCourseBase(Long company, CourseTeacher courseTeacher) {
        Long courseId = courseTeacher.getCourseId();
        if (courseId == null) {
            YixueException.cast("课程id不能为空");
        }
        if (StringUtils.isBlank(courseTeacher.getTeacherName())) {
            YixueException.cast("老师姓名不能为空");
        }
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        if (!courseBase.getCompanyId().equals(company)) {
            YixueException.cast("只能修改自身机构的课程");
        }
        courseBase.setCreateDate(LocalDateTime.now());
        if (courseTeacher.getId() == null) {
            //新增老师
            CourseTeacher courseTeacherNew = new CourseTeacher();
            BeanUtils.copyProperties(courseTeacher,courseTeacherNew);
            courseTeacherNew.setCreateDate(LocalDateTime.now());
            courseTeacherMapper.insert(courseTeacherNew);
            return getCourseTeacher(courseTeacherNew.getCourseId());
        } else {
            //修改老师
            CourseTeacher teacher = courseTeacherMapper.selectById(courseTeacher.getCourseId());
            BeanUtils.copyProperties(courseTeacher,teacher);
            courseTeacherMapper.updateById(teacher);
            return getCourseTeacher(teacher.getId());
        }
    }
    @Override
    public CourseTeacher getCourseTeacher(Long id) {
        return courseTeacherMapper.selectById(id);
    }

    @Override
    public void deleteCourseTeacher(Long courseId, Long teacherId) {
        LambdaQueryWrapper<CourseTeacher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CourseTeacher::getCourseId,courseId);
        queryWrapper.eq(CourseTeacher::getId,teacherId);
        courseTeacherMapper.delete(queryWrapper);
    }
}
