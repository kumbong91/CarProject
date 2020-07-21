package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BoardDAO {

	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;


	public void getCon() {
		try {
			
			Context init = new InitialContext();
		
			DataSource ds = (DataSource) init.lookup("java:comp/env/jdbc/jspbeginner");
		
			con = ds.getConnection(); 

		} catch (Exception err) {
			err.printStackTrace();
		}

	}// getCon();

	
	public Vector<BoardBean> getAllContent(int start, int end){
		
		Vector<BoardBean> v = new Vector<BoardBean>();
		
		BoardBean bean = null;
		try {
			
			getCon();
			
			String sql="select * from (select A.* , Rownum Rnum from (Select * from board2 order by ref desc, re_level Asc)A) where Rnum>? and Rnum<=?";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()){
				bean = new BoardBean();
				bean.setNum(rs.getInt(1));
				bean.setWriter(rs.getString(2));
				bean.setEmail(rs.getString(3));
				bean.setSubject(rs.getString(4));
				bean.setPasswd(rs.getString(5));
				bean.setReg_date(rs.getString(6));
				bean.setReadcount(rs.getInt(7));
				bean.setRef(rs.getInt(8));
				bean.setRe_step(rs.getInt(9));
				bean.setRe_level(rs.getInt(10));
				bean.setContent(rs.getString(11));
				
				v.add(bean);
			}
			con.close();	pstmt.close();		
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return v;
	}


	//전체 게시글의 숫자를 리턴하는 메소드
	public int getCount() {
		int result =0;

		try {
			getCon();
			String sql="select count(*) from board2";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()){
				result = rs.getInt(1);
			}
			con.close();	pstmt.close();			
		} catch (Exception e) {
			System.out.println("getCount메소드 내부에서 오류: " + e.getMessage());
		}	
		return result;
	}

	//하나의 게시글을 저장하는 메소드
	public void insertBoard(BoardBean bean) {
		try {
			getCon();
			int ref=1; 
			int re_step=1; 
			int re_level=1;
			int readcount=0;
			
			String refmax="select max(ref) from board";
			pstmt =con.prepareStatement(refmax);
			rs = pstmt.executeQuery();

			if(rs.next()){
				ref = rs.getInt(1);
			}

		
			String sql ="insert into board values(board_seq.NEXTVAL,?,?,?,?,sysdate,?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, bean.getWriter());
			pstmt.setString(2, bean.getEmail());
			pstmt.setString(3, bean.getSubject());
			pstmt.setString(4, bean.getPasswd());
			pstmt.setInt(5, readcount);
			pstmt.setInt(6, ref+1);
			pstmt.setInt(7, re_step);
			pstmt.setInt(8, re_level);
			pstmt.setString(9, bean.getContent());
			pstmt.executeUpdate();		
			con.close();pstmt.close();	
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	//하나의 게시글을 리턴 받는 메소드
	public BoardBean getOneContent(int num) {
		BoardBean bean =new BoardBean();
		try {
			getCon();
			
			String countsql="update board set readcount = readcount+1 where num=?";
			pstmt = con.prepareStatement(countsql);
		
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
			pstmt.close();

			
			String sql="select * from board where num=?";
			pstmt =con.prepareStatement(sql);
			pstmt.setInt(1, num);
		
			rs = pstmt.executeQuery();
			if(rs.next()){
				bean.setNum(rs.getInt(1));
				bean.setWriter(rs.getString(2));
				bean.setEmail(rs.getString(3));
				bean.setSubject(rs.getString(4));
				bean.setPasswd(rs.getString(5));
				bean.setReg_date(rs.getString(6));
				bean.setReadcount(rs.getInt(7));
				bean.setRef(rs.getInt(8));
				bean.setRe_step(rs.getInt(9));
				bean.setRe_level(rs.getInt(10));
				bean.setContent(rs.getString(11));
			}			
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return bean;
	}

	//답글 쓰기 
	public void reWriteBoard(BoardBean bean) {

	
		int ref =bean.getRef();
		int re_step = bean.getRe_step();
		int re_level = bean.getRe_level();
		int readcount=0;
		try {
			getCon();
			
			String levelsql ="update board set re_level=re_level+1 where ref=? and re_level>?";
			pstmt = con.prepareStatement(levelsql);
			pstmt.setInt(1, ref);
			pstmt.setInt(2, re_level);
			pstmt.executeUpdate();
			pstmt.close();
			
			String sql ="insert into board values(board_seq.NEXTVAL,?,?,?,?,sysdate,?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, bean.getWriter());
			pstmt.setString(2, bean.getEmail());
			pstmt.setString(3, bean.getSubject());
			pstmt.setString(4, bean.getPasswd());
			pstmt.setInt(5, readcount);
			pstmt.setInt(6, ref);
			pstmt.setInt(7, re_step+1);
			pstmt.setInt(8, re_level+1);
			pstmt.setString(9, bean.getContent());
			pstmt.executeUpdate();		
			con.close();pstmt.close();			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	//하나의 게시글을 리턴
	public BoardBean getOneUpdate(int num) {
		BoardBean bean =new BoardBean();
		try {
			getCon();
			
			String sql="select * from board where num=?";
			pstmt =con.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			if(rs.next()){
				bean.setNum(rs.getInt(1));
				bean.setWriter(rs.getString(2));
				bean.setEmail(rs.getString(3));
				bean.setSubject(rs.getString(4));
				bean.setPasswd(rs.getString(5));
				bean.setReg_date(rs.getString(6));
				bean.setReadcount(rs.getInt(7));
				bean.setRef(rs.getInt(8));
				bean.setRe_step(rs.getInt(9));
				bean.setRe_level(rs.getInt(10));
				bean.setContent(rs.getString(11));
			}			
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return bean;
	}

	//게시글을 수정 하는 메소드
	public void boardUpdate(int num, String subject, String content) {
		try {
			getCon();
			String sql="update board set content=? , subject=? where num=?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, content);
			pstmt.setString(2, subject);
			pstmt.setInt(3, num);
			pstmt.executeUpdate();
			con.close();			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	//하나의 게시글 삭제
	public int boardDelete(int num, String passwd) {
		int result=0;
		try {
			getCon();
			String sql ="delete from board where num=? and passwd=?";
			pstmt =con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setString(2, passwd);
			
			result = pstmt.executeUpdate();
			con.close();			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return result;
	}

	//관리자가 공지사항을 적어주는 메소드
	public void insertAdminBoard(String subject, String content) {
		try {
			getCon();
			
			String sql ="insert into admintable values "
					+ "(admin_seq.NEXTVAL, ? , ? , sysdate)";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, subject);
			pstmt.setString(2, content);
		
			pstmt.executeUpdate();
			con.close();
		} catch (Exception e) {
			System.out.println("insertAdminBoard메소드 내부에서 오류:" + e.getMessage());
		}		
	}
	//공지사항 글 전부 가져오기
	public Vector<AdminBean> getAllAdminBoard() {
		Vector<AdminBean> v = new Vector<AdminBean>();
		AdminBean bean =null;
		try {
			getCon();
			String sql="select * from admintable";
			pstmt = con.prepareStatement(sql);
			
			rs=pstmt.executeQuery();
			while(rs.next()){
				bean = new AdminBean();
				bean.setNo(rs.getInt(1));
				bean.setSubject(rs.getString(2));
				bean.setContent(rs.getString(3));
				bean.setWriteday(rs.getDate(4).toString());
				v.add(bean);				
			}			
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return v;
	}


}











