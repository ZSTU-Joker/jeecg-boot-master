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
 * @Description: 考核等级表
 * @Author: jeecg-boot
 * @Date: 2023-05-18
 * @Version: V1.0
 */
@Data
@TableName("examine_grade")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "examine_grade对象", description = "考核等级表")
public class ExamineGrade implements Serializable {
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
     * 考核模板ID
     */
    @Excel(name = "考核模板ID", width = 15)
    @ApiModelProperty(value = "考核模板ID")
    private String templateId;
    /**
     * 分数左区间
     */
    @Excel(name = "分数左区间", width = 15)
    @ApiModelProperty(value = "分数左区间")
    private Double leftScore;
    /**
     * 分数右区间
     */
    @Excel(name = "分数右区间", width = 15)
    @ApiModelProperty(value = "分数右区间")
    private Double rightScore;
    /**
     * 删除标记
     */
    @Excel(name = "删除标记", width = 15)
    @ApiModelProperty(value = "删除标记")
    @TableLogic
    private Integer delFlag;
    @Excel(name = "等级名称", width = 15)
    @ApiModelProperty(value = "等级名称")
    private String gradeName;
}
