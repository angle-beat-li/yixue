package com.yixue.content.api;

import com.yixue.content.mode.po.CourseTeacher;
import com.yixue.content.service.CourseTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CourseTeacherController$
 *
 * @author liy
 * @date 2023/11/8$
 */
@RestController
@Slf4j
@Api(value = "CourseTeacherController",tags = "课程老师接口")
@RequestMapping("/courseTeacher")
public class CourseTeacherController {

    @Autowired
    private CourseTeacherService courseTeacherService;
    /**
     * @description: 查询老师信息列表

     * @return: List<CourseTeacher>

     */
    @ApiOperation("课程id查询老师")
    @GetMapping("/list/{id}")
    public List<CourseTeacher> list(@PathVariable Long id){
        return courseTeacherService.list(id);
    }

    /**
     * @description: 新增。修改老师

     * @return: CourseTeacher

     */
    @ApiOperation("新增课程老师")
    @PostMapping
    public CourseTeacher modifyCourseBase(@RequestBody CourseTeacher courseTeacher) {
        Long companyId = 1232141425L;
        return courseTeacherService.modifyCourseBase(companyId,courseTeacher);
    }
    /**
     * @description: 删除课程老师信息

     * @return: void

     */
    @ApiOperation("删除课程老师信息")
    @DeleteMapping("/course/{courseID}/{teacherID}")
    public void deleteTeacher(@PathVariable Long courseID, @PathVariable Long teacherID){
        courseTeacherService.deleteCourseTeacher(courseID,teacherID);
    }
}
