package org.jeecg.modules.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: opterat_record
 * @Author: jeecg-boot
 * @Date:   2023-05-17
 * @Version: V1.0
 */
@Data
@TableName("opterat_record")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="opterat_record对象", description="opterat_record")
public class OperatRecord implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;
	/**计划表id*/
	@Excel(name = "计划表id", width = 15)
    @ApiModelProperty(value = "计划表id")
    private String planId;
	/**内容*/
	@Excel(name = "内容", width = 15)
    @ApiModelProperty(value = "内容")
    private String content;
	/**类型：0-操作，1-评论*/
	@Excel(name = "类型：0-操作，1-评论", width = 15)
    @ApiModelProperty(value = "类型：0-操作，1-评论")
    private String type;
	/**是否变更：0-发起变更， 1-没有变更*/
	@Excel(name = "是否变更：0-发起变更， 1-没有变更", width = 15)
    @ApiModelProperty(value = "是否变更：0-发起变更， 1-没有变更")
    private String changeFlag;
	/**变更id*/
	@Excel(name = "变更id", width = 15)
    @ApiModelProperty(value = "变更id")
    private String changeId;
	/**创建人账号*/
    @ApiModelProperty(value = "创建人账号")
    private String createBy;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;
	/**更新人账号*/
    @ApiModelProperty(value = "更新人账号")
    private String updateBy;
	/**更新时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "更新时间")
    private java.util.Date updateTime;
	/**操作人部门*/
    @ApiModelProperty(value = "操作人部门")
    private String sysOrgCode;
	/**删除标志：0-删除，1-存在*/
	@Excel(name = "删除标志：0-删除，1-存在", width = 15)
    @ApiModelProperty(value = "删除标志：0-删除，1-存在")
    @TableLogic
    private Integer delFlag;
}
