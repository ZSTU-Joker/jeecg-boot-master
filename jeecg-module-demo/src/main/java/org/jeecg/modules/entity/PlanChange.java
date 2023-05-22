package org.jeecg.modules.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 计划变更
 * @Author: jeecg-boot
 * @Date:   2023-05-16
 * @Version: V1.0
 */
@Data
@TableName("plan_change")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="plan_change对象", description="计划变更")
public class PlanChange implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;
	/**计划表id*/
	@Excel(name = "计划表id", width = 15)
    @ApiModelProperty(value = "计划表id")
    private String planId;
	/**变更前内容*/
	@Excel(name = "变更前内容", width = 15)
    @ApiModelProperty(value = "变更前内容")
    private String beforeChange;
	/**变更后内容*/
	@Excel(name = "变更后内容", width = 15)
    @ApiModelProperty(value = "变更后内容")
    private String afterChange;
	/**申请变更时间*/
	@Excel(name = "申请变更时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "申请变更时间")
    private Date changeTime;
	/**主管审核时间*/
	@Excel(name = "主管审核时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "主管审核时间")
    private Date chargeCheckTime;
	/**行政审核时间*/
	@Excel(name = "行政审核时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "行政审核时间")
    private Date officeCheckTime;
	/**状态*/
	@Excel(name = "状态", width = 15)
    @ApiModelProperty(value = "状态")
    @Dict(dicCode = "plan_status")
    private String status;
	/**创建人登录部门编号*/
    @ApiModelProperty(value = "创建人登录部门编号")
    private String sysOrgCode;
	/**创建人登录账号*/
    @ApiModelProperty(value = "创建人登录账号")
    private String createBy;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
	/**创建人账号*/
    @ApiModelProperty(value = "创建人账号")
    private String updateBy;
	/**更新时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
	/**删除标志：0-存在，1-删除*/
	@Excel(name = "删除标志：0-存在，1-删除", width = 15)
    @ApiModelProperty(value = "删除标志：0-存在，1-删除")
    @TableLogic
    private Integer delFlag;
}
