package com.yixue.content.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yixue.base.exception.YixueException;
import com.yixue.content.mapper.TeachplanMapper;
import com.yixue.content.mapper.TeachplanMediaMapper;
import com.yixue.content.mode.dto.SaveTeachplanDto;
import com.yixue.content.mode.dto.TeachplanDto;
import com.yixue.content.mode.po.Teachplan;
import com.yixue.content.mode.po.TeachplanMedia;
import com.yixue.content.service.TeachplanService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * TeachplanServiceImpl$
 *
 * @author liy
 * @date 2023/11/8$
 */
@Service
public class TeachplanServiceImpl implements TeachplanService {

    @Autowired
    private TeachplanMapper teachplanMapper;
    @Autowired
    private TeachplanMediaMapper teachplanMediaMapper;
    @Override
    public List<TeachplanDto> findTeachplanTree(long courseId) {
        return teachplanMapper.selectTreeNodes(courseId);
    }

    @Override
    public void saveTeachplan(SaveTeachplanDto saveTeachplanDto) {
        Long courseId = saveTeachplanDto.getCourseId();
        if (courseId == null) {
            YixueException.cast("课程id不能为空");
        }
        Long id = saveTeachplanDto.getId();
        if (id != null) {
            Teachplan teachplan = teachplanMapper.selectById(id);
            BeanUtils.copyProperties(saveTeachplanDto,teachplan);
            teachplanMapper.updateById(teachplan);
        } else {
            int count = getTeachplanCount(saveTeachplanDto.getCourseId(), saveTeachplanDto.getParentid());
            Teachplan teachplan = new Teachplan();
            teachplan.setOrderby(count);
            BeanUtils.copyProperties(saveTeachplanDto,teachplan);
            teachplanMapper.insert(teachplan);
        }
    }
    public int getTeachplanCount(long courseId,long parentId) {
        LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teachplan::getCourseId,courseId);
        queryWrapper.eq(Teachplan::getParentid,parentId);
        queryWrapper.orderByDesc(Teachplan::getOrderby);
        queryWrapper.last("limit 1");
        Teachplan teachplan = teachplanMapper.selectOne(queryWrapper);
        Integer count = 1;
        if (teachplan != null) {
            count += teachplan.getOrderby();
        }
        return count;
    }
    @Transactional
    public void deleteTeachplan(Long id){
        if (id ==null) {
            YixueException.cast("删除章节id不能为空");
        }
        LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teachplan::getParentid, id);
        Integer count = teachplanMapper.selectCount(queryWrapper);
        if (count != 0) {
            YixueException.cast("课程计划信息还有子级信息，无法操作");
        }
        teachplanMapper.deleteById(id);
        LambdaQueryWrapper<TeachplanMedia> teachplanMediaQueryWrapper = new LambdaQueryWrapper<>();
        teachplanMediaQueryWrapper.eq(TeachplanMedia::getTeachplanId,id);
        teachplanMediaMapper.delete(teachplanMediaQueryWrapper);
    }

    @Override
    public void moveUp(Long id) {
        move(id,-1);
    }

    @Override
    public void moveDown(Long id) {
        move(id,1);
    }
    @Transactional
    public void move(Long id, int move) {

        Teachplan teachplan = teachplanMapper.selectById(id);
        LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teachplan::getParentid,teachplan.getParentid());
        queryWrapper.orderByAsc(Teachplan::getOrderby);
        List<Teachplan> teachplans = teachplanMapper.selectList(queryWrapper);
        int len = teachplans.size();
        for (int i = 0; i < len; i++) {
            if (teachplan.getId().equals(teachplans.get(i).getId())) {
                if(i + move >= 0 && i + move < len) {
                    Teachplan teachplanTmp =teachplans.get(i + move);
                    int orderby = teachplan.getOrderby();
                    teachplan.setOrderby(teachplanTmp.getOrderby());
                    teachplanTmp.setOrderby(orderby);
                    teachplanMapper.updateById(teachplan);
                    teachplanMapper.updateById(teachplanTmp);
                }
            }
        }
    }
}
