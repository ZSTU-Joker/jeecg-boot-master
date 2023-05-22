package org.jeecg.modules.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.Data;

/**
 * 审核计划的入参
 *
 * @author: 黄兴怡
 * @date: 2023年05月17日
 */
@Data
public class CheckPlan {

    /**
     * 计划表id
     */
    @ApiModelProperty(value = "计划表id")
    private String id;

    /**
     * 审核描述
     */
    @ApiModelProperty(value = "审核描述")
    private String content;

    /**
     * 当前的计划表状态
     */
    @ApiModelProperty(value = "当前的计划表状态")
    private String status;

    /**
     * 是否通过:0-拒绝，1-通过
     */
    @ApiModelProperty(value = "是否通过:0-拒绝，1-通过")
    private Integer passFlag;
}
