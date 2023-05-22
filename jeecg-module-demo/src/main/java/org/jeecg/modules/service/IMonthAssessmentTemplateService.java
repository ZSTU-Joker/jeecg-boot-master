package org.jeecg.modules.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.dto.AssessmentIndicatorsAddParam;
import org.jeecg.modules.dto.MonthAssessmentTemplateAddParam;
import org.jeecg.modules.entity.MonthAssessmentTemplate;
import org.jeecg.modules.vo.MonthAssessmentTemplateWithIndicatorsVO;

import java.util.List;

/**
 * @Description: 月考核模板
 * @Author: jeecg-boot
 * @Date: 2023-05-16
 * @Version: V1.0
 */
public interface IMonthAssessmentTemplateService extends IService<MonthAssessmentTemplate> {


    IPage<MonthAssessmentTemplateWithIndicatorsVO> pageAppendIndicators(IPage<MonthAssessmentTemplate> page);

    /**
     * save the template and delete others.
     *
     * @param template
     * @return
     */
    boolean saveSingleData(MonthAssessmentTemplateAddParam template, List<AssessmentIndicatorsAddParam> indicators);

    boolean editSingleData(MonthAssessmentTemplate template, List<AssessmentIndicatorsAddParam> indicators);

    MonthAssessmentTemplateWithIndicatorsVO queryOne(String templateId);

    List<MonthAssessmentTemplate> selectPersonalTemplate();


}
