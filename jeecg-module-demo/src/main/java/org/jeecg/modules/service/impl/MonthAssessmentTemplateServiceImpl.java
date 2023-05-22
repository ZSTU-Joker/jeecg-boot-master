package org.jeecg.modules.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.vo.LoginUser;

import org.jeecg.modules.dto.AssessmentIndicatorsAddParam;
import org.jeecg.modules.dto.MonthAssessmentTemplateAddParam;
import org.jeecg.modules.entity.AssessmentIndicators;
import org.jeecg.modules.entity.MonthAssessmentTemplate;
import org.jeecg.modules.mapper.AssessmentIndicatorsMapper;
import org.jeecg.modules.mapper.MonthAssessmentTemplateMapper;
import org.jeecg.modules.service.IMonthAssessmentTemplateService;
import org.jeecg.modules.vo.MonthAssessmentTemplateWithIndicatorsVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 月考核模板
 * @Author: jeecg-boot
 * @Date: 2023-05-16
 * @Version: V1.0
 */
@Service
public class MonthAssessmentTemplateServiceImpl extends ServiceImpl<MonthAssessmentTemplateMapper, MonthAssessmentTemplate> implements IMonthAssessmentTemplateService {

    @Resource
    private AssessmentIndicatorsMapper assessmentIndicatorsMapper;
    @Resource
    private ISysBaseAPI sysBaseAPI;

    @Override
    public IPage<MonthAssessmentTemplateWithIndicatorsVO> pageAppendIndicators(IPage<MonthAssessmentTemplate> page) {
        IPage<MonthAssessmentTemplateWithIndicatorsVO> newPage = new Page<MonthAssessmentTemplateWithIndicatorsVO>();
        newPage.setPages(page.getPages());
        newPage.setCurrent(page.getCurrent());
        newPage.setSize(page.getSize());
        newPage.setTotal(page.getTotal());
        List<MonthAssessmentTemplate> records = page.getRecords();
        List<MonthAssessmentTemplateWithIndicatorsVO> vos = new ArrayList<>();
        for (MonthAssessmentTemplate t : records
        ) {
            MonthAssessmentTemplateWithIndicatorsVO vo = new MonthAssessmentTemplateWithIndicatorsVO();
            BeanUtils.copyProperties(vo, t);
            QueryWrapper q = new QueryWrapper();
            q.eq("template_id", t.getId());
            List<AssessmentIndicators> indicators = assessmentIndicatorsMapper.selectList(q);
            vo.setIndicatorList(indicators);
            vos.add(vo);
        }
        newPage.setRecords(vos);
        return newPage;
    }

    @Override
    @Transactional
    public boolean saveSingleData(MonthAssessmentTemplateAddParam template, List<AssessmentIndicatorsAddParam> indicators) {
        MonthAssessmentTemplate tp = new MonthAssessmentTemplate();
        tp.setName(template.getName());
        tp.setCode(template.getCode());
        tp.setSysOrgCode(template.getSysOrgCode());
        tp.setRemark(template.getRemark());
        tp.setDepartments(template.getDepartment());

        //delete others and insert only one useful data
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("sys_org_code", template.getSysOrgCode());
        baseMapper.delete(queryWrapper);
        baseMapper.insert(tp);
        // part2
        this.limitIndicators(indicators);
        List<AssessmentIndicators> indicatorsList = copyIndicators(indicators);
        for (AssessmentIndicators a : indicatorsList
        ) {
            a.setTemplateId(tp.getId());
            assessmentIndicatorsMapper.insert(a);
        }
        return true;
    }

    @Override
    @Transactional
    public boolean editSingleData(MonthAssessmentTemplate template, List<AssessmentIndicatorsAddParam> indicators) {
        baseMapper.updateById(template);
        this.limitIndicators(indicators);
        List<AssessmentIndicators> indicatorsList = copyIndicators(indicators);
        for (AssessmentIndicators a : indicatorsList
        ) {
            assessmentIndicatorsMapper.updateById(a);
        }
        return true;
    }

    @Override
    public MonthAssessmentTemplateWithIndicatorsVO queryOne(String templateId) {
        MonthAssessmentTemplate t = this.getById(templateId);
        MonthAssessmentTemplateWithIndicatorsVO vo = new MonthAssessmentTemplateWithIndicatorsVO();
        BeanUtils.copyProperties(vo, t);

        QueryWrapper q = new QueryWrapper();
        q.eq("template_id", t.getId());
        List<AssessmentIndicators> indicators = assessmentIndicatorsMapper.selectList(q);
        vo.setIndicatorList(indicators);
        return vo;
    }

    @Override
    public List<MonthAssessmentTemplate> selectPersonalTemplate() {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        QueryWrapper queryWrapper = new QueryWrapper();
//        queryWrapper.eq("sys_org_code", param.getSysOrgCode());
        queryWrapper.eq("del_flag", 0);
        //普通用户获得部门下的所有信息即可
        List<String> departIdsByUsername = sysBaseAPI.getDepartIdsByUsername(sysUser.getUsername());
        queryWrapper.in("sys_org_code", departIdsByUsername);

        List<MonthAssessmentTemplate> resultList = baseMapper.selectList(queryWrapper);

        return resultList;
    }

    /**
     * the sum of scores is 100.
     *
     * @param indicators
     * @return
     */
    private boolean limitIndicators(List<AssessmentIndicatorsAddParam> indicators) {
        int sumScores = 0;
        for (AssessmentIndicatorsAddParam i : indicators
        ) {
            sumScores += i.getScore();
        }
        if (sumScores != 100) {
            throw new JeecgBootException("模板总分相加必须等于100.");
        }
        // insert into indicators
        return true;
    }

    /**
     * copy
     *
     * @param vos
     * @return
     */
    private List<AssessmentIndicators> copyIndicators(List<AssessmentIndicatorsAddParam> vos) {
        List<AssessmentIndicators> indicators = new ArrayList<>();
        for (AssessmentIndicatorsAddParam i : vos
        ) {
            AssessmentIndicators indicator = new AssessmentIndicators();
            BeanUtils.copyProperties(i, indicator);
            indicators.add(indicator);
        }
        return indicators;
    }

}
