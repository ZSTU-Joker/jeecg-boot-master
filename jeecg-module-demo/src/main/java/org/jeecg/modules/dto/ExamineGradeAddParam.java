package org.jeecg.modules.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: Linker
 * @Date: 2023/5/18 8:48
 * @Description:
 */
@Data
public class ExamineGradeAddParam {
    @ApiModelProperty(value = "月考核模板ID")
    private String MonthAssessmentTemplateId;

    @ApiModelProperty(value = "分数区间,低,下标一一对应")
    private List<Double> leftScores;
    @ApiModelProperty(value = "分数区间,高,下标一一对应")
    private List<Double> rightScores;
    @ApiModelProperty(value = "等级名称,下标一一对应")
    private List<String> gradeNames;

}

