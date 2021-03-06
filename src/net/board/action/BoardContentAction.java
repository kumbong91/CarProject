package net.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.db.BoardBean;
import net.board.db.BoardDAO;

public class BoardContentAction implements Action{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardContentAction execute()");
	
		int num=Integer.parseInt(request.getParameter("num"));
	
		String pageNum=request.getParameter("pageNum");
		
		
		BoardDAO bdao=new BoardDAO();
		
		bdao.updateReadcount(num);
		
		BoardBean bb=bdao.getBoard(num);
		
		
		request.setAttribute("bb", bb);
		
		request.setAttribute("pageNum", pageNum);
		
		ActionForward forward=new ActionForward();
		forward.setRedirect(false);
		forward.setPath("./CarMain.jsp?center=board/content.jsp");
		return forward;
	}
}
