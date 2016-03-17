package com.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;

import com.models.DBConnection;
import com.models.UserModel;

@Path("/")
public class Services {

	/*
	 * @GET
	 * 
	 * @Path("/signup")
	 * 
	 * @Produces(MediaType.TEXT_HTML) public Response signUp(){ return
	 * Response.ok(new Viewable("/Signup.jsp")).build(); }
	 */

	@POST
	@Path("/signup")
	@Produces(MediaType.TEXT_PLAIN)
	public String signUp(@FormParam("name") String name,
			@FormParam("email") String email, @FormParam("pass") String pass) {
		UserModel user = UserModel.addNewUser(name, email, pass);
		JSONObject json = new JSONObject();
		json.put("id", user.getId());
		json.put("name", user.getName());
		json.put("email", user.getEmail());
		json.put("pass", user.getPass());
		json.put("lat", user.getLat());
		json.put("long", user.getLon());
		return json.toJSONString();
	}

	@POST
	@Path("/login")
	@Produces(MediaType.TEXT_PLAIN)
	public String login(@FormParam("email") String email,
			@FormParam("pass") String pass) {
		UserModel user = UserModel.login(email, pass);
		JSONObject json = new JSONObject();
		json.put("id", user.getId());
		json.put("name", user.getName());
		json.put("email", user.getEmail());
		json.put("pass", user.getPass());
		json.put("lat", user.getLat());
		json.put("long", user.getLon());
		return json.toJSONString();
	}
	
	@POST
	@Path("/updatePosition")
	@Produces(MediaType.TEXT_PLAIN)
	public String updatePosition(@FormParam("id") String id,
			@FormParam("lat") String lat, @FormParam("long") String lon) {
		Boolean status = UserModel.updateUserPosition(Integer.parseInt(id), Double.parseDouble(lat), Double.parseDouble(lon));
		JSONObject json = new JSONObject();
		json.put("status", status ? 1 : 0);
		return json.toJSONString();
	}
	@POST
	@Path("/userCurrentLoscation")
	@Produces(MediaType.TEXT_PLAIN)
	public String currentLocation(@FormParam("id") String id)
	{
		Double[] longLat = UserModel.returnUserLocation(Integer.parseInt(id));
		JSONObject json = new JSONObject();
		json.put("long",longLat[0]);
		json.put("lat",longLat[1]);
		return json.toJSONString();
	}

	@POST
	@Path("/followUser")
	@Produces(MediaType.TEXT_PLAIN)
	public String followUser(@FormParam("userId") String userId,
			@FormParam("followedId") String followedId) 
	{
		Boolean isfollowed = UserModel.followUser(Integer.parseInt(userId), Integer.parseInt(followedId));
		JSONObject json = new JSONObject();
		json.put("isfollowed", isfollowed);
		return json.toJSONString();
		//return isfollowed;
	}
	@POST
	@Path("/unfollow")
	@Produces(MediaType.TEXT_PLAIN)
	public String unfollow(@FormParam("userId") String userId,
			@FormParam("followedId") String followedId) {
		boolean retn = UserModel.unfollow(Integer.parseInt(userId), Integer.parseInt(followedId));
		JSONObject json = new JSONObject();
		json.put("unfollowed", retn);
		return json.toJSONString();
	}
	
	@POST
	@Path("/getfollowers")
	@Produces(MediaType.TEXT_PLAIN)
	public String getfolloweds(@FormParam("id") String id) {
		ArrayList<UserModel> status = UserModel.Get_Followers(Integer.parseInt(id));
		JSONObject json = new JSONObject();
		for (int i = 0; i < status.size(); i++) {
			json.put("followers", status.get(i).toString());
		}
		
//		System.out.println(status.toString());
		return json.toJSONString();
	}

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_PLAIN)
	public String getJson() {
		return "Hello after editing";
		// Connection URL:
		// mysql://$OPENSHIFT_MYSQL_DB_HOST:$OPENSHIFT_MYSQL_DB_PORT/
	}
}
