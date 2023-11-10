package com.yixue.content.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yixue.content.mode.dto.TeachplanDto;
import com.yixue.content.mode.po.Teachplan;

import java.util.List;

/**
 * <p>
 * 课程计划 Mapper 接口
 * </p>
 *
 * @author liy
 */
public interface TeachplanMapper extends BaseMapper<Teachplan> {
    List<TeachplanDto> selectTreeNodes(Long courseid);
}
