package controller;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.CarDAO;
import db.CarListBean;


@WebServlet("/CarListController.do")
public class CarListController extends HttpServlet {

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

		
		CarDAO cdao = new CarDAO();
		
		
		Vector<CarListBean> v = cdao.getAllCarlist();

		
		request.setAttribute("v", v); 

		
		RequestDispatcher dis = 
				request.getRequestDispatcher("CarMain.jsp?center=CarList.jsp");
		
		dis.forward(request, response);

	}

}
