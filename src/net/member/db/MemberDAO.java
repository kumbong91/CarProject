package net.member.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MemberDAO {
	
	private Connection getConnection() throws Exception{

		Connection con=null;
		
		Context init=new InitialContext();
		DataSource ds=(DataSource)init.lookup("java:comp/env/jdbc/jspbeginner");
		con=ds.getConnection();
		return con;
	}
	
	public boolean insertMember(MemberBean mb){

		Connection con=null;
		String sql="";
		PreparedStatement pstmt=null;
		
		int result = 0; 
		try{
			
			con=getConnection();
			
			sql="insert into member2(id,pass,name,age,date,email,address,phone,mobile) values(?,?,?,?,?,?,?,?,?)";
			pstmt =con.prepareStatement(sql);
			pstmt.setString(1,mb.getId()); 
			pstmt.setString(2,mb.getPass());
			pstmt.setString(3,mb.getName()); 
			pstmt.setInt(4,mb.getAge()); 
			pstmt.setTimestamp(5,mb.getDate()); 
			pstmt.setString(6, mb.getEmail());
			pstmt.setString(7, mb.getAddress());
			pstmt.setString(8, mb.getPhone());
			pstmt.setString(9, mb.getMobile());
		
			result = pstmt.executeUpdate(); 
			
			
			if(result != 0){
				return true;
			}
		}catch(Exception e){
		
			e.printStackTrace();
		}finally{
		
			if(pstmt!=null)try{pstmt.close();}catch(SQLException ex){}
			if(con!=null)try{con.close();}catch(SQLException ex){}
		}
		
		return false; 
		
	}//insertMember();
	
	
	public int userCheck(String id,String pass){
		Connection con=null;
		String sql="";
		PreparedStatement pstmt=null;
		int check=-1;
					
		ResultSet rs=null;
		try {
			
			con=getConnection();
		
			sql="select pass from member2 where id=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs=pstmt.executeQuery();
			
			if(rs.next()){
				
				if(pass.equals(rs.getString("pass"))){
					check=1;
				
				}else{
					check=0;
				}
			
			}else{
				check=-1; 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			
			if(rs!=null)try{rs.close();}catch(SQLException ex){}
			if(pstmt!=null)try{pstmt.close();}catch(SQLException ex){}
			if(con!=null)try{con.close();}catch(SQLException ex){}
		}
		return check;
		
	}//userCheck();	

	public MemberBean getMember(String id){
		Connection con=null;
		String sql="";
		PreparedStatement pstmt=null;
		int check=-1;
		ResultSet rs=null;
		MemberBean mb=null;
		try {
		
			con=getConnection();
			
			sql="select * from member2 where id=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, id);
		
			rs=pstmt.executeQuery();
			
			if(rs.next()){
				mb=new MemberBean();
				mb.setId(rs.getString("id"));
				mb.setPass(rs.getString("pass"));
				mb.setName(rs.getString("name"));
				mb.setAge(rs.getInt("age"));
				mb.setDate(rs.getTimestamp("date"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(rs!=null)try{rs.close();}catch(SQLException ex){}
			if(pstmt!=null)try{pstmt.close();}catch(SQLException ex){}
			if(con!=null)try{con.close();}catch(SQLException ex){}
		}
		return mb;
		
	}//getMember();
	
	
	public List getMemberList(){
		Connection con=null;
		String sql="";
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List memberList=new ArrayList();
		try {
		
			con=getConnection();
			
			sql="select * from member2";
			pstmt=con.prepareStatement(sql);
		
			rs=pstmt.executeQuery();
			
			while(rs.next()){
				MemberBean mb=new MemberBean();
				mb.setId(rs.getString("id"));
				mb.setPass(rs.getString("pass"));
				mb.setName(rs.getString("name"));
				mb.setAge(rs.getInt("age"));
				mb.setDate(rs.getTimestamp("date"));
				memberList.add(mb);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(rs!=null)try{rs.close();}catch(SQLException ex){}
			if(pstmt!=null)try{pstmt.close();}catch(SQLException ex){}
			if(con!=null)try{con.close();}catch(SQLException ex){}
		}
		return memberList;
	
	}//getMemberList();
	
	
}
