package com.prasad.location;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/")
public class Location {
	@GET
	@Path("/register/{cab}")
	public String registerCab(@PathParam("cab")String cab){
		Connection cn=null;
		PreparedStatement ps=null;
		boolean i;
		
		String query = "INSERT INTO user_location VALUES(?,'0',0,0,0)";
		try {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				return "Driver Class Not Loaded";
			}
			String url = "jdbc:mysql://127.12.90.2:3306/locationservice";
			cn = DriverManager.getConnection(url,"adminC3VsLxV","_XGEbqPApFDA");
			ps = cn.prepareStatement(query);
			ps.setString(1,cab);
			i= ps.execute();
			if(i){
				return "DUPLICATE";				
			}else{
				return "OK";
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
	
	@GET
	@Path("/check/{cabno}/{mobile_id}")
	public String checkCab(@PathParam("cabno")String cab,@PathParam("mobile_id")String id){
		Connection cn=null;
		PreparedStatement ps = null;
		String json = null;
		String query= "UPDATE user_location SET mobile_id = ? WHERE cab = ?";
		try{
			try{
				Class.forName("com.mysql.jdbc.Driver");
			}catch(ClassNotFoundException e){
				return "driver";
			}
			String url = "jdbc:mysql://127.12.90.2:3306/locationservice";
			cn = DriverManager.getConnection(url,"adminC3VsLxV","_XGEbqPApFDA");
			ps = cn.prepareStatement(query);
			ps.setString(1,id);
			ps.setString(2,cab);
			int count = ps.executeUpdate();
			System.out.println("count"+count);
			if(count!=0){
				return "OK";
			}else{
				return "FAILED";
			}
		}catch(SQLException e){
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
	@Path("/set/{cab}/{la}/{lo}/{status}")
	public void setCabLocation(@PathParam("cab")String cab,@PathParam("la")double lat,@PathParam("lo")double lng,@PathParam("status")String status){
		Connection cn=null;
		PreparedStatement ps=null;
		
		String query = "UPDATE user_location SET lat=?,lng=?,status=? WHERE cab=?";
		try {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.out.println("Driver Class Not Loaded");
			}
			String url = "jdbc:mysql://127.12.90.2:3306/locationservice";
			cn = DriverManager.getConnection(url,"adminC3VsLxV","_XGEbqPApFDA");
			ps = cn.prepareStatement(query);
			ps.setDouble(1,lat);
			ps.setDouble(2,lng);
			if(status.equals("true")){
				ps.setInt(3,1);
			}else{
				ps.setInt(3,0);
			}
			ps.setString(4,cab);
			int count= ps.executeUpdate();
			if(count==1){
				System.out.println("Saved location successfully " +status);
			}
			else{
				System.out.println("LOCATION NOT SAVED "+status);
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
	
	@POST
	@Path("/network")
	public String cabNetwork(){
		Connection cn=null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String json = null;
		String query = "SELECT lat,lng,status,cab FROM user_location";
		try{
			try{
				Class.forName("com.mysql.jdbc.Driver");
			}catch(ClassNotFoundException e){
				return "driver";
			}
			String url = "jdbc:mysql://127.12.90.2:3306/locationservice";
			cn = DriverManager.getConnection(url,"adminC3VsLxV","_XGEbqPApFDA");
			ps = cn.prepareStatement(query);
			rs = ps.executeQuery();
			boolean check = true;
			while(rs.next()){
			String obj = "{\"latlng\":{\"lat\":"+rs.getDouble(1)+",\"lng\":"+rs.getDouble(2)+"},\"status\":"+rs.getInt(3)+",\"title\":\""+rs.getString(4)+"\"}";
				if(check){
					json = obj;
					check = false;
				}else{
				json = json+","+obj;
				}
			}
			String jsonArray = "["+json+"]";
			return jsonArray;
		}catch(SQLException e){
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
	
	@POST
	@Path("/latlng/{cab}")
	public String getUserLocation(@PathParam("cab")String cab) {
		Connection cn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String query = "SELECT lat,lng,status FROM user_location WHERE cab = ?";
		try {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				return "Driver Class Not Loaded";
			}
			String url = "jdbc:mysql://127.12.90.2:3306/locationservice";
			cn = DriverManager.getConnection(url,"adminC3VsLxV","_XGEbqPApFDA");
			ps = cn.prepareStatement(query);
			ps.setString(1,cab);
			rs = ps.executeQuery();
			rs.next();
			double lat = rs.getDouble(1);
			double lng = rs.getDouble(2);
			return "{\"latitude\":"+lat+","+"\"longitude\":"+lng+",\"status\":"+rs.getInt(3)+"}";
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

}
