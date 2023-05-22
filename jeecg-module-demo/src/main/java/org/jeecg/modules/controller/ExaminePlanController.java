package org.jeecg.modules.controller;

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
import org.jeecg.modules.entity.ExaminePlan;
import org.jeecg.modules.service.IExaminePlanService;
import org.jeecg.modules.vo.ExaminePlanVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * @Description: 考核计划表
 * @Author: jeecg-boot
 * @Date: 2023-05-19
 * @Version: V1.0
 */
@Api(tags = "考核计划表")
@RestController
@RequestMapping("/examinePlan")
@Slf4j
public class ExaminePlanController extends JeecgController<ExaminePlan, IExaminePlanService> {
    @Autowired
    private IExaminePlanService examinePlanService;

    /**
     * 分页列表查询
     *
     * @param examinePlan
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "考核计划表-分页列表查询")
    @ApiOperation(value = "考核计划表-分页列表查询", notes = "考核计划表-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<ExaminePlanVO>> queryPageList(ExaminePlan examinePlan,
                                                      @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                      HttpServletRequest req) {
        QueryWrapper<ExaminePlan> queryWrapper = QueryGenerator.initQueryWrapper(examinePlan, req.getParameterMap());
        Page<ExaminePlan> page = new Page<ExaminePlan>(pageNo, pageSize);
        IPage<ExaminePlan> pageList = examinePlanService.page(page, queryWrapper);
        IPage<ExaminePlanVO> pageVoList = examinePlanService.pageExtend(pageList);
        return Result.OK(pageVoList);
    }

    /**
     * 添加
     *
     * @param examinePlan
     * @return
     */
    @AutoLog(value = "考核计划表-添加")
    @ApiOperation(value = "考核计划表-添加", notes = "考核计划表-添加")
    @RequiresPermissions("examine_plan:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody ExaminePlan examinePlan) {
        examinePlanService.save(examinePlan);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param examinePlan
     * @return
     */
    @AutoLog(value = "考核计划表-编辑")
    @ApiOperation(value = "考核计划表-编辑", notes = "考核计划表-编辑")
    @RequiresPermissions("examine_plan:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody ExaminePlan examinePlan) {
        examinePlanService.updateById(examinePlan);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "考核计划表-通过id删除")
    @ApiOperation(value = "考核计划表-通过id删除", notes = "考核计划表-通过id删除")
    @RequiresPermissions("examine_plan:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        examinePlanService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "考核计划表-批量删除")
    @ApiOperation(value = "考核计划表-批量删除", notes = "考核计划表-批量删除")
    @RequiresPermissions("examine_plan:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.examinePlanService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "考核计划表-通过id查询")
    @ApiOperation(value = "考核计划表-通过id查询", notes = "考核计划表-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<ExaminePlan> queryById(@RequestParam(name = "id", required = true) String id) {
        ExaminePlan examinePlan = examinePlanService.getById(id);
        if (examinePlan == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(examinePlan);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param examinePlan
     */
    @RequiresPermissions("examine_plan:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ExaminePlan examinePlan) {
        return super.exportXls(request, examinePlan, ExaminePlan.class, "考核计划表");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequiresPermissions("examine_plan:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, ExaminePlan.class);
    }

}
