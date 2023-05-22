package org.jeecg.modules.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.modules.entity.AssessmentIndicators;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @Author: Linker
 * @Date: 2023/5/17 10:41
 * @Description:
 */
@Data
public class MonthAssessmentTemplateWithIndicatorsVO {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private String id;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createBy;
    /**
     * 创建日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private String updateBy;
    /**
     * 更新日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;
    /**
     * 所属部门
     */
    @ApiModelProperty(value = "所属部门")
    private String sysOrgCode;
    /**
     * 模板名称
     */
    @Excel(name = "模板名称", width = 15)
    @ApiModelProperty(value = "模板名称")
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
    @Excel(name = "适用部门", width = 15)
    @ApiModelProperty(value = "适用部门")
    private String departments;
    /**
     * 状态
     */
    @Excel(name = "状态", width = 15)
    @ApiModelProperty(value = "状态")
    private String status;
    /**
     * 备注
     */
    @Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "考核指标列表")
    List<AssessmentIndicators> indicatorList;

}
