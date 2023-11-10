package com.yixue.content.api;

import com.yixue.content.mode.dto.SaveTeachplanDto;
import com.yixue.content.mode.dto.TeachplanDto;
import com.yixue.content.service.TeachplanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TeachplanController$
 *
 * @author liy
 * @date 2023/11/8$
 */
@RestController
@Slf4j
@RequestMapping("/teachplan")
@Api(value = "TeachplanController", tags = "课程计划接口")
public class TeachplanController {
    @Autowired
    TeachplanService teachplanService;

    /**
     * @description: 查询课程计划树形结构

     * @return: List<TeachplanDto>

     */
    @ApiOperation("查询课程计划树形结构")
    @ApiImplicitParam(value = "courseId",name = "课程基础Id值",required = true,dataType = "Long",paramType = "path")
    @GetMapping("/{courseId}/tree-nodes")
    public List<TeachplanDto> getTreeNodes(@PathVariable Long courseId){
        return teachplanService.findTeachplanTree(courseId);
    }
    /**
     * @description: 新增章节接口

     * @return: void

     */
    @ApiOperation("新增修改章节接口")
    @PostMapping
    public void saveTeachplan(@RequestBody SaveTeachplanDto saveTeachplanDto) {
        teachplanService.saveTeachplan(saveTeachplanDto);
    }
    /**
     * @description: 删除章节接口

     * @return: void

     */
    @ApiOperation("删除章节接口")
    @DeleteMapping("/{id}")
    public void deleteTeachplan(@PathVariable Long id) {
        teachplanService.deleteTeachplan(id);
    }

    /**
     * @description: 向下移动章节

     * @return: void

     */
    @ApiOperation("章节下移动接口")
    @PostMapping("/movedown/{id}")
    public void moveDown(@PathVariable  Long id){
        teachplanService.moveDown(id);
    }

    @ApiOperation("章节上移接口")
    @PostMapping("/moveup/{id}")
    public void moveUp(@PathVariable Long id) {
        teachplanService.moveUp(id);
    }
}
