package org.jeecg.modules.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.entity.ExaminePlan;
import org.jeecg.modules.vo.ExaminePlanVO;

/**
 * @Description: 考核计划表
 * @Author: jeecg-boot
 * @Date: 2023-05-19
 * @Version: V1.0
 */
public interface IExaminePlanService extends IService<ExaminePlan> {

    IPage<ExaminePlanVO> pageExtend(IPage<ExaminePlan> page);

}
