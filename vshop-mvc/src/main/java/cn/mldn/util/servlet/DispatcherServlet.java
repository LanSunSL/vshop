package cn.mldn.util.servlet;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.mldn.util.action.ActionMessageUtil;
import cn.mldn.util.upload.FileUploadTools;
import cn.mldn.util.web.ActionBeanUtil;
import cn.mldn.util.web.ParameterValidatorUtil;
import cn.mldn.util.web.RequestUrlUtil;
import cn.mldn.util.web.ServletObjectUtil;

/**
 * 项目的设计者需要把这个控制层的程序类编写完整，并且让其功能足够强大。
 * 
 * @author mldn
 */
@SuppressWarnings("serial")
public class DispatcherServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException { // 所有的请求交给服务方法创建请求处理线程

		String requestContextType = request.getContentType(); // 取得当前的表单模式
		if (requestContextType != null) {
			if (requestContextType.contains("multipart/form-data")) { // 表示表单封装
				try {
					FileUploadTools fut = new FileUploadTools(request, 3 * 1024 * 1024,
							this.getServletContext().getRealPath("/upload/"));
					ServletObjectUtil.setUpload(fut);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		ServletObjectUtil.setRequest(request);
		ServletObjectUtil.setResponse(response);
		String actionName = RequestUrlUtil.getActionName(request);
		String urlFlag = "/error" ;
		ParameterValidatorUtil pvu = new ParameterValidatorUtil(actionName);
		if (pvu.validate()) {
			String urlResult[] = RequestUrlUtil.splitUrl(request);
			try {
				// DispatcherServlet程序类只是负责有数据跳转操作。
				urlFlag = ActionBeanUtil.actionHandle(urlResult);
//				System.out.println(urlFlag);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else { // 应该跳转到一个错误页上
			if (ActionMessageUtil.getUrl(actionName + ".error.page") != null) {
				urlFlag = ActionMessageUtil.getUrl(actionName + ".error.page") ;
			} 
		}
		if (requestContextType != null) {
			if (requestContextType.contains("multipart/form-data")) { // 表示表单封装
				if (ServletObjectUtil.getUpload() != null) {
					ServletObjectUtil.getUpload().removeTemp();
				}
			}
		}
		ServletObjectUtil.clear(); // 清空保存的request、response
		if (urlFlag != null) {
			request.getRequestDispatcher(urlFlag).forward(request,
				response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}
}
