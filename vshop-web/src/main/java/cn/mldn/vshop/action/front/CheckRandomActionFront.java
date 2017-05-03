package cn.mldn.vshop.action.front;

import cn.mldn.util.web.ServletObjectUtil;
import cn.mldn.vshop.util.action.AbstractBaseAction;

public class CheckRandomActionFront extends AbstractBaseAction {
	public void check(String code) {
		try {
			String rand = (String) ServletObjectUtil.getSession().getAttribute("rand") ;
			if (rand == null) {
				super.print(false);
			} else {
				super.print(rand.equalsIgnoreCase(code));
			}
		} catch(Exception e){}
	}
}
