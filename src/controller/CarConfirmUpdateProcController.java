package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.CarDAO;
import db.CarOrderBean;


@WebServlet("/CarConfirmUpdateProcController.do")
public class CarConfirmUpdateProcController extends HttpServlet {
	
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
		
		int orderid = Integer.parseInt(request.getParameter("orderid"));
		int carreserveday = Integer.parseInt(request.getParameter("carreserveday"));
		int carqty = Integer.parseInt(request.getParameter("carqty"));
		int carins = Integer.parseInt(request.getParameter("carins"));
		int carwifi = Integer.parseInt(request.getParameter("carwifi"));
		int carbabyseat = Integer.parseInt(request.getParameter("carbabyseat"));
		String carbegindate = request.getParameter("carbegindate");
		String memberpass = request.getParameter("memberpass");
		
		
		CarOrderBean bean = new CarOrderBean();
		bean.setOrderid(orderid);
		bean.setCarreserveday(carreserveday);
		bean.setCarbabyseat(carbabyseat);
		bean.setCarbegindate(carbegindate);
		bean.setCarqty(carqty);
		bean.setCarins(carins);
		bean.setCarwifi(carwifi);
		bean.setMemberpass(memberpass);
		
		CarDAO cdao = new CarDAO();
		cdao.carOrderUpdate(bean);
		
		
		RequestDispatcher dis =
			request.getRequestDispatcher("CarListController.do");
		dis.forward(request, response);
		
	
	}
}
