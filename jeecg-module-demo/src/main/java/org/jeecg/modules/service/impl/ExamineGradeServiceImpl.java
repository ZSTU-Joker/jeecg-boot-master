package org.jeecg.modules.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.modules.dto.ExamineGradeUpdateParam;
import org.jeecg.modules.entity.ExamineGrade;
import org.jeecg.modules.mapper.ExamineGradeMapper;
import org.jeecg.modules.service.IExamineGradeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @Description: 考核等级表
 * @Author: jeecg-boot
 * @Date: 2023-05-18
 * @Version: V1.0
 */
@Service
public class ExamineGradeServiceImpl extends ServiceImpl<ExamineGradeMapper, ExamineGrade> implements IExamineGradeService {

    @Override
    public boolean insertForGrades(List<Double> leftScores, List<Double> rightScores, List<String> gradeNames, String templateId) {
        if (leftScores.size() != rightScores.size() || leftScores.size() != gradeNames.size()) {
            throw new JeecgBootException("数据数量异常");
        }
        //check size
        checkSize(leftScores, rightScores);
        //insert into
        List<ExamineGrade> grades = new ArrayList<>();
        for (int i = 0; i < leftScores.size(); i++) {
            ExamineGrade grade = new ExamineGrade();
            grade.setLeftScore(leftScores.get(i));
            grade.setRightScore(rightScores.get(i));
            grade.setGradeName(gradeNames.get(i));
            grade.setTemplateId(templateId);
            grades.add(grade);
        }
        this.saveBatch(grades);
        return true;
    }

    @Override
    @Transactional
    public boolean updateForGrades(ExamineGradeUpdateParam param) {
        ExamineGrade examineGrade = baseMapper.selectById(param.getId());
        examineGrade.setLeftScore(param.getLeftScore());
        examineGrade.setRightScore(param.getRightScore());
        examineGrade.setGradeName(param.getGradeName());
        //check
        baseMapper.updateById(examineGrade);
        updateCheckSize(examineGrade);
        return true;
    }

    public boolean updateCheckSize(ExamineGrade grade) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("template_id", grade.getTemplateId());
        List<ExamineGrade> examineGradeList = baseMapper.selectList(queryWrapper);
        if (examineGradeList.size() == 0) {
            throw new JeecgBootException("数据异常");
        }
        examineGradeList = sort(examineGradeList);
        for (int i = 0; i < examineGradeList.size() - 1; i++) {
            if (examineGradeList.get(i).getRightScore() >= examineGradeList.get(i + 1).getLeftScore()) {
                throw new JeecgBootException("数据异常");
            }
        }
        double min = examineGradeList.get(0).getLeftScore();
        double max = examineGradeList.get(examineGradeList.size() - 1).getRightScore();
        for (int i = 0; i < examineGradeList.size(); i++) {
            if (examineGradeList.get(i).getLeftScore() < min || examineGradeList.get(i).getRightScore() > max) {
                throw new JeecgBootException("数据异常");
            }
        }
        return true;

    }

    private List<ExamineGrade> sort(List<ExamineGrade> grades) {
        Collections.sort(grades, new Comparator<ExamineGrade>() {
            @Override
            public int compare(ExamineGrade o1, ExamineGrade o2) {
                if ((o1.getLeftScore() - o2.getLeftScore()) < 0) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
        return grades;
    }


    public boolean checkSize(List<Double> leftScores, List<Double> rightScores) {
        if (leftScores.size() != rightScores.size()) {
            throw new JeecgBootException("数据数量异常");
        }
        for (int i = 0; i < leftScores.size() - 1; i++) {
            if (leftScores.get(i) >= leftScores.get(i + 1)) {
                throw new JeecgBootException("数据异常，左区间异常");
            }
        }
        for (int i = 0; i < rightScores.size() - 1; i++) {
            if (rightScores.get(i) >= rightScores.get(i + 1)) {
                throw new JeecgBootException("数据异常,右区间异常");
            }
        }
        for (int i = 0; i < leftScores.size(); i++) {
            if (leftScores.get(i) > rightScores.get(i)) {
                throw new JeecgBootException("数据异常，左区间需要小于等于右区间");
            }
        }
        return true;
    }
}
