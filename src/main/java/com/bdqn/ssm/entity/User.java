package com.bdqn.ssm.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 鐢ㄦ埛绫?
 * @author 鍏冨ぇ绁?
 *
 */
public class User {

	private Long id;
	private Integer gender;
	private Long userRole;
	private Long createdBy;
	private Long modifyBy;
	@NotNull(message="用户编码不能为空")
	private String userCode;
	@NotNull(message="用户名不能为空")
	@Length(message="用户名必须是6~20字符之间",min=6,max=20)
	private String userName;
	@NotNull(message="密码不能为空")
	@Length(message="密码必须是6~16字符之间",min=6,max=16)
	private String userPassword;
	private String phone;
	private String address;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date birthday;
	private Date createDate;
	private Date modifyDate;
	
	private String roleName;
	private String idPicPath;
	private String workPicPath;
	
	public String getWorkPicPath() {
		return workPicPath;
	}
	public void setWorkPicPath(String workPicPath) {
		this.workPicPath = workPicPath;
	}
	public String getIdPicPath() {
		return idPicPath;
	}
	public void setIdPicPath(String idPicPath) {
		this.idPicPath = idPicPath;
	}
	/**确认密码*/
	@NotNull(message="确认密码不能为空")
	private String userRemi;
	
	public String getUserRemi() {
		return userRemi;
	}
	public void setUserRemi(String userRemi) {
		this.userRemi = userRemi;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public Long getUserRole() {
		return userRole;
	}
	public void setUserRole(Long userRole) {
		this.userRole = userRole;
	}
	public Long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	public Long getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(Long modifyBy) {
		this.modifyBy = modifyBy;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	
}
