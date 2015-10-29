package com.prasad.location;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.Connection;
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
	public String getHelloWorldJSON(@PathParam("phone")double phone) {
		Connection cn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String query = "SELECT lat,lng FROM user_location WHERE phone = ?";
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setUser("adminC3VsLxV");
		dataSource.setPassword("_XGEbqPApFDA");
		dataSource.setServerName("mysql://$OPENSHIFT_MYSQL_DB_HOST:$OPENSHIFT_MYSQL_DB_PORT/");
		try {
			cn = dataSource.getConnection();
			ps = cn.prepareStatement(query);
			ps.setLong(1, 9505412103l);
			rs = ps.executeQuery();
			rs.next();
			double lat = rs.getDouble(1);
			double lng = rs.getDouble(2);
			return "latitude is "+lat+"and longitude is "+lng;
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
