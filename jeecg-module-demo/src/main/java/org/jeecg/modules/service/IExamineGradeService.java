package org.jeecg.modules.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.dto.ExamineGradeUpdateParam;
import org.jeecg.modules.entity.ExamineGrade;

import java.util.List;

/**
 * @Description: 考核等级表
 * @Author: jeecg-boot
 * @Date: 2023-05-18
 * @Version: V1.0
 */
public interface IExamineGradeService extends IService<ExamineGrade> {
    boolean insertForGrades(List<Double> leftScores, List<Double> rightScores, List<String> gradeNames, String templateId);

    boolean updateForGrades( ExamineGradeUpdateParam param);


}
