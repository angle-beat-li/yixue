package com.yixue.content.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yixue.base.exception.YixueException;
import com.yixue.base.mode.PageParams;
import com.yixue.content.mapper.CourseCategoryMapper;
import com.yixue.content.mapper.CourseMarketMapper;
import com.yixue.content.mode.dto.AddCourseDto;
import com.yixue.content.mode.dto.CourseBaseInfoDto;
import com.yixue.content.mode.dto.PageResult;
import com.yixue.content.mode.dto.QueryCourseParamsDto;
import com.yixue.content.mapper.CourseBaseMapper;
import com.yixue.content.mode.po.CourseBase;
import com.yixue.content.mode.po.CourseCategory;
import com.yixue.content.mode.po.CourseMarket;
import com.yixue.content.service.CourseBaseInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    private CourseBaseInfoDto getCourseBaseInfo(long courseId) {
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
}
