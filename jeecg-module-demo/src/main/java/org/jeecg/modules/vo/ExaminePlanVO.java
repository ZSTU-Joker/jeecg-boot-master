package org.jeecg.modules.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.modules.entity.AssessmentIndicators;
import org.jeecg.modules.entity.MonthAssessmentTemplate;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Author: Linker
 * @Date: 2023/5/19 10:53
 * @Description:
 */
@Data
public class ExaminePlanVO {
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
    @ApiModelProperty(value = "部门名称")
    private String sysOrgName;
    /**
     * 被考核人员ID
     */
    @ApiModelProperty(value = "被考核人员ID")
    private String userId;
    /**
     * 考核人员名称
     */
    @ApiModelProperty(value = "考核人员名称")
    private String userName;
    /**
     * 月考核模板ID
     */
    @ApiModelProperty(value = "月考核模板ID")
    private String templateId;

    @ApiModelProperty(value = "月考核模板")
    private MonthAssessmentTemplate template;
    /**
     * 考核等级ID
     */
    @ApiModelProperty(value = "考核等级（规则）ID")
    private String gradeId;
    /**
     * 考核指标ID
     */
    @ApiModelProperty(value = "考核指标ID")
    private String indicatorId;

    @ApiModelProperty(value = "考核指标")
    private AssessmentIndicators indicators;
    /**
     * 考核人员ID
     */
    @ApiModelProperty(value = "考核评分人员ID")
    private String managerId;

    @ApiModelProperty(value = "考核人员名称")
    private String managerName;

}
