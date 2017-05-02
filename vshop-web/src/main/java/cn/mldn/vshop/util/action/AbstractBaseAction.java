package cn.mldn.vshop.util.action;

import java.io.IOException;
import java.util.Set;

import cn.mldn.util.action.ActionMessageUtil;
import cn.mldn.util.web.ServletObjectUtil;

public abstract class AbstractBaseAction {
	/**
	 * 验证用户是否具备角色
	 * @param role 要验证的角色
	 * @return 具备该角色返回true，否则false
	 */
	public boolean isRole(String role) {
		Set<String> roles = (Set<String>) ServletObjectUtil.getSession().getAttribute("allRoles");
		return roles.contains(role);
	}
	/**
	 * 验证用户是否具备权限
	 * @param action	要验证的权限
	 * @return	具备该权限返回true，否则false
	 */
	public boolean isAction(String action) {
		Set<String> actions = (Set<String>) ServletObjectUtil.getSession().getAttribute("allActions");
		return actions.contains(action);
	}
	/**
	 * 验证是否具备角色和权限
	 * @param role	要验证的角色
	 * @param action	要验证的权限
	 * @return	同时具备角色和权限则返回true，否则false
	 */
	public boolean isRoleAndAction(String role, String action) {
		return isRole(role) && isAction(action);
	}
	/**
	 * 从session中取得当前登录用户id
	 * @return 用户id
	 */
	public String getMid() {
		return (String) ServletObjectUtil.getSession().getAttribute("mid");
	}
	/**
	 * 取得路径信息，通过ActionMessageUtil.getUrl()方法获得
	 * 
	 * @param key 资源文件key
	 * @return 取得资源对应的内容
	 */
	public String getUrl(String key) {
		return ActionMessageUtil.getUrl(key);
	}
	/**
	 * 通过Request属性范围设置msg与url两个参数的内容
	 * @param urlKey url参数的key
	 * @param msgKey msg参数的key
	 * @param param msg参数的占位符
	 */
	public void setUrlAndMsg(String urlKey,String msgKey, Object...param) {
		ServletObjectUtil.getRequest().setAttribute("url", this.getUrl(urlKey));
		ServletObjectUtil.getRequest().setAttribute("msg", this.getMsg(msgKey, param));
	}

	/**
	 * 取得提示文字信息，通过ActionMessageUtil.getMsg()方法获得
	 * 
	 * @param key 资源文件key
	 * @param param 占位符数据
	 * @return 取得资源对应的内容
	 */
	public String getMsg(String key, Object... param) {
		return ActionMessageUtil.getMsg(key, param);
	}

	/**
	 * 进行信息打印输出操作，主要为ajax异步处理操作提供支持
	 * 
	 * @param value 要打印的对象内容
	 */
	public void print(Object value) {
		try {
			ServletObjectUtil.getResponse().getWriter().print(value);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
