package net.board.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import net.board.db.BoardBean;
import net.board.db.BoardDAO;

public class MainAction implements Action{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("MainAction execute()");
		
		BoardDAO bdao=new BoardDAO();
	
		int count=bdao.getBoardCount();
		String pageNum="1";
		List<BoardBean> boardList=null;
		
		if(count!=0){
			boardList=bdao.getBoardList(1, 5);
		}
		
		request.setAttribute("count", count);
		request.setAttribute("boardList", boardList);
		request.setAttribute("pageNum", pageNum);
		
		ActionForward forward=new ActionForward();
		forward.setRedirect(false);
		forward.setPath("./main/main.jsp");
		return forward;
	}
}
