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
 * @Description: 计划详情
 * @Author: jeecg-boot
 * @Date:   2023-05-16
 * @Version: V1.0
 */
@Data
@TableName("plan_detail")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="plan_detail对象", description="计划详情")
public class PlanDetail implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;
	/**计划表id*/
	@Excel(name = "计划表id", width = 15)
    @ApiModelProperty(value = "计划表id")
    private String planId;
	/**级别*/
	@Excel(name = "级别", width = 15)
    @ApiModelProperty(value = "级别")
    @Dict(dicCode = "plan_level")
    private String level;
    /**级别*/
    @Excel(name = "工作项目", width = 15)
    @ApiModelProperty(value = "工作项目")
    private String content;
	/**计划完成时间*/
	@Excel(name = "计划完成时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "计划完成时间")
    private Date planFinishTime;
	/**进度*/
	@Excel(name = "进度", width = 15)
    @ApiModelProperty(value = "进度")
    private String progress;
	/**完成时间*/
	@Excel(name = "完成时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "完成时间")
    private Date actualFinishTime;
	/**完成情况*/
	@Excel(name = "完成情况", width = 15)
    @ApiModelProperty(value = "完成情况")
    private String finishInformation;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private String remark;
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
	/**更新人账号*/
    @ApiModelProperty(value = "更新人账号")
    private String updateBy;
	/**更新时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd hh:mm:dd")
    @DateTimeFormat(pattern="yyyy-MM-dd hh:mm:dd")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
	/**删除标志：0-存在，1-删除*/
	@Excel(name = "删除标志：0-存在，1-删除", width = 15)
    @ApiModelProperty(value = "删除标志：0-存在，1-删除")
    @TableLogic
    private Integer delFlag;
}
