package org.jeecg.modules.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.dto.AddPersonPlan;
import org.jeecg.modules.dto.CheckPlan;
import org.jeecg.modules.dto.PlanEdit;
import org.jeecg.modules.entity.PersonPlan;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.entity.PlanDetail;
import org.jeecg.modules.vo.PersonPlanDetail;

/**
 * @Description: 个人计划
 * @Author: jeecg-boot
 * @Date:   2023-05-16
 * @Version: V1.0
 */
public interface IPersonPlanService extends IService<PersonPlan> {

    /**
     * @author huang xingyi
     * @description: 计划表详情查询
     * @date 2023/5/16
     * @param id
     * @param page
     * @return PersonPlan
    */
     IPage<PersonPlanDetail> getPlanDetailById(String id, Page<PlanDetail> page);

    /**
     * @author huang xingyi
     * @description: 新增计划表
     * @date 2023/5/16
     * @param addPersonPlan
     * @return void
    */
    void addNewPlan(AddPersonPlan addPersonPlan);

    /**
     * @author huang xingyi
     * @description: 个人计划编辑
     * @date 2023/5/16
     * @param planEdit
     * @return void
    */
    void editById(PlanEdit planEdit);


    /**
     * @author huang xingyi
     * @description: 临时添加
     * @date 2023/5/16
     * @param planDetail
     * @return void
    */
    void temporaryAdd(PlanDetail planDetail);

    /**
     * @author huang xingyi
     * @description: 主管或行政审核个人计划
     * @date 2023/5/17
     * @param checkPlan
     * @return void
    */
    void planCheck(CheckPlan checkPlan);
}
