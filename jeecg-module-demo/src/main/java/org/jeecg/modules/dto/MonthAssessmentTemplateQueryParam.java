package org.jeecg.modules.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: Linker
 * @Date: 2023/5/17 15:01
 * @Description:
 */
@Data
public class MonthAssessmentTemplateQueryParam {
    @ApiModelProperty(value = "所属部门",required = true)
    private String sysOrgCode;
}
