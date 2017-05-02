package cn.mldn.vshop.action.front;

import cn.mldn.util.factory.Factory;
import cn.mldn.util.web.ModelAndView;
import cn.mldn.vshop.service.front.IMemberServiceFront;
import cn.mldn.vshop.util.action.AbstractBaseAction;
import cn.mldn.vshop.vo.Member;

public class MemberCenterActionFront extends AbstractBaseAction {
	private static  final String MEMBER_FLAG = "用户信息";
	private IMemberServiceFront memberService = Factory.getServiceInstance("member.service.front");
	/**
	 * 先进行用户角色权限判断<br>
	 * 用户基本信息跟新前的取得，用以进行表单回填
	 * @return 根据mid查到的用户信息，以vo类对象的形式返回
	 */
	public ModelAndView editBasePre() {
		ModelAndView mav = new ModelAndView();
		if (super.isRoleAndAction("info","info:edit")) {
			mav.setUrl(super.getUrl("membercenter.edit.page"));
			try {
				mav.add("memberBase", memberService.getEditBasePre(super.getMid()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			mav.setUrl(super.getUrl("forward.front.page"));
			super.setUrlAndMsg("index.page", "unaction.msg");
		}
		return mav;
	}
	/**
	 * 先进行用户角色权限判断，再进行用户基本信息修改
	 * @param 包含要修改信息的vo类对象
	 * @return	跳转路径 forward_front.jsp
	 */
	public String editBase(Member vo) {
		vo.setMid(super.getMid());
		if (super.isRoleAndAction("info","info:edit")) {
			try {
				if (memberService.editBase(vo)) {
					super.setUrlAndMsg("membercenter.edit.action", "action.edit.success", MEMBER_FLAG);
				} else {
					super.setUrlAndMsg("membercenter.edit.action", "action.edit.failure", MEMBER_FLAG);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			super.setUrlAndMsg("index.page", "unaction.msg");
		}
		return super.getUrl("forward.front.page");
	}
}
