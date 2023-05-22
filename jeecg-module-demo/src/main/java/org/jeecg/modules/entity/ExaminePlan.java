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

/**
 * @Description: 考核计划表
 * @Author: jeecg-boot
 * @Date: 2023-05-19
 * @Version: V1.0
 */
@Data
@TableName("examine_plan")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "examine_plan对象", description = "考核计划表")
public class ExaminePlan implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
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
    private java.util.Date createTime;
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
    private java.util.Date updateTime;
    /**
     * 所属部门
     */
    @ApiModelProperty(value = "所属部门")
    private String sysOrgCode;
    /**
     * 部门名称
     */
    @Excel(name = "部门名称", width = 15)
    @ApiModelProperty(value = "部门名称")
    private String sysOrgName;
    /**
     * 被考核人员ID
     */
    @Excel(name = "被考核人员ID", width = 15)
    @ApiModelProperty(value = "被考核人员ID")
    private String userId;
    /**
     * 被考核人员名称
     */
    @Excel(name = "被考核人员名称", width = 15)
    @ApiModelProperty(value = "被考核人员名称")
    private String userName;
    /**
     * 月考核模板ID
     */
    @Excel(name = "月考核模板ID", width = 15)
    @ApiModelProperty(value = "月考核模板ID")
    private String templateId;
    /**
     * 考核等级ID
     */
    @Excel(name = "考核等级ID", width = 15)
    @ApiModelProperty(value = "考核等级ID")
    private String gradeId;
    /**
     * 考核指标ID
     */
    @Excel(name = "考核指标ID", width = 15)
    @ApiModelProperty(value = "考核指标ID")
    private String indicatorId;
    /**
     * 考核人员ID
     */
    @Excel(name = "考核人员ID", width = 15)
    @ApiModelProperty(value = "考核人员ID")
    private String managerId;
    /**
     * 考核人员名称
     */
    @Excel(name = "考核人员名称", width = 15)
    @ApiModelProperty(value = "考核人员名称")
    private String managerName;
    /**
     * 删除标记（0未删除，1已删除）
     */
    @Excel(name = "删除标记（0未删除，1已删除）", width = 15)
    @ApiModelProperty(value = "删除标记（0未删除，1已删除）")
    @TableLogic
    private Integer delFlag;
    /**
     * 考核年月
     */
    @Excel(name = "考核年月", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "考核年月")
    private java.util.Date examineDate;
}
