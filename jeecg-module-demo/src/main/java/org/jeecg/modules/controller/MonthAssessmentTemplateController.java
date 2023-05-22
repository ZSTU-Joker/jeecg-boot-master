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
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.dto.AssessmentIndicatorsAddParam;
import org.jeecg.modules.dto.MonthAssessmentTemplateAddParam;
import org.jeecg.modules.dto.MonthAssessmentTemplateQueryParam;
import org.jeecg.modules.entity.MonthAssessmentTemplate;
import org.jeecg.modules.service.IMonthAssessmentTemplateService;
import org.jeecg.modules.vo.MonthAssessmentTemplateWithIndicatorsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * @Description: 月考核模板
 * @Author: jeecg-boot
 * @Date: 2023-05-16
 * @Version: V1.0
 */
@Api(tags = "月考核模板")
@RestController
@RequestMapping("monthAssessmentTemplate")
@Slf4j
public class MonthAssessmentTemplateController extends JeecgController<MonthAssessmentTemplate, IMonthAssessmentTemplateService> {
    @Autowired
    private IMonthAssessmentTemplateService monthAssessmentTemplateService;

//    /**
//     * 分页列表查询
//     *
//     * @param monthAssessmentTemplate
//     * @param pageNo
//     * @param pageSize
//     * @param req
//     * @return
//     */
//    //@AutoLog(value = "月考核模板-分页列表查询")
//    @ApiOperation(value = "月考核模板-分页列表查询", notes = "月考核模板-分页列表查询")
//    @GetMapping(value = "/list")
//    public Result<IPage<MonthAssessmentTemplate>> queryPageList(MonthAssessmentTemplate monthAssessmentTemplate,
//                                                                @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
//                                                                @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
//                                                                HttpServletRequest req) {
//        QueryWrapper<MonthAssessmentTemplate> queryWrapper = QueryGenerator.initQueryWrapper(monthAssessmentTemplate, req.getParameterMap());
//        Page<MonthAssessmentTemplate> page = new Page<MonthAssessmentTemplate>(pageNo, pageSize);
//        IPage<MonthAssessmentTemplate> pageList = monthAssessmentTemplateService.page(page, queryWrapper);
//        return Result.OK(pageList);
//    }


    @ApiOperation(value = "月考核模板-分页列表查询-追加考核指标", notes = "月考核模板-分页列表查询-追加考核指标")
    @GetMapping(value = "/list/template/indicators")
    public Result<IPage<MonthAssessmentTemplateWithIndicatorsVO>> queryPageListWithIndicators(MonthAssessmentTemplate monthAssessmentTemplate,
                                                                                              @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                                                              @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                                                              HttpServletRequest req) {
        QueryWrapper<MonthAssessmentTemplate> queryWrapper = QueryGenerator.initQueryWrapper(monthAssessmentTemplate, req.getParameterMap());
        queryWrapper.like("name", monthAssessmentTemplate.getName());
        queryWrapper.like("code", monthAssessmentTemplate.getCode());
        Page<MonthAssessmentTemplate> page = new Page<MonthAssessmentTemplate>(pageNo, pageSize);
        IPage<MonthAssessmentTemplate> pageList = monthAssessmentTemplateService.page(page, queryWrapper);
        IPage<MonthAssessmentTemplateWithIndicatorsVO> pageList2 = monthAssessmentTemplateService.pageAppendIndicators(pageList);
        return Result.OK(pageList2);
    }

    @ApiOperation(value = "月考核模板-分页列表查询-查询个人模板", notes = "月考核模板-分页列表查询-追加考核指标")
    @GetMapping(value = "/list/template/personal")
    public Result<MonthAssessmentTemplate> queryPersonalTemplate(MonthAssessmentTemplateQueryParam param) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("sys_org_code", param.getSysOrgCode());
        queryWrapper.eq("del_flag", 0);
        MonthAssessmentTemplate template = monthAssessmentTemplateService.getOne(queryWrapper);
        if (template == null) {
            throw new JeecgBootException("模板不存在");
        }
        return Result.OK(template);
    }

    /**
     * 添加
     *
     * @param templateAddParam
     * @return
     */
    @AutoLog(value = "月考核模板-添加")
    @ApiOperation(value = "月考核模板-添加", notes = "月考核模板-添加")
//	@RequiresPermissions("month_assessment_template:add")
    @PostMapping(value = "/add")
    public Result<String> add(MonthAssessmentTemplateAddParam templateAddParam) {
        monthAssessmentTemplateService.saveSingleData(templateAddParam, templateAddParam.getIndicatorsList());
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param monthAssessmentTemplate
     * @return
     */
    @AutoLog(value = "月考核模板-编辑")
    @ApiOperation(value = "月考核模板-编辑", notes = "月考核模板-编辑")
    @RequiresPermissions("month_assessment_template:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(MonthAssessmentTemplate monthAssessmentTemplate, List<AssessmentIndicatorsAddParam> indicatorsList) {
        monthAssessmentTemplateService.editSingleData(monthAssessmentTemplate, indicatorsList);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "月考核模板-通过id删除")
    @ApiOperation(value = "月考核模板-通过id删除", notes = "月考核模板-通过id删除")
    @RequiresPermissions("month_assessment_template:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        monthAssessmentTemplateService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "月考核模板-批量删除")
    @ApiOperation(value = "月考核模板-批量删除", notes = "月考核模板-批量删除")
    @RequiresPermissions("month_assessment_template:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.monthAssessmentTemplateService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param templateId
     * @return
     */
    //@AutoLog(value = "月考核模板-通过id查询")
    @ApiOperation(value = "月考核模板-通过id查询", notes = "月考核模板-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<MonthAssessmentTemplateWithIndicatorsVO> queryById(@RequestParam(name = "id", required = true) String templateId) {
        MonthAssessmentTemplateWithIndicatorsVO monthAssessmentTemplate = monthAssessmentTemplateService.queryOne(templateId);
//        if (monthAssessmentTemplate == null) {
//            return Result.error("未找到对应数据");
//        }
        return Result.OK(monthAssessmentTemplate);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param monthAssessmentTemplate
     */
    @RequiresPermissions("month_assessment_template:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, MonthAssessmentTemplate monthAssessmentTemplate) {
        return super.exportXls(request, monthAssessmentTemplate, MonthAssessmentTemplate.class, "月考核模板");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequiresPermissions("month_assessment_template:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, MonthAssessmentTemplate.class);
    }

}
