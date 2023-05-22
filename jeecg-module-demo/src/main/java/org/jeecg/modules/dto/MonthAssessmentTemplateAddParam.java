package org.jeecg.modules.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.util.List;

/**
 * @Author: Linker
 * @Date: 2023/5/17 9:52
 * @Description:
 */
@Data
public class MonthAssessmentTemplateAddParam {
    /**
     * 所属部门
     */
    @ApiModelProperty(value = "所属部门(下拉筛选框)", required = true)
    private String sysOrgCode;
    /**
     * 模板名称
     */
    @Excel(name = "模板名称", width = 15)
    @ApiModelProperty(value = "模板名称", required = true)
    private String name;
    /**
     * 模板编号
     */
    @Excel(name = "模板编号", width = 15)
    @ApiModelProperty(value = "模板编号")
    private String code;
    /**
     * 适用部门
     */
    @Excel(name = "适用部门(下拉筛选框)", width = 15)
    @ApiModelProperty(value = "适用部门", required = true)
    private String department;
    /**
     * 状态
     */
//    @Excel(name = "状态", width = 15)
//    @ApiModelProperty(value = "状态")
    private String status;
    /**
     * 备注
     */
    @Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "考核指标列表", required = true)
    private List<AssessmentIndicatorsAddParam> indicatorsList;
}
