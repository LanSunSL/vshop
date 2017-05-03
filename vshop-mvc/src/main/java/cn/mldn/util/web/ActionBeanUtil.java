package cn.mldn.util.web;

import java.lang.reflect.Method;

import cn.mldn.util.MessageUtil;
import cn.mldn.util.bean.MethodUtil;

public class ActionBeanUtil {
	private static final MessageUtil ACTION_MESSAGE = new MessageUtil("cn.mldn.util.message.action") ;
	private ActionBeanUtil() {}
	/**
	 * 描述的进行指定的Action对象实例化，以及方法的反射调用
	 * @param uriResult 包含两个内容：Action的程序的KEY、方法名称
	 * @return 要跳转的处理路径
	 * @throws Exception 处理失败抛出
	 */
	public static String actionHandle(String uriResult[]) throws Exception {
		String className = ACTION_MESSAGE.getText(uriResult[0]) ;	// 取得调用的类名称
		Class<?> actionClass = Class.forName(className) ;	// 取得Class对象
		Object actionObject = actionClass.newInstance() ;	// 反射实例化对象
		Method actionMethod = MethodUtil.getMethod(actionClass, uriResult[1]) ;
		Object retObj = null ;
		if (actionMethod.getParameterTypes().length == 0) {	// 没有参数
			retObj = actionMethod.invoke(actionObject) ;	// 没有参数调用
		} else {	// 有参数，首先根据参数名称取得方法的内容
			Object methodValue [] = ParameterValueUtil.getMethodParameter(actionClass, actionMethod) ;
			retObj = actionMethod.invoke(actionObject, methodValue) ;
		}
		if (retObj instanceof ModelAndView) {
			return ((ModelAndView) retObj).getUrl() ;
		}
		if (retObj instanceof String) { 
			return retObj.toString() ;
		}
		return null ;
	} 
}
