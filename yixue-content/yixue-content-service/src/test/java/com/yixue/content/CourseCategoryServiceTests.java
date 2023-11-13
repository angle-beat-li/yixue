package com.yixue.content;

import com.yixue.content.mapper.CourseCategoryMapper;
import com.yixue.content.mode.dto.CourseCategoryTreeDto;
import com.yixue.content.mode.po.CourseCategory;
import com.yixue.content.service.CourseBaseInfoService;
import com.yixue.content.service.CourseCategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * CourseCategoryServiceTests$
 *
 * @author liy
 * @date 2023/11/6$
 */
@SpringBootTest()
class CourseCategoryServiceTests {
    @Autowired
    CourseCategoryService courseCategoryService;
    @Autowired
    CourseBaseInfoService courseBaseInfoService;

    @Autowired
    CourseCategoryMapper categoryMapper;
    @Test
    void testqueryTreeNodes() {
        List<CourseCategoryTreeDto> categoryTreeDtos =
                courseCategoryService.queryTreeNodes("1");
        System.out.println(categoryTreeDtos);
    }
    @Test
    void testCourseBase(){
        List<CourseCategoryTreeDto> courseBasePageResult = categoryMapper.selectTreeNode("1");
            System.out.println(courseBasePageResult);
    }
    @Test
    void tesCousequery(){
        CourseCategory courseCategory = categoryMapper.selectById("1");
        System.out.println(courseCategory);
    }


    @Test
    void test() {
        int x = 4;
        System.out.println(x & -x);
    }
}