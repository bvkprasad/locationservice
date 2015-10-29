package com.prasad.addnumbers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/")
public class Location {
	@GET
	@Path("/latlng/{lat}/{lng}")
	public String getHelloWorldJSON(@PathParam("lat")double lat,@PathParam("lng")double lng) {
		return "your location is "+lat+" "+lng;
	}
}
