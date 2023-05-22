package org.jeecg.modules.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.entity.OperatRecord;
import org.jeecg.modules.entity.PersonPlan;
import org.jeecg.modules.mapper.OperatRecordMapper;
import org.jeecg.modules.mapper.PersonPlanMapper;
import org.jeecg.modules.service.IOperatRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: opterat_record
 * @Author: jeecg-boot
 * @Date:   2023-05-17
 * @Version: V1.0
 */
@Service
public class OperatRecordServiceImpl extends ServiceImpl<OperatRecordMapper, OperatRecord> implements IOperatRecordService {
    @Autowired
    private OperatRecordMapper operatRecordMapper;

    @Autowired
    private PersonPlanMapper personPlanMapper;

    @Override
    public IPage<OperatRecord> getOperatRecordByUser() {
        IPage<OperatRecord> result = new Page<OperatRecord>() {
        };
        //获取登陆人的信息
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        //如果是普通员工
        if(loginUser.getUserIdentity() == 1){
            //查询创建者账户为登陆人账户的操作记录
            List<OperatRecord> listOne = operatRecordMapper.selectList(new LambdaQueryWrapper<OperatRecord>().eq(OperatRecord::getCreateBy, loginUser.getUsername()));
            //查询和登陆人计划有关的操作记录，例如计划表的审批
            //获取当前登陆人的计划表id
            List<String> planIdList = personPlanMapper.selectList(new LambdaQueryWrapper<PersonPlan>().eq(PersonPlan::getCreateBy, loginUser.getUsername()))
                    .stream().map(PersonPlan::getId).collect(Collectors.toList());
            //查询和计划表id相关的操作记录
            List<OperatRecord> listTwo = operatRecordMapper.selectList(new LambdaQueryWrapper<OperatRecord>().in(OperatRecord::getPlanId, planIdList));
            listOne.addAll(listTwo);
            result.setRecords(listOne);
        }else {
            //如果是部门领导
            //查询本部门的人员的所有操作
            List<OperatRecord> list = operatRecordMapper.selectList(new LambdaQueryWrapper<OperatRecord>().eq(OperatRecord::getSysOrgCode, loginUser.getOrgCode()));
        }
        return result;
    }
}
