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
 * @Description: 个人计划
 * @Author: jeecg-boot
 * @Date:   2023-05-16
 * @Version: V1.0
 */
@Data
@TableName("person_plan")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="person_plan对象", description="个人计划")
public class PersonPlan implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;
	/**计划年月*/
	@Excel(name = "计划年月", width = 15)
    @ApiModelProperty(value = "计划年月")
    private String yearAndMonth;
	/**状态*/
	@Excel(name = "状态", width = 15)
    @ApiModelProperty(value = "状态")
    @Dict(dicCode = "plan_status")
    private String status;
	/**自评分*/
	@Excel(name = "自评分", width = 15)
    @ApiModelProperty(value = "自评分")
    private Integer personScore;
	/**主管评分*/
	@Excel(name = "主管评分", width = 15)
    @ApiModelProperty(value = "主管评分")
    private Integer chargeScore;
	/**考核等级*/
	@Excel(name = "考核等级", width = 15)
    @ApiModelProperty(value = "考核等级")
    private String grade;
	/**总体评价*/
	@Excel(name = "总体评价", width = 15)
    @ApiModelProperty(value = "总体评价")
    private String evaluation;
	/**创建人账号*/
    @ApiModelProperty(value = "创建人账号")
    private String createBy;
    /**创建人真实姓名*/
    @ApiModelProperty(value = "创建人真实姓名")
    private String createRealname;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
	/**更新人账号*/
    @ApiModelProperty(value = "更新人账号")
    private String updateBy;
	/**更新时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
	/**创建人登录部门编号*/
    @ApiModelProperty(value = "创建人登录部门编号")
    @Dict(dicCode = "org_code", dictTable = "sys_depart", dicText = "depart_name")
    private String sysOrgCode;
	/**删除标志：0-存在， 1-删除*/
	@Excel(name = "删除标志：0-存在， 1-删除", width = 15)
    @ApiModelProperty(value = "删除标志：0-存在， 1-删除")
    private Integer delFlag;
    /**主管审核内容*/
    @ApiModelProperty(value = "主管审核内容")
    private String chargeCheckContent;
    /**主管审核内容*/
    @ApiModelProperty(value = "主管审核内容")
    private String administrationCheckContent;
}
