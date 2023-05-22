package org.jeecg.modules.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.modules.entity.PlanDetail;

import java.util.List;

/**
 * 个人计划编辑入参
 *
 * @author: 黄兴怡
 * @date: 2023年05月16日
 */
@Data
@ApiModel(value="PlanEdit对象", description="个人计划编辑")
public class PlanEdit {

    /**
     * 计划表id
     */
    @ApiModelProperty(value = "计划表id")
    private String id;

    /**
     * 新增的计划项
     */
    @ApiModelProperty(value = "新增的计划项")
    private List<PlanDetail> addNewplanDetailList;

    /**
     * 经过编辑的计划项
     */
    @ApiModelProperty(value = "经过编辑的计划项")
    private List<PlanDetail> editPlanDetailList;

    /**
     * 需要删除的计划项id
     */
    @ApiModelProperty(value = "需要删除的计划项id")
    private List<String> delPlanDetailIdList;

    /**
     * 是否要提交:0-不提交，1-提交
     */
    @ApiModelProperty(value = "是否要提交:0-不提交，1-提交")
    private Integer submitFlag;
}