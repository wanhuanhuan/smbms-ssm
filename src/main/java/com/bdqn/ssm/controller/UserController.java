package com.bdqn.ssm.controller;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bdqn.ssm.entity.User;
import com.bdqn.ssm.service.UserService;
import com.bdqn.ssm.tools.PageEntity;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	public UserService userService;
	
	@RequestMapping("/index")
	public String index(String userName,
						Integer userRole,
						Integer pageIndex,
						Model model){
		PageEntity pageEntity = new PageEntity();
		if(pageIndex!=null && !"".equals(pageIndex)){
			pageEntity.setPageIndex(pageIndex);
		}
		model.addAttribute("userName", userName);
		try {
			pageEntity = userService.findUserByCondition(userName,userRole,pageEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		model.addAttribute("page", pageEntity);	
		return "user/index";
	}
	@RequestMapping("/add")
	public String add(@ModelAttribute User user){
		
		return "user/add";
	}
	
	/*@RequestMapping("/insert")
	public String insert(User user,
						 HttpSession session){
		boolean flag=userService.insert(user,session);
		String msg="添加失败";
		if(flag){
			msg="添加成功";
		}
		
		return "redirect:/user/index";
	}*/
	/**
	 * jsr303数据校验的演示
	 * @param user
	 * @param model
	 * @param session
	 * @return
	 *//*
	@RequestMapping("/insert")
	public String doCreate(@Valid User user,
						   BindingResult bindResult,
						   Model model,
			               HttpSession session){
		if(bindResult.hasErrors()){//如果BindingResult中存在错误
			return "user/add";
		}
		
		boolean flag = userService.insert(user, session);
		String createMsg = "服务器错误!";
		if(flag){//表示添加成功
			createMsg = "添加成功";
		}
		model.addAttribute("createMsg", createMsg);
		return "redirect:/user/index";
	}*/
	/**
	 * 单文件上传的表单
	 * @param user
	 * @param model
	 * @param session
	 * @return
	 *//*
	@RequestMapping(value="/doCreate",method=RequestMethod.POST)
	public String doCreate(MultipartFile attache,
						   @Valid User user,
						   BindingResult bindResult,
						   Model model,
			               HttpSession session){
		//保存用户
		if(bindResult.hasErrors()){//如果BindingResult中存在错误
			return "user/addview";
		}
		if(!attache.isEmpty()){//判断用户是否要做文件上传的操作
			//表示用户有证件照需要上传
			//获取上传文件的名称
			String filename = attache.getOriginalFilename();
			//获取文件的大小,单位字节
			long size = attache.getSize();
			//获取文件的后缀
			String suffix = FilenameUtils.getExtension(filename);
			//加入我们规定文件上传的大小不能超过1M
			long maxUploadFileSize = 1*1024*1024;
			if(size>maxUploadFileSize){//超过上传文件的最大值，不允许上传，给予相应的提示
				model.addAttribute("uploadMsg", "上传的文件过大,上传的文件的大小应小于1M");
				return "user/addview";
			}
			if(!("jpg".equalsIgnoreCase(suffix)
					||"png".equalsIgnoreCase(suffix)
					||"gif".equalsIgnoreCase(suffix))){//判断文件的格式
				model.addAttribute("uploadMsg", "上传的文件的格式不正确,上传的文件的格式必须为:jpg,png,fig");
				return "user/addview";
			}
			try {
				//如果代码能走到这里说明符合上传要求,开始文件的上传
				//服务器的位置G:/dev/myeclipse-tomcat/apache-tomcat-7.0.47/apache-tomcat-7.0.4/webapps/smbms-ssm/upload
				String serverPath = session.getServletContext().getRealPath("/upload");
				//设置文件的上传的文件在服务器上的名称
				String uploadFileName = System.currentTimeMillis()+"."+suffix;
				//上传文件的路径就变成了如下
				////服务器的位置G:/dev/myeclipse-tomcat/apache-tomcat-7.0.47/apache-tomcat-7.0.4/webapps/smbms-ssm/upload/asdfasdfa.jpg
				String pathname = serverPath+File.separator+uploadFileName;
				
				File file = new File(pathname);//设置上传到服务器的那个文件
				//文件上传
				//attache.transferTo(file);
				
				InputStream is = attache.getInputStream();
				FileUtils.copyInputStreamToFile(is, file);
				//设置User对象中的证件照片的名称
				user.setIdPicPath(uploadFileName);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		boolean flag = userService.insert(user, session);
		String createMsg = "服务器错误!";
		if(flag){//表示添加成功
			createMsg = "添加成功";
		}
		model.addAttribute("createMsg", createMsg);
		return "redirect:/user/index";
	}*/
	
	/**
	 * 多文件上传的表单
	 * @param user
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/insert",method=RequestMethod.POST)
	public String doCreate(@RequestParam MultipartFile[] attaches,
						   @Valid User user,
						   BindingResult bindResult,
						   Model model,
			               HttpSession session){
		//保存用户
		if(bindResult.hasErrors()){//如果BindingResult中存在错误
			return "user/add";
		}
		for (int i = 0; i < attaches.length; i++) {
			MultipartFile attache=attaches[i];
			if(!attache.isEmpty()){//判断用户是否要做文件上传的操作
				//表示用户有证件照需要上传
				//获取上传文件的名称
				String filename = attache.getOriginalFilename();
				//获取文件的大小,单位字节
				long size = attache.getSize();
				//获取文件的后缀
				String suffix = FilenameUtils.getExtension(filename);
				//加入我们规定文件上传的大小不能超过1M
				long maxUploadFileSize = 1*1024*1024;
				if(size>maxUploadFileSize){//超过上传文件的最大值，不允许上传，给予相应的提示
					model.addAttribute("uploadMsg", "上传的文件过大,上传的文件的大小应小于1M");
					return "user/add";
				}
				if(!("jpg".equalsIgnoreCase(suffix)
						||"png".equalsIgnoreCase(suffix)
						||"gif".equalsIgnoreCase(suffix))){//判断文件的格式
					model.addAttribute("uploadMsg", "上传的文件的格式不正确,上传的文件的格式必须为:jpg,png,fig");
					return "user/add";
				}
				try {
					//如果代码能走到这里说明符合上传要求,开始文件的上传
					//服务器的位置G:/dev/myeclipse-tomcat/apache-tomcat-7.0.47/apache-tomcat-7.0.4/webapps/smbms-ssm/upload
					String serverPath = session.getServletContext().getRealPath("/upload");
					//设置文件的上传的文件在服务器上的名称
					String uploadFileName = System.currentTimeMillis()+"."+suffix;
					//上传文件的路径就变成了如下
					////服务器的位置G:/dev/myeclipse-tomcat/apache-tomcat-7.0.47/apache-tomcat-7.0.4/webapps/smbms-ssm/upload/asdfasdfa.jpg
					String pathname = serverPath+File.separator+uploadFileName;
					
					File file = new File(pathname);//设置上传到服务器的那个文件
					//文件上传
					//attache.transferTo(file);
					
					InputStream is = attache.getInputStream();
					FileUtils.copyInputStreamToFile(is, file);
					//设置User对象中的证件照片的名称
					if(i==0){
						user.setIdPicPath(uploadFileName);
					}else if(i==1){
						user.setWorkPicPath(uploadFileName);
					}
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		boolean flag = userService.insert(user, session);
		if(flag){//表示添加成功
			System.out.println("<script>alert('添加成功！');</script>");
		}else{
			System.out.println("<script>alert('添加失败！');</script>");
		}
		return "redirect:/user/index";
	}
	
	@RequestMapping("/detail")
	public String detail(String id,
						 HttpServletRequest request){
		User user=userService.findUserById(id);
		request.setAttribute("user", user);
		return "user/detail";
	}
	@RequestMapping("/updateview/{id}")
	public String updateview(@PathVariable String id,
						 HttpServletRequest request){
		User user=userService.findUserById(id);
		request.setAttribute("user", user);
		return "user/update";
	}
	@RequestMapping("/update")
	public String update(User user,HttpSession session){
		boolean flag = userService.update(user,session);
		if(flag){
			System.out.println("<script>alert('修改成功！');</script>");
		}else{
			System.out.println("<script>alert('修改失败！');</script>");
		}
		return "redirect:/user/index";
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String,Object> delete(@RequestParam Integer userId){
		boolean flag=userService.delete(userId);
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("flag", flag);
		return resultMap;
	}
}
