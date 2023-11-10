package com.yixue.content.service.Impl;

import com.yixue.content.mapper.CourseCategoryMapper;
import com.yixue.content.mode.dto.CourseCategoryTreeDto;
import com.yixue.content.service.CourseCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * CourseCategoryImpl$
 *
 * @author liy
 * @date 2023/11/6$
 */
@Service
public class CourseCategoryServiceImpl implements CourseCategoryService {

    @Autowired
    public CourseCategoryMapper courseCategoryMapper;
    @Override
    public List<CourseCategoryTreeDto> queryTreeNodes(String id) {
        List<CourseCategoryTreeDto> courseCategoryTreeDtoList =courseCategoryMapper.selectTreeNode(id);
        //将所有节点转换成map，并排除根节点
        Map<String,CourseCategoryTreeDto> treeDtoMap =
                courseCategoryTreeDtoList.stream().filter(key->!id.equals(key.getId())).
                        collect(Collectors.toMap(key->key.getId(),value->value,(key1,key2)->key1));
        //最终返回的List
        List<CourseCategoryTreeDto> result = new ArrayList<>();
        courseCategoryTreeDtoList.stream().filter(item->!id.equals(item.getId())).forEach(item->{
            if (item.getParentid().equals(id)){
                result.add(item);
            }
            CourseCategoryTreeDto courseCategoryTreeDto = treeDtoMap.get(item.getParentid());
            if (courseCategoryTreeDto != null) {
                if (courseCategoryTreeDto.getChildrenTreeNodes() == null) {
                    courseCategoryTreeDto.setChildrenTreeNodes(new ArrayList<>());
                }
                courseCategoryTreeDto.getChildrenTreeNodes().add(item);
            }

        });
        return result;
    }
}
