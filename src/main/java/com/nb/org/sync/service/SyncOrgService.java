package com.nb.org.sync.service;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.http.HttpRequest;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nb.org.domain.Department;
import com.nb.org.domain.DepartmentSN;
import com.nb.org.domain.SyncOrg;
import com.nb.org.exception.DepartmentException;
import com.nb.org.service.IDepartmentService;
import com.nb.org.service.ISyncOrgService;
import com.nb.org.sync.config.SyncConfig;
import com.nb.org.sync.service.SyncOrgError.OrgError;
import com.nb.org.sync.util.Sign;
import com.nb.org.sync.util.parse.DataParseFactory;
import com.nb.org.sync.util.parse.IDataParse;
import com.nb.org.util.SNGenerator;
import com.nb.org.util.StringUtil;
import com.nb.org.util.SyncProperties;
@Controller
public class SyncOrgService {
	private static Logger logger = Logger.getLogger(SyncOrgService.class);
	@Resource
	private ISyncOrgService iSyncOrgService;
	@Resource
	private IDepartmentService iDepartmentService;
	@Resource
	private SNGenerator snGenerator;
	
	@ResponseBody
	@RequestMapping("/synchronizeOrg")
	public String synchronizeOrg(String servicecode, String time, String sign, String args, String datatype,
			String describe, String method, String orgcoding) {
		if(method!=null&&method.equals("deleteOrg")){
			return deleteOrg(servicecode, time, sign, args, datatype, describe, method, orgcoding);
		}else{
			return syncOrg(servicecode, time, sign, args, datatype, describe, method, orgcoding);
		}
	}
	//新增或者更新组织
	public String syncOrg(String servicecode, String time, String sign, String args, String datatype,
			String describe, String method, String orgcoding){

		String result = "";
		IDataParse dp = DataParseFactory.factory(datatype);
		boolean vali = Sign.sign(servicecode, time, sign);
		if (!vali) {
			logger.info("签名不合法");
			return SyncOrgError.getErrResult(dp, OrgError.appForbidSync);
		}
		try {
			Map<String, String> orgMap = dp.parseSimple(args);// 用户数据集合
			orgcoding = orgMap.get(SyncConfig.ORGCODING);
			if (orgcoding.isEmpty()) {
				logger.info("orgcoding为空");
				return SyncOrgError.getErrResult(dp, OrgError.argsIllegal);
			}
			SyncOrg syncOrg = iSyncOrgService.selectByOrgCoding(orgcoding);
			// 新增组织
			if (syncOrg == null) {
				
				String org_p ="";
				SyncOrg syncOrg_p = null;
				int depId = 0;
				if(orgcoding.equals(SyncProperties.initialOrg)){
					org_p = "";
					depId = Integer.parseInt(SyncProperties.nbDepId);
				}else{					
					org_p = orgcoding.substring(0, orgcoding.length()-3);
					syncOrg_p = iSyncOrgService.selectByOrgCoding(org_p);
					if (syncOrg_p == null) {
						logger.info("从syncOrg表中获取父部门失败");
						return SyncOrgError.getErrResult(dp, OrgError.orgcodingIllegal);
					}
					depId= syncOrg_p.getDepId();
				}
				Department department_p = iDepartmentService.getDepartmentById(depId);

				String name = orgMap.get("orgname");
				String contactPerson = orgMap.get("principalname");
				String contactNumber = orgMap.get("orgphone");
				String address = orgMap.get("orgoffice");
				String description = orgMap.get("orgallname");
				String devcoding = orgMap.get("devcoding");
				String nodetype = orgMap.get("nodetype");
				if(StringUtil.isNoE(devcoding)&&!StringUtil.isNoE(nodetype)&&nodetype.equals("2")){
					devcoding = syncOrg_p.getDevcoding();
				}
				
				if(orgcoding.equals(SyncProperties.initialOrg)){
					name = name+"（来自杭州）";
				}
				
				Department department_insert = new Department();
				department_insert.setName(name);
				department_insert.setFullname(name);
				department_insert.setContactPerson(contactPerson);
				department_insert.setContactNumber(contactNumber);
				department_insert.setAddress(address);
				department_insert.setDescription(description);
				department_insert.setParentdep(department_p);
				

				DepartmentSN sn = snGenerator.generateDepartmentSN(department_p);
				department_insert.setSn(sn.getBase() + sn.getNumber());
				if(orgcoding.equals(SyncProperties.initialOrg)){
					department_insert.setSn(sn.getBase() + sn.getNumber());
				}
				
				int res = 0;
				res = iDepartmentService.insertDepartment(department_insert, sn);
				if(res==1){
					Department department_query = iDepartmentService.selectDepBySN(sn.getBase() + sn.getNumber());
					SyncOrg syncOrg_insert = new SyncOrg();
					syncOrg_insert.setOrgcoding(orgcoding);
					syncOrg_insert.setDepId(department_query.getId());
					syncOrg_insert.setDevcoding(devcoding);
					logger.info("待插入的组织信息："+syncOrg_insert.toString());
					res = iSyncOrgService.insert(syncOrg_insert);
				}
				if (res == 1) {
					result = SyncOrgError.getErrResult(dp, OrgError.success);
					logger.info("插入组织成功");
				} else {
					result = SyncOrgError.getErrResult(dp, OrgError.other);
					logger.info("插入组织失败");
				}
			}
			// 更新组织
			else {
				String name = orgMap.get("orgname");
				String contactPerson = orgMap.get("principalname");
				String contactNumber = orgMap.get("orgphone");
				String address = orgMap.get("orgoffice");
				String description = orgMap.get("orgallname");
				
				Department department_update = iDepartmentService.getDepartmentById(syncOrg.getDepId());
				if (name != null && (!name.trim().equals(""))) {
					if(orgcoding.equals(SyncProperties.initialOrg)){
						name = name+"（来自杭州）";
					}
					department_update.setName(name);
					department_update.setFullname(name);
				}
				if (contactPerson != null && (!contactPerson.trim().equals(""))) {
					department_update.setContactPerson(contactPerson);
				}
				if (contactNumber != null && (!contactNumber.trim().equals(""))) {
					department_update.setContactNumber(contactNumber);
				}
				if (address != null && (!address.trim().equals(""))) {
					department_update.setAddress(address);
				}
				if (description != null && (!description.trim().equals(""))) {
					department_update.setDescription(description);
				}
				int res = 0;
				logger.info("待更新的组织信息为："+department_update.toString());
				res = iDepartmentService.updateDepartment(department_update);
				if (res == 1) {
					logger.info("更新组织成功");
					result = SyncOrgError.getErrResult(dp, OrgError.success);
				} else {
					logger.info("更新组织失败");
					result = SyncOrgError.getErrResult(dp, OrgError.other);
				}
			}
		} catch (Exception e) {
			logger.info("从args中获取参数失败");
			return SyncOrgError.getErrResult(dp, OrgError.other);
		}
		return result;
	}
	
