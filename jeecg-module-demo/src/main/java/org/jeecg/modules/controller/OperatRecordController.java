package org.jeecg.modules.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.UUIDGenerator;
import org.jeecg.modules.entity.OperatRecord;
import org.jeecg.modules.service.IOperatRecordService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

 /**
 * @Description: opterat_record
 * @Author: jeecg-boot
 * @Date:   2023-05-17
 * @Version: V1.0
 */
@Api(tags="操作记录")
@RestController
@RequestMapping("/operation/operatRecord")
@Slf4j
public class OperatRecordController extends JeecgController<OperatRecord, IOperatRecordService> {
	@Autowired
	private IOperatRecordService operatRecordService;
	
	/**
	 * 操作记录查询，普通员工只能看和自己有关的操作记录，部门领导可以看本部门的所有操作记录
	 *
	 * @param operatRecord
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "操作记录-分页列表查询")
	@ApiOperation(value="操作记录-分页列表查询", notes="操作记录-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<OperatRecord>> queryPageList(OperatRecord operatRecord,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<OperatRecord> queryWrapper = QueryGenerator.initQueryWrapper(operatRecord, req.getParameterMap());
		Page<OperatRecord> page = new Page<OperatRecord>(pageNo, pageSize);
		IPage<OperatRecord> pageList = operatRecordService.getOperatRecordByUser();
		return Result.OK(pageList);
	}
	
	/**
	 *   操作记录添加
	 *
	 * @param operatRecord
	 * @return
	 */
	@AutoLog(value = "操作记录或评论-添加")
	@ApiOperation(value="操作记录或评论-添加", notes="操作记录或评论-添加")
	@PostMapping(value = "/addOperation")
	public Result<String> addOperation(@RequestBody OperatRecord operatRecord) {
		operatRecord.setId(UUIDGenerator.generate());
		operatRecord.setType("0");
		operatRecordService.save(operatRecord);
		return Result.OK("添加成功！");
	}

	 /**
	  *   评论添加
	  *
	  * @param operatRecord
	  * @return
	  */
	 @AutoLog(value = "评论-添加")
	 @ApiOperation(value="操作记录或评论-添加", notes="操作记录或评论-添加")
	 @PostMapping(value = "/addRemark")
	 public Result<String> addRemark(@RequestBody OperatRecord operatRecord) {
		 operatRecord.setId(UUIDGenerator.generate());
		 operatRecord.setType("1");
		 operatRecordService.save(operatRecord);
		 return Result.OK("添加成功！");
	 }
	
	/**
	 *  编辑
	 *
	 * @param operatRecord
	 * @return
	 */
	@AutoLog(value = "操作记录-编辑")
	@ApiOperation(value="操作记录-编辑", notes="操作记录-编辑")
	@RequiresPermissions("operation:opterat_record:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody OperatRecord operatRecord) {
		operatRecordService.updateById(operatRecord);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "操作记录-通过id删除")
	@ApiOperation(value="操作记录-通过id删除", notes="操作记录-通过id删除")
	@RequiresPermissions("operation:opterat_record:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		operatRecordService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "操作记录-批量删除")
	@ApiOperation(value="操作记录-批量删除", notes="操作记录-批量删除")
	@RequiresPermissions("operation:opterat_record:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.operatRecordService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "操作记录-通过id查询")
	@ApiOperation(value="操作记录-通过id查询", notes="操作记录-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<OperatRecord> queryById(@RequestParam(name="id",required=true) String id) {
		OperatRecord operatRecord = operatRecordService.getById(id);
		if(operatRecord==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(operatRecord);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param operatRecord
    */
    @RequiresPermissions("operation:opterat_record:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, OperatRecord operatRecord) {
        return super.exportXls(request, operatRecord, OperatRecord.class, "opterat_record");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("operation:opterat_record:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, OperatRecord.class);
    }

}
