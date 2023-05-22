package org.jeecg.modules.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.entity.AssessmentIndicators;
import org.jeecg.modules.entity.ExaminePlan;
import org.jeecg.modules.entity.MonthAssessmentTemplate;
import org.jeecg.modules.mapper.AssessmentIndicatorsMapper;
import org.jeecg.modules.mapper.ExaminePlanMapper;
import org.jeecg.modules.mapper.MonthAssessmentTemplateMapper;
import org.jeecg.modules.service.IExaminePlanService;
import org.jeecg.modules.vo.ExaminePlanVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 考核计划表
 * @Author: jeecg-boot
 * @Date: 2023-05-19
 * @Version: V1.0
 */
@Service
public class ExaminePlanServiceImpl extends ServiceImpl<ExaminePlanMapper, ExaminePlan> implements IExaminePlanService {

    @Resource
    private MonthAssessmentTemplateMapper templateMapper;
    @Resource
    private AssessmentIndicatorsMapper indicatorsMapper;

    @Override
    public IPage<ExaminePlanVO> pageExtend(IPage<ExaminePlan> page) {
        IPage<ExaminePlanVO> newPage = new Page<ExaminePlanVO>();
        newPage.setPages(page.getPages());
        newPage.setCurrent(page.getCurrent());
        newPage.setSize(page.getSize());
        newPage.setTotal(page.getTotal());
        List<ExaminePlan> records = page.getRecords();
        List<ExaminePlanVO> voRecords = new ArrayList<>();
        for (ExaminePlan p : records
        ) {
            ExaminePlanVO vo = new ExaminePlanVO();
            BeanUtils.copyProperties(p, vo);
            MonthAssessmentTemplate template = templateMapper.selectById(p.getTemplateId());
            vo.setTemplate(template);

            AssessmentIndicators indicators = indicatorsMapper.selectById(p.getIndicatorId());
            vo.setIndicators(indicators);
            voRecords.add(vo);
        }
        newPage.setRecords(voRecords);
        return newPage;
    }
}
