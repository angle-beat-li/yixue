package com.yixue.content.service;

import com.yixue.content.mode.dto.CourseCategoryTreeDto;

import java.util.List;

/**
 * CourseCategoryService$
 *
 * @author liy
 * @date 2023/11/6$
 */
public interface CourseCategoryService {
    public List<CourseCategoryTreeDto> queryTreeNodes(String id);
}
