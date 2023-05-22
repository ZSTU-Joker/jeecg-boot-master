package org.jeecg.modules.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecg.modules.entity.PersonPlan;
import org.jeecg.modules.entity.PlanDetail;

import java.util.List;

/**
 * 计划表详情VO
 *
 * @author: 黄兴怡
 * @date: 2023年05月16日
 */
@Data
public class PersonPlanDetail {

    /**
     * 计划表基本信息
     */
    private PersonPlan personPlan;

    /**
     * 计划表项列表
     */
    private List<PlanDetail> planDetailList;
}
