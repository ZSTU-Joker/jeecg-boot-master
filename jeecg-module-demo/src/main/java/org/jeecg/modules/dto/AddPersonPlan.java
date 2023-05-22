package org.jeecg.modules.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.modules.entity.PersonPlan;
import org.jeecg.modules.entity.PlanDetail;

import java.util.List;

/**
 * 功能描述
 *
 * @author: 黄兴怡
 * @date: 2023年05月16日
 */
@Data
public class AddPersonPlan {
    /**
     * 计划表基本信息
     */
    @ApiModelProperty(value = "计划表基本信息")
    private PersonPlan personPlan;

    /**
     * 计划表项列表
     */
    @ApiModelProperty(value = "计划表项列表")
    private List<PlanDetail> planDetailList;

    /**
     * 是否要提交:0-不提交，1-提交
     */
    @ApiModelProperty(value = "是否要提交:0-不提交，1-提交")
    private Integer submitFlag;
}
