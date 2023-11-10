package com.yixue.content.service;

import com.yixue.content.mode.po.CourseTeacher;

import java.util.List;

/**
 * CourseTeacherService$ 课程老师
 *
 * @author liy
 * @date 2023/11/8$
 */
public interface CourseTeacherService {
    /**
     * @description: 查询老师列表

     * @return: List<CourseTeacher>

     */
    List<CourseTeacher> list(Long courseId);
    /**
     * @description: 新增、修改老师信息

     * @return: CourseTeacher

     */
    CourseTeacher modifyCourseBase(Long company, CourseTeacher courseTeacher);

    /**
     * @description: 查询老师信息

     * @return: CourseTeacher

     */
    CourseTeacher getCourseTeacher(Long id);

    /**
     * @description: 删除老师信息

     * @return: void

     */
    void deleteCourseTeacher(Long courseId, Long teacherId);
}
