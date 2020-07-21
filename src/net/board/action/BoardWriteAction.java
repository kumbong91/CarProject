package net.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.db.BoardBean;
import net.board.db.BoardDAO;

public class BoardWriteAction implements Action{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardWriteAction execute()");
		
		request.setCharacterEncoding("utf-8");
	
		BoardBean bb=new BoardBean();
		
		bb.setName(request.getParameter("name"));
		bb.setPass(request.getParameter("pass"));
		bb.setSubject(request.getParameter("subject"));
		bb.setContent(request.getParameter("content"));
		bb.setIp(request.getRemoteAddr());
		
		BoardDAO bdao= new BoardDAO();
		
		bdao.insertBoard(bb);
		
		ActionForward forward=new ActionForward();
		forward.setRedirect(true);
		forward.setPath("./BoardList.bo");
		return forward;
	}
}
