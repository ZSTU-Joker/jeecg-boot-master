package org.jeecg.modules.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Author: Linker
 * @Date: 2023/5/17 9:55
 * @Description:
 */
@Data
public class AssessmentIndicatorsAddParam {
    /**
     * 所属部门
     */
    @ApiModelProperty(value = "所属部门")
    private String sysOrgCode;
    /**
     * 月考核模板ID
     */
    @Excel(name = "月考核模板ID", width = 15)
    @ApiModelProperty(value = "月考核模板ID")
    private String templateId;
    /**
     * 指标名称
     */
    @Excel(name = "指标名称", width = 15)
    @ApiModelProperty(value = "指标名称")
    private String name;
    /**
     * 指标说明
     */
    @Excel(name = "指标说明", width = 15)
    @ApiModelProperty(value = "指标说明")
    private String illustrate;
    /**
     * 指标规则
     */
    @Excel(name = "指标规则", width = 15)
    @ApiModelProperty(value = "指标规则")
    private String rule;
    /**
     * 分数
     */
    @Excel(name = "分数", width = 15)
    @ApiModelProperty(value = "分数")
    private Double score;
    /**
     * 备注
     */
    @Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private String remark;
    /**
     * 个人评分占比
     */
    @Excel(name = "个人评分占比", width = 15)
    @ApiModelProperty(value = "个人评分占比")
    private Double selfScoreProportion;
    /**
     * 主管评分占比
     */
    @Excel(name = "主管评分占比", width = 15)
    @ApiModelProperty(value = "主管评分占比")
    private Double manageScoreProportion;
}
