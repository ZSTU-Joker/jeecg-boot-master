package org.jeecg.modules.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.entity.PlanDetail;
import org.jeecg.modules.service.IPlanDetailService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

 /**
 * @Description: 计划详情
 * @Author: jeecg-boot
 * @Date:   2023-05-16
 * @Version: V1.0
 */
@Api(tags="计划详情")
@RestController
@RequestMapping("/modules/planDetail")
@Slf4j
public class PlanDetailController extends JeecgController<PlanDetail, IPlanDetailService> {
	@Autowired
	private IPlanDetailService planDetailService;
	
	/**
	 * 分页列表查询
	 *
	 * @param planDetail
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "计划详情-分页列表查询")
	@ApiOperation(value="计划详情-分页列表查询", notes="计划详情-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<PlanDetail>> queryPageList(PlanDetail planDetail,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<PlanDetail> queryWrapper = QueryGenerator.initQueryWrapper(planDetail, req.getParameterMap());
		Page<PlanDetail> page = new Page<PlanDetail>(pageNo, pageSize);
		IPage<PlanDetail> pageList = planDetailService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param planDetailList
	 * @return
	 */
	@AutoLog(value = "计划详情-添加")
	@ApiOperation(value="计划详情-添加", notes="计划详情-添加")
	@RequiresPermissions("modules:plan_detail:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody List<PlanDetail> planDetailList) {
		//planDetailList.stream().forEach();
		//planDetailService.saveBatch(planDetail);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param planDetail
	 * @return
	 */
	@AutoLog(value = "计划详情-编辑")
	@ApiOperation(value="计划详情-编辑", notes="计划详情-编辑")
	@RequiresPermissions("modules:plan_detail:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody PlanDetail planDetail) {
		planDetailService.updateById(planDetail);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "计划详情-通过id删除")
	@ApiOperation(value="计划详情-通过id删除", notes="计划详情-通过id删除")
	@RequiresPermissions("modules:plan_detail:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		planDetailService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "计划详情-批量删除")
	@ApiOperation(value="计划详情-批量删除", notes="计划详情-批量删除")
	@RequiresPermissions("modules:plan_detail:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.planDetailService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "计划详情-通过id查询")
	@ApiOperation(value="计划详情-通过id查询", notes="计划详情-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<PlanDetail> queryById(@RequestParam(name="id",required=true) String id) {
		PlanDetail planDetail = planDetailService.getById(id);
		if(planDetail==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(planDetail);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param planDetail
    */
    @RequiresPermissions("modules:plan_detail:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, PlanDetail planDetail) {
        return super.exportXls(request, planDetail, PlanDetail.class, "计划详情");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("modules:plan_detail:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, PlanDetail.class);
    }

}
