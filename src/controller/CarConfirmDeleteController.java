package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.CarDAO;


@WebServlet("/CarConfirmDeleteController.do")
public class CarConfirmDeleteController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		requestpro(request,response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		requestpro(request,response);
	}

	private void requestpro(HttpServletRequest request,
			HttpServletResponse response)throws ServletException, IOException  {
	
	
		int orderid= Integer.parseInt(request.getParameter("orderid"));
		String memberpass = request.getParameter("memberpass");
		
	
		CarDAO cdao = new CarDAO();
		int result = cdao.carOrderDelete(orderid,memberpass);
		
		if(result!=0){
			 
			 response.setContentType("text/html;charset=utf-8"); 
             PrintWriter out = response.getWriter(); 
             out.println("<script>"); 
             out.println("alert('렌트카 예약정보를 삭제 하였습니다.');"); 
             out.println("location.href='CarListController.do'"); 
             out.println("</script>"); 

		}else{
			
			request.setAttribute("result", result);
			
			RequestDispatcher dis = 
					request.getRequestDispatcher("CarMain.jsp?center=CarConfirmDelete.jsp");
				dis.forward(request, response);
			
		}
			
	}
}