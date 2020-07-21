package net.board.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.db.BoardBean;
import net.board.db.BoardDAO;

public class BoardListAction implements Action{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardListAction execute()");
	
		BoardDAO bdao=new BoardDAO();
		
		int count=bdao.getBoardCount();
		
		int pageSize=5;
		
		String pageNum=request.getParameter("pageNum");
		if(pageNum==null){
			pageNum="1";
		}
		
		int currentPage=Integer.parseInt(pageNum);
		int startRow=(currentPage-1)*pageSize+1;
		
		int endRow=currentPage*pageSize;
		List<BoardBean> boardList=null;
	
		if(count!=0){
			boardList=bdao.getBoardList(startRow, pageSize);
		}
		
		int pageCount =count/pageSize+(count%pageSize==0?0:1);
		
		int pageBlock=3;
		
		int startPage=((currentPage-1)/pageBlock)*pageBlock+1;
		
		int endPage=startPage+pageBlock-1;
		if(endPage > pageCount){
			endPage = pageCount;
		}
		
		request.setAttribute("count", count); 
		request.setAttribute("boardList", boardList); 
		request.setAttribute("pageNum", pageNum); 
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("pageBlock", pageBlock);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
	
		ActionForward forward=new ActionForward();
		forward.setRedirect(false);
		forward.setPath("./CarMain.jsp?center=board/list.jsp");
		return forward;
	}
}
