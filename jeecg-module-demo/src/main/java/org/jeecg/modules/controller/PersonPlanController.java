package org.jeecg.modules.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.common.PlanStatus;
import org.jeecg.modules.dto.AddPersonPlan;
import org.jeecg.modules.dto.CheckPlan;
import org.jeecg.modules.dto.PlanEdit;
import org.jeecg.modules.entity.PersonPlan;
import org.jeecg.modules.entity.PlanDetail;
import org.jeecg.modules.service.IPersonPlanService;
import org.jeecg.modules.service.IPlanDetailService;
import org.jeecg.modules.vo.PersonPlanDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * @Description: 个人计划
 * @Author: jeecg-boot
 * @Date: 2023-05-16
 * @Version: V1.0
 */
@Api(tags = "个人计划")
@RestController
@RequestMapping("/modules/personPlan")
@Slf4j
public class PersonPlanController extends JeecgController<PersonPlan, IPersonPlanService> {
    @Autowired
    private IPersonPlanService personPlanService;

    @Autowired
    private IPlanDetailService planDetailService;

    /**
     * 分页列表查询
     *
     * @param personPlan
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "个人计划-分页列表查询")
    @ApiOperation(value = "个人计划-分页列表查询", notes = "个人计划-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<PersonPlan>> queryPageList(PersonPlan personPlan,
                                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                   HttpServletRequest req) {
        QueryWrapper<PersonPlan> queryWrapper = QueryGenerator.initQueryWrapper(personPlan, req.getParameterMap());
        Page<PersonPlan> page = new Page<PersonPlan>(pageNo, pageSize);
        IPage<PersonPlan> pageList = personPlanService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 新增
     *
     * @param addPersonPlan
     * @return
     */
    @AutoLog(value = "个人计划-新增")
    @ApiOperation(value = "个人计划-新增", notes = "个人计划-新增")
    @RequiresPermissions("modules:person_plan:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody AddPersonPlan addPersonPlan) {
        try {
            personPlanService.addNewPlan(addPersonPlan);
            return Result.OK("添加成功！");
        } catch (Exception e) {
            return Result.error("添加失败！");
        }
    }

    /**
     * 编辑
     *
     * @param planEdit
     * @return
     */
    @AutoLog(value = "个人计划-编辑")
    @ApiOperation(value = "个人计划-编辑", notes = "个人计划-编辑")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody PlanEdit planEdit) {
        personPlanService.editById(planEdit);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "个人计划-通过id删除")
    @ApiOperation(value = "个人计划-通过id删除", notes = "个人计划-通过id删除")
    @RequiresPermissions("modules:person_plan:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        personPlanService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "个人计划-批量删除")
    @ApiOperation(value = "个人计划-批量删除", notes = "个人计划-批量删除")
    @RequiresPermissions("modules:person_plan:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.personPlanService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询详情
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "个人计划-通过id查询")
    @ApiOperation(value = "个人计划-通过id查询详情", notes = "个人计划-通过id查询详情")
    @GetMapping(value = "/queryById")
    public Result<IPage<PersonPlanDetail>> queryById(@RequestParam(name = "id", required = true) String id,
                                                     @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                     @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Page<PlanDetail> planDetailPage = new Page<>(pageNo, pageSize);
//		Page<PersonPlan> personPlanPage = new Page<>(pageNo, pageSize);
//		IPage<PersonPlan> personPlanIPage = personPlanService.page(personPlanPage, new LambdaQueryWrapper<PersonPlan>()
//				.eq(PersonPlan::getId, id));
//		IPage<PlanDetail> planDetailIPage = planDetailService.page(planDetailPage, new LambdaQueryWrapper<PlanDetail>()
//				.eq(PlanDetail::getPlanId, id));
//		PersonPlanDetail personPlanDetail = new PersonPlanDetail();
//		personPlanDetail.setPersonPlan(personPlanIPage);
//		personPlanDetail.setPlanDetailList(planDetailIPage);
//		if(personPlanDetail==null) {
//			return Result.error("未找到对应数据");
//		}
//		Page<PersonPlanDetail> result = new Page<>() ;
//		result.setRecords(Arrays.asList());
        IPage<PersonPlanDetail> planDetailById = personPlanService.getPlanDetailById(id, planDetailPage);
        return Result.ok(planDetailById);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param personPlan
     */
    @RequiresPermissions("modules:person_plan:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, PersonPlan personPlan) {
        return super.exportXls(request, personPlan, PersonPlan.class, "个人计划");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequiresPermissions("modules:person_plan:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, PersonPlan.class);
    }

    /**
     * 临时添加
     *
     * @param planDetail
     * @return
     */
    @AutoLog(value = "个人计划-临时添加")
    @ApiOperation(value = "个人计划-临时添加", notes = "个人计划-临时添加")
    @RequiresPermissions("modules:person_plan:edit")
    @RequestMapping(value = "/temporaryAdd", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody PlanDetail planDetail) {
        //如果状态在待主管评后，就不能做临时添加操作
        PersonPlan personPlan = personPlanService.page(null, new LambdaQueryWrapper<PersonPlan>().eq(PersonPlan::getId, planDetail.getPlanId()));
        if (personPlan.getStatus().compareTo(PlanStatus.CODE_22.getCode()) >= 0) {
            return Result.error("当前状态不能进行临时添加!");
        }
        personPlanService.temporaryAdd(planDetail);
        return Result.OK("临时添加!");
    }

    /**
     * 更新进度
     *
     * @param planDetail
     * @return
     */
    @AutoLog(value = "个人计划-更新进度")
    @ApiOperation(value = "个人计划-更新进度", notes = "个人计划-更新进度")
    @RequestMapping(value = "/updateProgress", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> updateProgress(@RequestBody PlanDetail planDetail) {
        planDetailService.updateById(planDetail);
        return Result.OK("更新进度成功!");
    }


    /**
     * 主管或行政审核个人计划
     *
     * @param checkPlan
     * @return
     */
    @AutoLog(value = "个人计划-审核")
    @ApiOperation(value = "个人计划-审核", notes = "个人计划-审核")
    @RequestMapping(value = "/planCheck", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> planCheck(@RequestBody CheckPlan checkPlan) {
        personPlanService.planCheck(checkPlan);
        return Result.OK("审核成功!");
    }
}
