package com.yixue.content.api;

import com.yixue.content.mode.dto.CourseCategoryTreeDto;
import com.yixue.content.service.CourseCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * CourseCategoryController$
 *
 * @author liy
 * @date 2023/11/6$
 */
@Slf4j
@RestController
@Api(value = "课程分类树结构接口",tags = "课程分类树结构接口")
@RequestMapping("/course-category")
public class CourseCategoryController {

    @Autowired
    private CourseCategoryService courseCategoryService;
    @ApiOperation("课程分类接口")
    @GetMapping("/tree-nodes")
    public List<CourseCategoryTreeDto> queryTreeNode(){
        List<CourseCategoryTreeDto> courseCategoryTreeDtoList = courseCategoryService.queryTreeNodes("1");
        return courseCategoryTreeDtoList;
    }
}
