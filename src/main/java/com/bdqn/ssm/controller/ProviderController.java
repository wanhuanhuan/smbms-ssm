package com.bdqn.ssm.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bdqn.ssm.entity.Provider;
import com.bdqn.ssm.service.ProviderService;
import com.bdqn.ssm.tools.Page;

@Controller
@RequestMapping("/provider")
public class ProviderController {

		@Autowired
		public ProviderService providerService;
		
		@RequestMapping("/providerList")
		public String providerList(Integer pageIndex,String findParam,Model model){
			Page page=new Page();
			if(pageIndex!=null && !"".equals(pageIndex)){
				page.setPageIndex(pageIndex);
			}
			page = providerService.findBillByCondition(findParam,page);
			model.addAttribute("page", page);
			return "provider/providerList";
		}
		
		@RequestMapping("/providerAdd")
		public String providerAdd(){
			
			return "provider/providerAdd";
		}
		@RequestMapping("/proAdd")
		public String proAdd(Provider pro,HttpSession session){
			boolean flag = providerService.insert(pro,session);
			if(flag){//表示添加成功
				System.out.println("<script>alert('添加成功！');</script>");
			}else{
				System.out.println("<script>alert('添加失败！');</script>");
			}
			return "redirect:/provider/providerList";
		}
		
		@RequestMapping("/providerUpdate/{id}")
		public String providerUpdate(@PathVariable Integer id,HttpServletRequest request){
			request.setAttribute("list", providerService.findProviderById(id));
			return "provider/providerUpdate";
		}
		
		@RequestMapping("proUpdate")
		public String proUpdate(Provider pro,HttpSession session){
			boolean flag = providerService.update(pro,session);
			if(flag){//表示添加成功
				System.out.println("<script>alert('修改成功！');</script>");
			}else{
				System.out.println("<script>alert('修改失败！');</script>");
			}
			return "redirect:/provider/providerList";
		}
		
		@RequestMapping("/providerView/{id}")
		public String providerView(@PathVariable Integer id,HttpServletRequest request){
			request.setAttribute("list", providerService.findProviderById(id));
			return "provider/providerView";
		}
		
		@RequestMapping("/delete")
		@ResponseBody
		public boolean delete(@RequestParam Integer proId){
			boolean flag=providerService.delete(proId);
			if(flag){
				return true;
			}
			return false;
			
		}
}
