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
import org.jeecg.modules.entity.AssessmentIndicators;
import org.jeecg.modules.service.IAssessmentIndicatorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * @Description: 考核指标表
 * @Author: jeecg-boot
 * @Date: 2023-05-16
 * @Version: V1.0
 */
@Api(tags = "考核指标表")
@RestController
@RequestMapping("assessmentIndicators")
@Slf4j
public class AssessmentIndicatorsController extends JeecgController<AssessmentIndicators, IAssessmentIndicatorsService> {
    @Autowired
    private IAssessmentIndicatorsService assessmentIndicatorsService;

    /**
     * 分页列表查询
     *
     * @param assessmentIndicators
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "考核指标表-分页列表查询")
    @ApiOperation(value = "考核指标表-分页列表查询", notes = "考核指标表-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<AssessmentIndicators>> queryPageList(AssessmentIndicators assessmentIndicators, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req) {
        QueryWrapper<AssessmentIndicators> queryWrapper = QueryGenerator.initQueryWrapper(assessmentIndicators, req.getParameterMap());
        Page<AssessmentIndicators> page = new Page<AssessmentIndicators>(pageNo, pageSize);
        IPage<AssessmentIndicators> pageList = assessmentIndicatorsService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param assessmentIndicators
     * @return
     */
    @AutoLog(value = "考核指标表-添加")
    @ApiOperation(value = "考核指标表-添加", notes = "考核指标表-添加")
    @RequiresPermissions("assessment_indicators:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody AssessmentIndicators assessmentIndicators) {
        assessmentIndicatorsService.save(assessmentIndicators);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param assessmentIndicators
     * @return
     */
    @AutoLog(value = "考核指标表-编辑")
    @ApiOperation(value = "考核指标表-编辑", notes = "考核指标表-编辑")
    @RequiresPermissions("assessment_indicators:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody AssessmentIndicators assessmentIndicators) {
        assessmentIndicatorsService.updateById(assessmentIndicators);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "考核指标表-通过id删除")
    @ApiOperation(value = "考核指标表-通过id删除", notes = "考核指标表-通过id删除")
    @RequiresPermissions("assessment_indicators:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        assessmentIndicatorsService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "考核指标表-批量删除")
    @ApiOperation(value = "考核指标表-批量删除", notes = "考核指标表-批量删除")
    @RequiresPermissions("assessment_indicators:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.assessmentIndicatorsService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "考核指标表-通过id查询")
    @ApiOperation(value = "考核指标表-通过id查询", notes = "考核指标表-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<AssessmentIndicators> queryById(@RequestParam(name = "id", required = true) String id) {
        AssessmentIndicators assessmentIndicators = assessmentIndicatorsService.getById(id);
        if (assessmentIndicators == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(assessmentIndicators);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param assessmentIndicators
     */
    @RequiresPermissions("assessment_indicators:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, AssessmentIndicators assessmentIndicators) {
        return super.exportXls(request, assessmentIndicators, AssessmentIndicators.class, "考核指标表");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequiresPermissions("assessment_indicators:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, AssessmentIndicators.class);
    }

}
