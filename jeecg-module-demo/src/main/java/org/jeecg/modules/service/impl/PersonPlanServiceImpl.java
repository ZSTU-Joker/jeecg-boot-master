package org.jeecg.modules.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.UUIDGenerator;
import org.jeecg.modules.controller.OperatRecordController;
import org.jeecg.modules.dto.AddPersonPlan;
import org.jeecg.modules.dto.CheckPlan;
import org.jeecg.modules.dto.PlanEdit;
import org.jeecg.modules.entity.OperatRecord;
import org.jeecg.modules.entity.PersonPlan;
import org.jeecg.modules.entity.PlanDetail;
import org.jeecg.modules.mapper.PersonPlanMapper;
import org.jeecg.modules.common.PlanStatus;
import org.jeecg.modules.service.IOperatRecordService;
import org.jeecg.modules.service.IPersonPlanService;
import org.jeecg.modules.service.IPlanDetailService;
import org.jeecg.modules.vo.PersonPlanDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 个人计划
 * @Author: jeecg-boot
 * @Date: 2023-05-16
 * @Version: V1.0
 */
@Service
public class PersonPlanServiceImpl extends ServiceImpl<PersonPlanMapper, PersonPlan> implements IPersonPlanService {
    @Autowired
    private PersonPlanMapper personPlanMapper;

    @Autowired
    private IPlanDetailService planDetailService;

    @Autowired
    private ISysBaseAPI sysBaseAPI;

    @Autowired
    private OperatRecordController operatRecordController;

    @Override
    public Page<PersonPlanDetail> getPlanDetailById(String id, Page<PlanDetail> page) {
        PersonPlan personPlan = personPlanMapper.selectById(id);
        LambdaQueryWrapper lambdaQueryWrapper = new LambdaQueryWrapper<PlanDetail>().eq(PlanDetail::getPlanId, id)
                .orderByAsc(PlanDetail::getLevel).orderByDesc(PlanDetail::getUpdateTime);
        List<PlanDetail> planDetailList = planDetailService.page(page, lambdaQueryWrapper).getRecords();
        //字典项翻译
        String status = sysBaseAPI.getDictItems("plan_status").stream().filter(p -> p.getValue().equals(personPlan.getStatus()))
                .collect(Collectors.toList()).get(0).getText();
        personPlan.setStatus(status);
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        personPlan.setCreateBy(loginUser.getRealname());
        personPlan.setSysOrgCode(String.join(",", sysBaseAPI.getDepartNamesByUsername(loginUser.getUsername())));
        PersonPlanDetail planDetailVO = new PersonPlanDetail();
        planDetailVO.setPersonPlan(personPlan);
        planDetailVO.setPlanDetailList(planDetailList);
        Page<PersonPlanDetail> result = new Page<>();
        result.setRecords(Arrays.asList(planDetailVO));
        return result;
    }

    @Override
    @Transactional
    public void addNewPlan(AddPersonPlan addPersonPlan) {
        LoginUser loginUser = (LoginUser)SecurityUtils.getSubject().getPrincipal();
        String id = UUIDGenerator.generate();
        addPersonPlan.getPersonPlan().setId(id);
        addPersonPlan.getPersonPlan().setUpdateBy(loginUser.getUsername());
        //person_plan新增一条记录
        this.save(addPersonPlan.getPersonPlan());
        addPersonPlan.getPlanDetailList().stream().forEach(p -> {
            p.setId(UUIDGenerator.generate());
            p.setPlanId(id);
            p.setUpdateBy(loginUser.getUsername());
        });
        //plan_detail新增多条记录
        planDetailService.saveBatch(addPersonPlan.getPlanDetailList());
        //新增操作记录
        OperatRecord operatRecord = new OperatRecord();
        operatRecord.setPlanId(id);
        operatRecord.setContent(loginUser.getRealname() + "创建了个人计划");
        operatRecordController.addOperation(operatRecord);
        //是否提交
        if(addPersonPlan.getSubmitFlag().equals(0)){
            this.update(new LambdaUpdateWrapper<PersonPlan>().eq(PersonPlan::getId, id).set(PersonPlan::getStatus, "2"));
            //提交操作记录
            OperatRecord submitRecord = new OperatRecord();
            submitRecord.setPlanId(id);
            submitRecord.setContent(loginUser.getRealname() + "提交了个人计划");
            operatRecordController.addOperation(operatRecord);
        }
    }

