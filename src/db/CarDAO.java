package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;




public class CarDAO {
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
	

	public Vector<CarListBean> getAllCarlist() {
		
		Vector<CarListBean> v = new Vector<CarListBean>();
		
		CarListBean bean = null;

		try {
			getCon();
			String sql = "select * from carlist";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new CarListBean();
				bean.setCarno(rs.getInt(1));
				bean.setCarname(rs.getString(2));
				bean.setCarcompany(rs.getString(3));
				bean.setCarprice(rs.getInt(4));
				bean.setCarusepeople(rs.getInt(5));
				bean.setCarinfo(rs.getString(6));
				bean.setCarimg(rs.getString(7));
				bean.setCarcategory(rs.getString(8));
				
				v.add(bean);
			}
		
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return v; 
	}// getAllCarlist();

	
	public Vector<CarListBean> getCategoryCarList(String carcategory) {
	
		Vector<CarListBean> v = new Vector<CarListBean>();
	
		CarListBean bean = null;

		try {
		
			getCon();// DB연결
		
			String sql = "select * from carlist where carcategory=?";
		
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, carcategory);
		
			rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new CarListBean();
				bean.setCarno(rs.getInt(1)); 
				bean.setCarname(rs.getString(2)); 
				bean.setCarcompany(rs.getString(3));
				bean.setCarprice(rs.getInt(4));
				bean.setCarusepeople(rs.getInt(5));
				bean.setCarinfo(rs.getString(6));
				bean.setCarimg(rs.getString(7));
				bean.setCarcategory(rs.getString(8));
				
				v.add(bean);
			}
		
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return v; 
	}// getCategoryCarList();

	
	public CarListBean getOneCar(int carno) {
	
		CarListBean bean = null;
		try {
			
			getCon();
			
			String sql = "select * from carlist where carno=?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, carno);
			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new CarListBean();
				bean.setCarno(rs.getInt(1));
				bean.setCarname(rs.getString(2));
				bean.setCarcompany(rs.getString(3));
				bean.setCarprice(rs.getInt(4));
				bean.setCarusepeople(rs.getInt(5));
				bean.setCarinfo(rs.getString(6));
				bean.setCarimg(rs.getString(7));
				bean.setCarcategory(rs.getString(8));
			}
			
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean; 
	}


	public void insertCarOrder(CarOrderBean cbean) {
		try {
			
			getCon();
			
			String sql = "insert into carorder(carno,carqty,carreserveday,"
			+ "carbegindate,carins,carwifi,carnave,carbabyseat,memberphone,memberpass) "
			+ "values(?,?,?,?,?,?,?,?,?,?)";
			
			pstmt = con.prepareStatement(sql);
		
			pstmt.setInt(1, cbean.getCarno());
			pstmt.setInt(2, cbean.getCarqty());
			pstmt.setInt(3, cbean.getCarreserveday());
			pstmt.setString(4, cbean.getCarbegindate());
			pstmt.setInt(5, cbean.getCarins());
			pstmt.setInt(6, cbean.getCarwifi());
			pstmt.setInt(7, cbean.getCarnave());
			pstmt.setInt(8, cbean.getCarbabyseat());
			pstmt.setString(9, cbean.getMemberphone());
			pstmt.setString(10, cbean.getMemberpass());
	
			pstmt.executeUpdate();
			
			
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//insertCarOrder();


	public Vector<CarConfirmBean> getAllCarOrder(String memberphone, String memberpass) {
		
		Vector<CarConfirmBean> v = new Vector<CarConfirmBean>();

		CarConfirmBean bean = null;
		try {
		
			getCon();
		
			
		
			String sql = "select * from carorder natural join carlist where "
					+ "now() < str_to_date(carbegindate , '%Y-%m-%d') and "
					+ "memberphone=? and memberpass=?";
			
			

	
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, memberphone);
			pstmt.setString(2, memberpass);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				bean = new CarConfirmBean(); 
				bean.setOrderid(rs.getInt(2));
				bean.setCarqty(rs.getInt(3));
				bean.setCarreserveday(rs.getInt(4));
				bean.setCarbegindate(rs.getString(5));
				bean.setCarins(rs.getInt(6));
				bean.setCarwifi(rs.getInt(7));
				bean.setCarnave(rs.getInt(8));
				bean.setCarbabyseat(rs.getInt(9));
				bean.setCarname(rs.getString(12));
				bean.setCarprice(rs.getInt(14));
				bean.setCarimg(rs.getString(17));
				v.add(bean);
			}
			
			con.close();		
			
		} catch (Exception e) {
			System.out.println("getAllCarOrder메소드에서 오류 : " + e);
		}
		
		
		
		return v;
	}
	
	
	public CarConfirmBean getOneOrder(int orderid) {
	
		CarConfirmBean cbean =null;
		try {
			getCon();
	
			String sql ="select * from carorder where orderid=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, orderid);
	
			rs=pstmt.executeQuery();
			if(rs.next()){
				cbean = new CarConfirmBean();
				cbean.setOrderid(orderid);
				cbean.setCarbegindate(rs.getString(5));
				cbean.setCarreserveday(rs.getInt(4));
				cbean.setCarins(rs.getInt(6));
				cbean.setCarwifi(rs.getInt(7));
				cbean.setCarnave(rs.getInt(8));
				cbean.setCarbabyseat(rs.getInt(9)); 
			}
			con.close();			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return cbean;
	}//getOneOrder();


		
	public void carOrderUpdate(CarOrderBean bean) {
		try {
			getCon();
		String sql ="update carorder set carbegindate=? , carreserveday=? , carqty=?"
					+ ", carins=? , carwifi=? , carbabyseat=? where orderid=? "
					+ "and memberpass=?";
		
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, bean.getCarbegindate());
			pstmt.setInt(2, bean.getCarreserveday());
			pstmt.setInt(3, bean.getCarqty());
			pstmt.setInt(4, bean.getCarins());
			pstmt.setInt(5, bean.getCarwifi());
			pstmt.setInt(6, bean.getCarbabyseat());
			pstmt.setInt(7, bean.getOrderid());
			pstmt.setString(8, bean.getMemberpass());
			pstmt.executeUpdate();
			con.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	public int carOrderDelete(int orderid, String memberpass) {
		int result=0;
		try {
			getCon();
			String sql ="delete from carorder where orderid=? and memberpass=?";
			pstmt = con.prepareStatement(sql);
		
			pstmt.setInt(1, orderid);
			pstmt.setString(2, memberpass);
			
			result = pstmt.executeUpdate();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		return result;
	}

}