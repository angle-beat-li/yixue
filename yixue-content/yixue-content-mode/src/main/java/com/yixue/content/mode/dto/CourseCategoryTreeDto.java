package com.yixue.content.mode.dto;

import com.yixue.content.mode.po.CourseCategory;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * CourseCategoryTreeDto$
 *
 * @author liy
 * @date 2023/11/6$
 */
@Data
public class CourseCategoryTreeDto  extends CourseCategory implements Serializable {
    List<CourseCategoryTreeDto>  childrenTreeNodes;
}
