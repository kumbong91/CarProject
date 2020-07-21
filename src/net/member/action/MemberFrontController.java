package net.member.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MemberFrontController extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doProcess(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doProcess(request, response);
	}

	
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		String RequestURI=request.getRequestURI();
		System.out.println(RequestURI);
	
		String contextPath=request.getContextPath();
		
	
		System.out.println(contextPath.length());
		
	
		String command=RequestURI.substring(contextPath.length());
		System.out.println(command);
					
	
		ActionForward forward=null;
	
		
		Action action=null;
			
	
		if(command.equals("/MemberJoin.me")){
		
			
			forward=new ActionForward();
			
			forward.setRedirect(false);
			
			forward.setPath("./CarMain.jsp?center=member/join.jsp");
			
		
		}else if(command.equals("/MemberJoinAction.me")){
	
			
			action=new MemberJoinAction();
			
			try {
				
				forward=action.execute(request, response);
						
			} catch (Exception e) {
				e.printStackTrace();
			}
			
	
		}else if(command.equals("/MemberLogin.me")){ 
			
			forward=new ActionForward();
			
			forward.setRedirect(false); 
			
			forward.setPath("./CarMain.jsp?center=member/login.jsp"); 
		
		
		}else if(command.equals("/MemberLoginAction.me")){
			
		
			action=new MemberLoginAction();
			
			try {
				
				forward=action.execute(request, response);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
						
		}else if(command.equals("/MemberLogout.me")){
			
			
			action=new MemberLogoutAction();
			try {
				
				forward=action.execute(request, response); //return null;

			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
		else if(command.equals("/Main.me")){
			
			forward=new ActionForward();
			forward.setRedirect(false);
			forward.setPath("./CarMain.jsp");
		}
		
		
		if(forward!=null){ 
			if(forward.isRedirect()){
				
				response.sendRedirect(forward.getPath());
				
			}else{
				
				RequestDispatcher dispatcher=request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
			}
			
		}//if 
		
	}//	doProcess();
	
}



