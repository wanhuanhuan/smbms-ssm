package com.bdqn.ssm.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdqn.ssm.entity.*;
import com.bdqn.ssm.mapper.ProviderMapper;
import com.bdqn.ssm.service.ProviderService;
import com.bdqn.ssm.tools.Page;
import com.bdqn.ssm.tools.SysContent;

@Service
public class ProviderServiceImpl implements ProviderService {

	@Autowired
	public ProviderMapper providerMapper;
	public Page findBillByCondition(String findParam, Page page) {
		Map<String,Object> paramValues = new HashMap<String,Object>();
		paramValues.put("findParam", findParam);
		int totalCount = providerMapper.totalCount(paramValues);
		page.setTotalCount(totalCount);
		paramValues.put("start", (page.getPageIndex()-1)*page.getPageSize());//设置从那一条数据开始查询
		paramValues.put("end", page.getPageSize());//设置查询对少条数据
		List<Provider> proList = providerMapper.findByCondition(paramValues);
		page.setList(proList);
		return page;
	}
	public Provider findProviderById(Integer id) {
		if(id==0){//传过来的userId为空
			return null;
		}
		Map<String,Object> paramValues = new HashMap<String,Object>();
		paramValues.put("proId", id);
		List<Provider> proList = providerMapper.selectByCondition(paramValues);
		if(proList!=null && proList.size()==1){
			return proList.get(0);
		}
		return null;
	}
	public boolean insert(Provider pro, HttpSession session) {
		//位添加的用户设置创建者
		User loginUser = (User) session.getAttribute(SysContent.LOGINSESSION);
		pro.setCreatedBy(loginUser.getId());
		//位添加的用户设置创建时间
		pro.setCreateDate(new Date());
		int num = providerMapper.insert(pro);
		if(num>0){
			return true;
		}
		return false;
	}
	public boolean update(Provider pro, HttpSession session) {
		//位添加的用户设置创建者
		User loginUser = (User) session.getAttribute(SysContent.LOGINSESSION);
		pro.setCreatedBy(loginUser.getId());
		//位添加的用户设置创建时间
		pro.setCreateDate(new Date());
		int num = providerMapper.update(pro);
		if(num>0){
			return true;
		}
		return false;
	}
	public boolean delete(Integer proId) {
		int num=providerMapper.delete(proId);
		if(num>0){
			return true;
		}
		return false;
	}

}
