package org.jeecg.modules.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 考核指标表
 * @Author: jeecg-boot
 * @Date:   2023-05-16
 * @Version: V1.0
 */
@Data
@TableName("assessment_indicators")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="assessment_indicators对象", description="考核指标表")
public class AssessmentIndicators implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private String sysOrgCode;
	/**月考核模板ID*/
	@Excel(name = "月考核模板ID", width = 15)
    @ApiModelProperty(value = "月考核模板ID")
    private String templateId;
	/**指标名称*/
	@Excel(name = "指标名称", width = 15)
    @ApiModelProperty(value = "指标名称")
    private String name;
	/**指标说明*/
	@Excel(name = "指标说明", width = 15)
    @ApiModelProperty(value = "指标说明")
    private String illustrate;
	/**指标规则*/
	@Excel(name = "指标规则", width = 15)
    @ApiModelProperty(value = "指标规则")
    private String rule;
	/**分数*/
	@Excel(name = "分数", width = 15)
    @ApiModelProperty(value = "分数")
    private Double score;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private String remark;
	/**个人评分占比*/
	@Excel(name = "个人评分占比", width = 15)
    @ApiModelProperty(value = "个人评分占比")
    private Double selfScoreProportion;
	/**主管评分占比*/
	@Excel(name = "主管评分占比", width = 15)
    @ApiModelProperty(value = "主管评分占比")
    private Double manageScoreProportion;

    @TableLogic
    @ApiModelProperty(value = "逻辑删除")
    private Integer delFlag;
}
