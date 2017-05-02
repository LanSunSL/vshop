package cn.mldn.vshop.dao;

import java.sql.SQLException;
import java.util.Date;

import cn.mldn.util.dao.IBaseDAO;
import cn.mldn.vshop.vo.Member;

public interface IMemberDAO extends IBaseDAO<String, Member> {
	/**
	 * 进行用户最后一次登录日期的更新处理
	 * @param mid 用户id
	 * @param lastdate 用户最后一次登录日期
	 * @return 更新成功返回true，否则返回false
	 * @throws SQLException 通过PreparedStatement执行操作返回的是SQL异常，看见SQL异常就查数据层代码
	 */
	public boolean doUpdateLastdate(String mid,Date lastdate) throws SQLException ;
	/**
	 * 进行登录验证处理，只验证用户名和密码的额信息
	 * @param mid 用户编号
	 * @param password 必须是加密后的数据
	 * @return 如果登录成功则将用户的相关信息保存在Member对象之中，并且返回Member的实例，如果用户名密码不存在则返回null
	 * @throws SQLException 通过PreparedStatement执行操作返回的是SQL异常，看见SQL异常就查数据层代码
	 */
	public Member findLogin(String mid,String password) throws SQLException ; 
	/**
	 * 修改用户基本信息：包括name，phone，email
	 * @param mid	要修改的用户信息的vo类对象
	 * @return	修改成功返回true，否则false
	 * @throws SQLException	SQL异常
	 */
	public boolean doUpdateBase(Member vo) throws SQLException;
	/**
	 * 修改用户密码
	 * @param mid	要修改的用户id
	 * @param newpassword	新的密码
	 * @return	修改成功返回true，否则false
	 * @throws SQLException	SQL异常
	 */
	public boolean doUpdatePassword(String mid, String password) throws SQLException;
}
