package com.yixue.content.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yixue.base.exception.YixueException;
import com.yixue.base.mode.PageParams;
import com.yixue.content.mapper.*;
import com.yixue.content.mode.dto.*;
import com.yixue.content.mode.po.*;
import com.yixue.content.service.CourseBaseInfoService;
import com.yixue.content.service.CourseTeacherService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * CourseBaseInfoServiceImpl$
 *
 * @author liy
 * @date 2023/11/5$
 */
@Service
public class CourseBaseInfoServiceImpl implements CourseBaseInfoService {

    @Autowired
    private CourseBaseMapper courseBaseMapper;

    @Autowired
    private CourseMarketMapper courseMarketMapper;

    @Autowired
    private CourseCategoryMapper courseCategoryMapper;

    @Autowired
    private CourseTeacherMapper courseTeacherMapper;

    @Autowired
    private TeachplanMapper teachplanMapper;

    @Autowired
    private TeachplanMediaMapper teachplanMediaMapper;
    @Override
    public PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto queryCourseParamsDto) {
        LambdaQueryWrapper<CourseBase> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(queryCourseParamsDto.getCourseName()),CourseBase::getName,queryCourseParamsDto.getCourseName());
        queryWrapper.eq(StringUtils.isNotEmpty(queryCourseParamsDto.getAuditStatus()),CourseBase::getAuditStatus,queryCourseParamsDto.getAuditStatus());
        queryWrapper.eq(StringUtils.isNotEmpty(queryCourseParamsDto.getPublishStatus()),CourseBase::getPic,queryCourseParamsDto.getPublishStatus());
        Page<CourseBase> page = new Page<>(pageParams.getPageNo(), pageParams.getPageSize());
        Page<CourseBase> pageResult = courseBaseMapper.selectPage(page,queryWrapper);
        List<CourseBase> list = pageResult.getRecords();
        PageResult<CourseBase> result = new PageResult<>(list,pageResult.getTotal(), pageParams.getPageNo(), pageParams.getPageSize());
        return result;
    }

    @Override
    @Transactional
    public CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto dto) {
        //合法性校验
        if(StringUtils.isBlank(dto.getName())){
            throw new YixueException("课程名称为空");
        }

        if (StringUtils.isBlank(dto.getMt())) {
            throw new YixueException("课程分类为空");
        }

        if (StringUtils.isBlank(dto.getSt())) {
            throw new YixueException("课程分类为空");
        }

        if (StringUtils.isBlank(dto.getGrade())) {
            throw new YixueException("课程等级为空");
        }

        if (StringUtils.isBlank(dto.getTeachmode())) {
            throw new YixueException("教育模式为空");
        }

        if (StringUtils.isBlank(dto.getUsers())) {
            throw new YixueException("适应人群为空");
        }

        if (StringUtils.isBlank(dto.getCharge())) {
            throw new YixueException("收费规则为空");
        }
        CourseBase courseBase = new CourseBase();
        //对象复制
        BeanUtils.copyProperties(dto,courseBase);
        //设置审核状态
        courseBase.setAuditStatus("202002");
        //设置发布状态
        courseBase.setStatus("203001");
        //机构id
        courseBase.setCompanyId(companyId);
        //插入时间
        courseBase.setCreateDate(LocalDateTime.now());
        //插入课程基本信息表
        int insert = courseBaseMapper.insert(courseBase);
        if(insert<=0) {
            throw new YixueException("新增课程基本信息失败");
        }
        CourseMarket courseMarket = new CourseMarket();
        Long id = courseBase.getId();
        BeanUtils.copyProperties(dto,courseMarket);
        courseMarket.setId(id);
        int marketInsert = saveCourseMarket(courseMarket);
        if (marketInsert <= 0) {
            throw new YixueException("新增课程营销失败");
        }
        return getCourseBaseInfo(id);
    }

    private int saveCourseMarket(CourseMarket courseMarket){
        //收费规则判断
        String charge = courseMarket.getCharge();
        if (StringUtils.isBlank(charge)) {
            throw new YixueException("没有收费规则");
        }
        if ("201001".equals(charge)){
            if (courseMarket.getPrice() == null || courseMarket.getPrice() <= 0){
                YixueException.cast("收费不能为空或者小于等于0");
            }
        }
        CourseMarket courseBaseObj = courseMarketMapper.selectById(courseMarket.getId());
        if (courseBaseObj == null) {
            return courseMarketMapper.insert(courseMarket);
        } else {
            BeanUtils.copyProperties(courseMarket,courseBaseObj);
            courseBaseObj.setId(courseMarket.getId());
            return courseMarketMapper.updateById(courseBaseObj);
        }
    }

    //返回课程基本信息
    public CourseBaseInfoDto getCourseBaseInfo(Long courseId) {
        CourseBaseInfoDto resDto = new CourseBaseInfoDto();
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        if (courseBase ==null) {
            return null;
        }
        BeanUtils.copyProperties(courseBase,resDto);
        CourseMarket courseMarket = courseMarketMapper.selectById(courseId);
        if (courseMarket != null) {
            BeanUtils.copyProperties(courseMarket,resDto);
        }
        //查询分类名称
        CourseCategory courseCategory = courseCategoryMapper.selectById(courseBase.getSt());
        resDto.setStName(courseCategory.getName());
        CourseCategory courseCategoryByMt = courseCategoryMapper.selectById(courseBase.getMt());
        resDto.setMtName(courseCategoryByMt.getName());
        return resDto;
    }

    @Override
    @Transactional
    public CourseBaseInfoDto updateCourseBase(Long companyId,EditCourseDto editCourseDto) {
        Long id = editCourseDto.getId();
        CourseBase courseBase = courseBaseMapper.selectById(id);
        if (courseBase == null) {
            YixueException.cast("该课程不存在");
        }
        if (!courseBase.getCompanyId().equals(companyId)) {
            YixueException.cast("只能修改自己机构的课程");
        }
        BeanUtils.copyProperties(editCourseDto,courseBase);
        courseBase.setCreateDate(LocalDateTime.now());
        int courseBaseResult = courseBaseMapper.updateById(courseBase);
        if (courseBaseResult <= 0) {
            YixueException.cast("课程更新失败");
        }
        CourseMarket market = courseMarketMapper.selectById(id);
        BeanUtils.copyProperties(editCourseDto,market);
        int marketResult = courseMarketMapper.updateById(market);
        if(marketResult <= 0) {
            YixueException.cast("课程更新失败");
        }
        return getCourseBaseInfo(id);
    }

    @Override
    @Transactional
    public void deleteCourseBase(Long companyId, Long courseId) {
        if (courseId == null) {
            return;
        }
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        if (!courseBase.getCompanyId().equals(companyId)){
            return;
        }
        //删除基本课程信息表
        courseBaseMapper.deleteById(courseId);
        //删除课程营销表
        courseMarketMapper.deleteById(courseId);
        //删除课程老师表
        LambdaQueryWrapper<CourseTeacher> courseTeacherWrapper = new LambdaQueryWrapper<>();
        courseTeacherWrapper.eq(CourseTeacher::getCourseId,courseId);
        courseTeacherMapper.delete(courseTeacherWrapper);
        //删除课程章节表
        LambdaQueryWrapper<Teachplan> teachplanWrapper = new LambdaQueryWrapper<>();
        teachplanWrapper.eq(Teachplan::getCourseId,courseId);
        List<Teachplan> teachplans = teachplanMapper.selectList(teachplanWrapper);

        teachplanMapper.delete(teachplanWrapper);
        //删除课程媒体表
        List<Long> teachplanIdList = new ArrayList<>();
        teachplans.stream().forEach(item->{
            teachplanIdList.add(item.getId());
        });
        LambdaQueryWrapper<TeachplanMedia> teachplanMediaWrapper = new LambdaQueryWrapper<>();
        teachplanMediaWrapper.in(TeachplanMedia::getCourseId,teachplanIdList);
        teachplanMediaMapper.delete(teachplanMediaWrapper);
    }

}
