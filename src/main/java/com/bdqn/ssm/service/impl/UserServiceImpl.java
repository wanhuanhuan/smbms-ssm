package com.bdqn.ssm.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.bdqn.ssm.entity.User;
import com.bdqn.ssm.mapper.UserMapper;
import com.bdqn.ssm.service.UserService;
import com.bdqn.ssm.tools.PageEntity;
import com.bdqn.ssm.tools.SysContent;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	
	public User login(String loginParam, String password) {
		User user=userMapper.login(loginParam);
		if(user==null){
			return null;
		}
		String md5Pass=DigestUtils.md5DigestAsHex(password.getBytes());
		if(!md5Pass.equals(user.getUserPassword())){
			return null;
		}
		user.setUserPassword(null);
		return user;
	}
	
	public PageEntity findUserByCondition(String userName,Integer userRole,
			PageEntity pageEntity) {
		// 我们需要为pageEntity对象的属性：totalCount和dataList属性设置属性值
		Map<String, Object> paramValues = new HashMap<String, Object>();
		// 1.totalCount设置属性值
		paramValues.put("userName", userName);
		paramValues.put("userRole", userRole);
		int totalCount = userMapper.totalCount(paramValues);
		pageEntity.setTotalCount(totalCount);
		// 2.dataList设置属性值
		paramValues.put("start",
				(pageEntity.getPageIndex() - 1) * pageEntity.getPageSize());// 设置从那一条数据开始查询
		paramValues.put("size", pageEntity.getPageSize());// 设置查询对少条数据
		List<User> userList = userMapper.findByCondition(paramValues);
		
		//获取当前年份
		Calendar da = Calendar.getInstance();
		int year = da.get(Calendar.YEAR);
		SimpleDateFormat mat = new SimpleDateFormat("yyyy-mm-dd");
		for (User user : userList) {
			//获得用户出生日期的年份
	        long yy = year-Integer.parseInt(mat.format(user.getBirthday()).substring(0,4));
	        user.setModifyBy(yy);
			}
		pageEntity.setDataList(userList);
		return pageEntity;
	}
	
	public User findUserById(String userId){
		Integer id = 0;
		if(userId!=null && !"".equals(userId)){
			id = Integer.parseInt(userId);
		}
		if(id==0){//传过来的userId为空
			return null;
		}
		Map<String,Object> paramValues = new HashMap<String,Object>();
		paramValues.put("userId", id);
		List<User> userList = userMapper.selectByCondition(paramValues);
		if(userList!=null && userList.size()==1){
			return userList.get(0);
		}
		return null;
	}

	public List<User> allUser() {
		List<User> userList = userMapper.selectByCondition(null);
		
		return userList;
	}

	public boolean insert(User user, HttpSession session) {
		// 位添加的用户设置创建者
		User loginUser = (User) session.getAttribute(SysContent.LOGINSESSION);
		user.setCreatedBy(loginUser.getId());
		// 位添加的用户设置创建时间
		user.setCreateDate(new Date());
		// 用户的面加密
		String md5Pass = DigestUtils.md5DigestAsHex(user.getUserPassword()
				.getBytes());
		user.setUserPassword(md5Pass);
		int num = userMapper.insert(user);
		if (num > 0) {
			return true;
		}
		return false;
	}

	public boolean update(User user, HttpSession session) {
		User loginUser = (User) session.getAttribute(SysContent.LOGINSESSION);
		if(loginUser!=null){//修改者是当前登录系统的用户
			user.setModifyBy(loginUser.getId());
		}
		user.setModifyDate(new Date());
		int num = userMapper.update(user);
		if(num>0){
			return true;
		}
		return false;
	}

	public boolean delete(Integer id) {
		int num = userMapper.delete(id);
		if(num>0){
			return true;
		}
		return false;
	}

	public boolean checkIsExiste(String checkType, String password) {
		Integer total = userMapper.checkIsExists(checkType,password);
		if(total!=null && total>0){
			return true;
		}
		return false;
	}

	public boolean pwdIsExiste(Integer id, String password) {
		// TODO Auto-generated method stub
		return userMapper.updateUserPwd(password,id);
	}
}