    @Override
    @Transactional
    public void editById(PlanEdit planEdit) {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        //如果有新增做新增
        if (planEdit.getAddNewplanDetailList() != null) {
            planEdit.getAddNewplanDetailList().stream().forEach(p -> p.setId(UUIDGenerator.generate()));
            planDetailService.saveBatch(planEdit.getAddNewplanDetailList());
        }
        //如果有编辑做编辑
        if (planEdit.getEditPlanDetailList() != null) {
            for(PlanDetail planDetail : planEdit.getEditPlanDetailList()){
                planDetailService.updateById(planDetail);
            }
        }
        //如果有删除做删除
        if (planEdit.getDelPlanDetailIdList() != null) {
            planDetailService.removeByIds(planEdit.getDelPlanDetailIdList());
        }
        //person_plan表更新
        personPlanMapper.update(null, new LambdaUpdateWrapper<PersonPlan>().eq(PersonPlan::getId, planEdit.getId())
                .set(PersonPlan::getUpdateTime, new Date()).set(PersonPlan::getUpdateBy, loginUser.getUsername()));
        //编辑操作记录
        OperatRecord operatRecord = new OperatRecord();
        operatRecord.setPlanId(planEdit.getId());
        operatRecord.setContent(loginUser.getRealname() + "编辑了个人计划表");
        operatRecordController.addOperation(operatRecord);
        //是否提交
        if(planEdit.getSubmitFlag().equals(0)){
            this.update(new LambdaUpdateWrapper<PersonPlan>().eq(PersonPlan::getId, planEdit.getId()).set(PersonPlan::getStatus, "2"));
            //提交操作记录
            OperatRecord submitRecord = new OperatRecord();
            submitRecord.setPlanId(planEdit.getId());
            submitRecord.setContent(loginUser.getRealname() + "提交了个人计划");
            operatRecordController.addOperation(operatRecord);
        }
    }

    @Override
    @Transactional
    public void temporaryAdd(PlanDetail planDetail) {
        planDetailService.save(planDetail);
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        //person_plan表记录更新
        personPlanMapper.update(null, new LambdaUpdateWrapper<PersonPlan>().eq(PersonPlan::getId, planDetail.getPlanId())
                .set(PersonPlan::getUpdateTime, new Date())
                .set(PersonPlan::getUpdateBy, loginUser.getUsername()));
        OperatRecord operatRecord = new OperatRecord();
        operatRecord.setPlanId(planDetail.getPlanId());
        operatRecord.setContent(loginUser.getRealname() + "临时添加了个人计划表");
        operatRecordController.addOperation(operatRecord);
    }

    @Override
    @Transactional
    public void planCheck(CheckPlan checkPlan) {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        switch (checkPlan.getPassFlag()){
            //被驳回的情况
            case 0:
                //被行政驳回
               if(checkPlan.getStatus().equals(PlanStatus.CODE_4.getCode())){
                   PersonPlan personPlan = new PersonPlan();
                   personPlan.setId(checkPlan.getId());
                   personPlan.setAdministrationCheckContent(checkPlan.getContent());
                   personPlan.setStatus(PlanStatus.CODE_5.getCode());
                   personPlanMapper.updateById(personPlan);
               }else {
                   PersonPlan personPlan = new PersonPlan();
                   personPlan.setId(checkPlan.getId());
                   personPlan.setChargeCheckContent(checkPlan.getContent());
                   personPlan.setStatus(PlanStatus.CODE_2.getCode());
                   personPlanMapper.updateById(personPlan);
               }
                OperatRecord failRecord = new OperatRecord();
                failRecord.setPlanId(checkPlan.getId());
                failRecord.setContent(loginUser.getRealname() + "拒绝了该计划表: " + checkPlan.getContent());
                operatRecordController.addOperation(failRecord);
                break;
               //通过
            case 1:
                if(checkPlan.getStatus().equals(PlanStatus.CODE_4.getCode())){
                    PersonPlan personPlan = new PersonPlan();
                    personPlan.setId(checkPlan.getId());
                    personPlan.setAdministrationCheckContent(checkPlan.getContent());
                    personPlan.setStatus(PlanStatus.CODE_21.getCode());
                    personPlanMapper.updateById(personPlan);
                }else {
                    PersonPlan personPlan = new PersonPlan();
                    personPlan.setId(checkPlan.getId());
                    personPlan.setChargeCheckContent(checkPlan.getContent());
                    personPlan.setStatus(PlanStatus.CODE_4.getCode());
                    personPlanMapper.updateById(personPlan);
                }
                OperatRecord passRecord = new OperatRecord();
                passRecord.setPlanId(checkPlan.getId());
                passRecord.setContent(loginUser.getRealname() + "同意了该计划表: " + checkPlan.getContent());
                operatRecordController.addOperation(passRecord);
                break;
        }
    }
}
