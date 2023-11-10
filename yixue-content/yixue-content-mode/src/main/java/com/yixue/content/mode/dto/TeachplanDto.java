package com.yixue.content.mode.dto;

import com.yixue.content.mode.po.Teachplan;
import com.yixue.content.mode.po.TeachplanMedia;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * TeachplanDto$
 *
 * @author liy
 * @date 2023/11/8$
 */
@Data
@ToString
public class TeachplanDto extends Teachplan {
    List<Teachplan> teachPlanTreeNodes;
    TeachplanMedia teachplanMedia;
}
