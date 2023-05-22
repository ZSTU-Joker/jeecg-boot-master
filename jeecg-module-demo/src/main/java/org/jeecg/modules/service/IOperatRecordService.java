package org.jeecg.modules.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.entity.OperatRecord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: opterat_record
 * @Author: jeecg-boot
 * @Date:   2023-05-17
 * @Version: V1.0
 */
public interface IOperatRecordService extends IService<OperatRecord> {

    /**
     * @author huang xingyi
     * @description: 获取操作记录，普通员工只能看到和自己有关的操作记录，部门主管可以看到自己部门下所有人员的操作记录
     * @date 2023/5/17
     * @param
     * @return IPage<OperatRecord>
    */
    IPage<OperatRecord> getOperatRecordByUser();
}
