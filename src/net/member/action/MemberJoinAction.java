package net.member.action;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.member.db.MemberBean;
import net.member.db.MemberDAO;


public class MemberJoinAction implements Action{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		System.out.println("MemberJoinAction execute()");
		
		
		request.setCharacterEncoding("utf-8");
		
		
		
		MemberBean mb=new MemberBean();
		mb.setId(request.getParameter("id"));
		mb.setPass(request.getParameter("pass")); 
		mb.setName(request.getParameter("name")); 
		mb.setAddress(request.getParameter("address")); 
		mb.setEmail(request.getParameter("email"));
		mb.setMobile(request.getParameter("mobile"));
		mb.setPhone(request.getParameter("phone"));
		mb.setDate(new Timestamp(System.currentTimeMillis()));
	
		
		boolean result = false;
		
		
		MemberDAO mdao=new MemberDAO();
		
		
		result = mdao.insertMember(mb);
	
		
		if(result == false){
			System.out.println("회원가입 실패");
			return null;
		}
		
		
		ActionForward forward=new ActionForward();
		
		forward.setRedirect(true);
		
		forward.setPath("./MemberLogin.me");
		
		return forward;
	}
}
