package net.member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.member.db.MemberDAO;


public class MemberLoginAction implements Action{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		
		String id=request.getParameter("id");
		String pass=request.getParameter("pass");
		
	
		MemberDAO mdao=new MemberDAO();
		
	 
		
		int check=mdao.userCheck(id, pass);
		
		
		
		if(check==0){
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out=response.getWriter();
			out.println("<script>");
			out.println("alert('아이디 또는 비밀번호틀림');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
			return null;
		
		
		}else if(check==-1){
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out=response.getWriter();
			out.println("<script>");
			out.println("alert('아이디없음');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
			return null;	
		}
		
		
	
		HttpSession session=request.getSession();
		
	
		session.setAttribute("id", id);
		

		ActionForward forward=new ActionForward();
		
		
		forward.setRedirect(true);
		
	
		forward.setPath("./Main.me"); 
		
	
		return forward;
	}
}

