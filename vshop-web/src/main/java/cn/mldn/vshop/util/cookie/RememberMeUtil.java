package cn.mldn.vshop.util.cookie;

import java.util.Base64;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RememberMeUtil {
	private static final String REMEMBER_ME_TITLE = "menuuser" ;
	private HttpServletRequest request ;
	private HttpServletResponse response ;
	public RememberMeUtil(HttpServletRequest request,HttpServletResponse response) {
		this.request = request ;
		this.response = response ;
	}
	public void saveRemeber(String mid,String password) {
		String str = mid + ":" + password ;
		String newStr = new String(Base64.getEncoder().encode(str.getBytes())) ;
		Cookie c = new Cookie(REMEMBER_ME_TITLE,newStr) ;
		c.setMaxAge(864000);
		c.setPath(this.request.getContextPath()); 
		this.response.addCookie(c); 
	}
	public void clear() {
		Cookie c = new Cookie(REMEMBER_ME_TITLE,"") ;
		c.setMaxAge(0);
		c.setPath(this.request.getContextPath()); 
		this.response.addCookie(c);
	}
	/**
	 * 数组的第一个元素是用户名，第二个元素是密码
	 * @return 数组
	 */
	public String[] loadRemember() {
		Cookie c[] = this.request.getCookies() ;	// 取得全部的Cookie的数据
		if (c == null) {
			return null ;	// 没有信息
		}
		for (int x = 0 ; x < c.length ; x ++) {
			if (REMEMBER_ME_TITLE.equalsIgnoreCase(c[x].getName())) {	// 有指定的Cookie数据
				String str = c[x].getValue() ;	// 加密数据
				if ("".equals(str)) {
					return null ;
				}
				String old = new String(Base64.getDecoder().decode(str.getBytes())) ;
				return old.split(":") ;
			}
		}
		return null ;
	}
}
