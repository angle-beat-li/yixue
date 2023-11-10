package com.yixue.content.service;

import com.yixue.content.mode.dto.SaveTeachplanDto;
import com.yixue.content.mode.dto.TeachplanDto;

import java.util.List;

/**
 * TeachplanService$课程基本信息管理业务接口
 *
 * @author liy
 * @date 2023/11/8$
 */
public interface TeachplanService {
    /**
     * @description: 查询课程计划树型结构

     * @return: List<Teachplan>

     */
    List<TeachplanDto> findTeachplanTree(long courseId);
    /**
     * @description: 新增课程计划

     * @return: void

     */
    void saveTeachplan(SaveTeachplanDto saveTeachplanDto);
    /**
     * @description: 删除课程章节

     * @return: void

     */
    void deleteTeachplan(Long id);

    /**
     * @description: 上移章节

     * @return: void

     */
    void moveUp(Long id);

    /**
     * @description: 下移章节

     * @return: void

     */
    void moveDown(Long id);
}
