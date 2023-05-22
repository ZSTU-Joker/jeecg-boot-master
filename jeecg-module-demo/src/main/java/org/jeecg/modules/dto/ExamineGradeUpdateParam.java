package org.jeecg.modules.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: Linker
 * @Date: 2023/5/18 10:36
 * @Description:
 */
@Data
public class ExamineGradeUpdateParam {

    @ApiModelProperty(value = "月考核模板ID")
    private String MonthAssessmentTemplateId;

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "分数区间,低,下标一一对应")
    private Double leftScore;
    @ApiModelProperty(value = "分数区间,高,下标一一对应")
    private Double rightScore;
    @ApiModelProperty(value = "等级名称,下标一一对应")
    private String gradeName;

}