	// 删除组织
	public String deleteOrg(String servicecode, String time, String sign, String args, String datatype,
			String describe, String method, String orgcoding){
		String result = "";
		IDataParse dp = DataParseFactory.factory(datatype);
		boolean vali = Sign.sign(servicecode, time, sign);
		if (!vali) {
			return SyncOrgError.getErrResult(dp, OrgError.appForbidSync);
		}
		if (orgcoding.isEmpty()) {
			logger.info("orgcoding为空");
			return SyncOrgError.getErrResult(dp, OrgError.argsIllegal);
		}
		SyncOrg syncOrg = iSyncOrgService.selectByOrgCoding(orgcoding);
		if (syncOrg == null) {
			logger.info("syncOrg表中没有orgcoding对应的组织");
			return SyncOrgError.getErrResult(dp, OrgError.orgNotExist);
		}
		int res = 0;
		try {
			res = iDepartmentService.delDepartment(syncOrg.getDepId());
			if(res==1) res = iSyncOrgService.deteleByOrgCoding(orgcoding);
		} catch (DepartmentException e) {
			e.printStackTrace();
		}
		if (res == 1) {
			logger.info("组织删除成功");
			result = SyncOrgError.getErrResult(dp, OrgError.success);
		} else {
			logger.info("组织删除失败");
			result = SyncOrgError.getErrResult(dp, OrgError.other);
		}
		return result;
	}
	
}
