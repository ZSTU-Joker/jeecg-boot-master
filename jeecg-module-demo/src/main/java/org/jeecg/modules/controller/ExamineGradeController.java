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
import org.jeecg.modules.dto.ExamineGradeAddParam;
import org.jeecg.modules.dto.ExamineGradeUpdateParam;
import org.jeecg.modules.entity.ExamineGrade;
import org.jeecg.modules.service.IExamineGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * @Description: 考核等级表
 * @Author: jeecg-boot
 * @Date: 2023-05-18
 * @Version: V1.0
 */
@Api(tags = "考核等级表")
@RestController
@RequestMapping("/grade/examineGrade")
@Slf4j
public class ExamineGradeController extends JeecgController<ExamineGrade, IExamineGradeService> {
    @Autowired
    private IExamineGradeService examineGradeService;

    /**
     * 分页列表查询
     *
     * @param examineGrade
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "考核等级表-分页列表查询")
    @ApiOperation(value = "考核等级表-分页列表查询", notes = "考核等级表-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<ExamineGrade>> queryPageList(ExamineGrade examineGrade,
                                                     @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                     @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                     HttpServletRequest req) {
        QueryWrapper<ExamineGrade> queryWrapper = QueryGenerator.initQueryWrapper(examineGrade, req.getParameterMap());
        Page<ExamineGrade> page = new Page<ExamineGrade>(pageNo, pageSize);
        IPage<ExamineGrade> pageList = examineGradeService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param param
     * @return
     */
    @AutoLog(value = "考核等级表-添加")
    @ApiOperation(value = "考核等级表-添加", notes = "考核等级表-添加")
    @RequiresPermissions("grade:examine_grade:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody ExamineGradeAddParam param) {
        examineGradeService.insertForGrades(param.getLeftScores(), param.getRightScores(), param.getGradeNames(), param.getMonthAssessmentTemplateId());
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param
     * @return
     */
    @AutoLog(value = "考核等级表-编辑")
    @ApiOperation(value = "考核等级表-编辑", notes = "考核等级表-编辑")
    @RequiresPermissions("grade:examine_grade:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody ExamineGradeUpdateParam param) {
        examineGradeService.updateForGrades(param);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "考核等级表-通过id删除")
    @ApiOperation(value = "考核等级表-通过id删除", notes = "考核等级表-通过id删除")
    @RequiresPermissions("grade:examine_grade:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        examineGradeService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "考核等级表-批量删除")
    @ApiOperation(value = "考核等级表-批量删除", notes = "考核等级表-批量删除")
    @RequiresPermissions("grade:examine_grade:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.examineGradeService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "考核等级表-通过id查询")
    @ApiOperation(value = "考核等级表-通过id查询", notes = "考核等级表-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<ExamineGrade> queryById(@RequestParam(name = "id", required = true) String id) {
        ExamineGrade examineGrade = examineGradeService.getById(id);
        if (examineGrade == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(examineGrade);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param examineGrade
     */
    @RequiresPermissions("grade:examine_grade:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ExamineGrade examineGrade) {
        return super.exportXls(request, examineGrade, ExamineGrade.class, "考核等级表");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequiresPermissions("grade:examine_grade:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, ExamineGrade.class);
    }

}
