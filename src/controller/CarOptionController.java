package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.CarOrderBean;


@WebServlet("/CarOptionController.do")
public class CarOptionController extends HttpServlet {
	
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
	   
		int carqty = Integer.parseInt(request.getParameter("carqty"));
		int carprice = Integer.parseInt(request.getParameter("carprice"));
		int carreserveday = Integer.parseInt(request.getParameter("carreserveday"));
		
			
		int carins = Integer.parseInt(request.getParameter("carins"));
		
		
		int carwifi = Integer.parseInt(request.getParameter("carwifi"));

		
		int carnave = Integer.parseInt(request.getParameter("carnave"));       
		
		
		int carbabyseat = Integer.parseInt(request.getParameter("carbabyseat"));
		

		
		int totalreserve = carqty * carreserveday * carprice;
		
		int totaloption = 
((carins * carreserveday) + (carwifi * carreserveday) + (carbabyseat*carreserveday)) * 10000 * carqty;
		
		
		CarOrderBean cbean = new CarOrderBean();
		cbean.setCarno(Integer.parseInt(request.getParameter("carno")));
		cbean.setCarqty(carqty);
		cbean.setCarreserveday(carreserveday);
		cbean.setCarins(carins);
		cbean.setCarnave(carnave);
		cbean.setCarbabyseat(carbabyseat);
		cbean.setCarbegindate(request.getParameter("carbegindate"));
		
		
		request.setAttribute("cbean", cbean);
		request.setAttribute("totalreserve", totalreserve);
		request.setAttribute("totaloption", totaloption);
		
		
		RequestDispatcher dis = 
				request.getRequestDispatcher("CarMain.jsp?center=CarOrder.jsp");
		dis.forward(request, response);			
	}
}
