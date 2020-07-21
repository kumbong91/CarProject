package controller;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.CarConfirmBean;
import db.CarDAO;


@WebServlet("/CarReserveConfirmController.do")
public class CarReserveConfirmController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		requestpro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		requestpro(request, response);
	}


	private void requestpro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String memberphone = request.getParameter("memberphone");
		String memberpass = request.getParameter("memberpass");
			
	
		CarDAO cdao = new CarDAO();
		
		
		Vector<CarConfirmBean> v = cdao.getAllCarOrder(memberphone,memberpass); 
		
		
		request.setAttribute("v", v);
		RequestDispatcher dis =
				request.getRequestDispatcher("CarMain.jsp?center=CarReserveResult.jsp");
		dis.forward(request, response);
		
		
	}

}








