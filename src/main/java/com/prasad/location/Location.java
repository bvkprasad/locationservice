package com.prasad.location;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/")
public class Location {
	
	@GET
	@Path("/latlng/{phone}")
	public String getUserLocation(@PathParam("phone")double phone) {
		Connection cn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String query = "SELECT lat,lng FROM user_location WHERE phone = ?";
		try {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				return "Driver Class Not Loaded";
			}
			String url = "jdbc:mysql://127.12.90.2:3306/locationservice";
			cn = DriverManager.getConnection(url,"adminC3VsLxV","_XGEbqPApFDA");
			ps = cn.prepareStatement(query);
			ps.setLong(1, 9505412103l);
			rs = ps.executeQuery();
			rs.next();
			double lat = rs.getDouble(1);
			double lng = rs.getDouble(2);
			return "{\"latitude\":"+lat+","+"\"longitude\":"+lng+"}";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "failed transaction";
		}
		finally{
			if(cn != null){
				try {
					cn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(ps != null){
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	@GET
	@Path("/set/{ph}/{la}/{lo}")
	public String setUserLocation(@PathParam("ph")double phone,@PathParam("la")double lat,@PathParam("lo")double lng){
		Connection cn=null;
		PreparedStatement ps=null;
		
		String query = "UPDATE user_location SET lat=?,lng=? WHERE phone=?";
		try {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				return "Driver Class Not Loaded";
			}
			String url = "jdbc:mysql://127.12.90.2:3306/locationservice";
			cn = DriverManager.getConnection(url,"adminC3VsLxV","_XGEbqPApFDA");
			ps = cn.prepareStatement(query);
			ps.setDouble(1,lat);
			ps.setDouble(2,lng);
			ps.setDouble(3,phone);
			int i= ps.executeUpdate();
			if(i==1){
				System.out.println("SUCCESSFULLY UPDATED LOCATION WITH NEW COORDINATES");
				return "success";
			}
			return "2";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "failed transaction";
		}
		finally{
			if(cn != null){
				try {
					cn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(ps != null){
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	@GET
	@Path("/register/{ph}")
	public String registerUser(@PathParam("ph")double ph){
		Connection cn=null;
		PreparedStatement ps=null;
		boolean i;
		
		String query = "INSERT INTO user_location VALUES(?,0,0)";
		try {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				return "Driver Class Not Loaded";
			}
			String url = "jdbc:mysql://127.12.90.2:3306/locationservice";
			cn = DriverManager.getConnection(url,"adminC3VsLxV","_XGEbqPApFDA");
			ps = cn.prepareStatement(query);
			ps.setDouble(1,ph);
			i= ps.execute();
			if(i){
				System.out.println("NEW USER SUCCESSFULLY REGISTERED WITH DEFAULT LOCATION COORDINATES");
				return "success";
			}else{
				System.out.println("USER REGISTERED WITH ALREADY REGISTERED PHONE NO");
				return "This phone no. is already registered";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "failed transaction";
		}
		
		finally{
			if(cn != null){
				try {
					cn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(ps != null){
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
