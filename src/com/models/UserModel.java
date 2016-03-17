package com.models;

//import java.sql.Connection;
//import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
//import java.sql.SQLException;
import java.util.ArrayList;
//import com.mysql.jdbc.Statement;


import com.mysql.jdbc.PreparedStatement;

public class UserModel {

	
	private String name;
	private String email;
	private String pass;
	private Integer id;
	private Double lat;
	private Double lon;
	
	@Override
	public String toString() {
		return "UserModel [name=" + name + ", email=" + email + ", pass="
				+ pass + ", id=" + id + ", lat=" + lat + ", lon=" + lon + "]";
	}

	public String getPass(){
		return pass;
	}
	
	public void setPass(String pass){
		this.pass = pass;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}

	public static UserModel addNewUser(String name, String email, String pass) {
		UserModel user=null;
		try{
		DBConnection.getActiveConnection();
		DBConnection.ExecuteUpdate("Insert into users (name,`email`,`password`) VALUES  ('"+name+"','"+email+"','"+pass+"')");
		ResultSet rs = DBConnection.ExecuteQuery("select id from users where email='"+email+"'");
		if (rs.next()) {
		    user = new UserModel();
			user.id = rs.getInt("id");
			user.email = email;
			user.pass = pass;
			user.name = name;
			user.lat = 0.0;
			user.lon = 0.0;
		}
		}catch(Exception e)
		{
			System.out.println("Error");
		}finally
		{
			DBConnection.Close_Connection();
		}
		return user;
	}

	public static boolean Verfiy_Email(String email)
	{
		
		boolean t=false;
      try{	
    	  DBConnection.getActiveConnection();
		ResultSet rs = DBConnection.ExecuteQuery("select id from users where email='"+email+"'");
		
			if (rs.next()) 
				t=true;
			}catch(Exception e)
			{
				
			}finally
			{
				DBConnection.Close_Connection();
			}
      if (t)
		return true;
      else
    	  return false;
	}
	
	public static UserModel login(String email, String pass) {
		UserModel user=null;
		try{
			DBConnection.getActiveConnection();
			ResultSet res=DBConnection.ExecuteQuery("select * from users where email ='"+email+"' and password ='"+pass+"'");
			if (res.next())
			{
				user=new UserModel();
				user.id = res.getInt("id");
				user.email = res.getString("email");
				user.pass = res.getString("password");
				user.name = res.getString("name");
				user.lat = res.getDouble("lat");
				user.lon = res.getDouble("long");
				return user;	
			}
			
		}catch(Exception e)
		{
			System.out.println("error");
		}finally
		{
			DBConnection.Close_Connection();
		}
		return null;
	}

	public static boolean updateUserPosition(Integer id, Double lat, Double lon) {
		try{
			DBConnection.getActiveConnection();
			DBConnection.ExecuteUpdate("Update users set users.lat ="+lat+" ,users.long ="+lon+" where id="+id);
			return true;
		}catch(Exception e)
		{
			System.out.println("error");
		}finally
		{
			DBConnection.Close_Connection();
		}
		return false;
	}
	public static void Accept_Follow(int user_id,int follower_id)
	{
		try{
			DBConnection.getActiveConnection();
			DBConnection.ExecuteUpdate("insert into followers values("+user_id+", "+follower_id+")");
			
		}catch(Exception e)
		{
			System.out.println("error");
		}finally
		{
			DBConnection.Close_Connection();
		}
	}
	public static void Un_Follow(int user_id,int follower_id)
	{
		try{
			DBConnection.getActiveConnection();
			DBConnection.ExecuteUpdate("delete from followers where user_id ="+user_id+" and follower_id ="+follower_id);
			
		}catch(Exception e)
		{
			System.out.println("erroe");
		}finally
		{
			DBConnection.Close_Connection();
		}
	}

	public static Double[] returnUserLocation(int userID)
	{
		Double longLat[]=new Double[2];
		try{
			DBConnection.getActiveConnection();
			ResultSet res=DBConnection.ExecuteQuery("select lat ,users.long from users where id ="+userID);
			if(res.next())
			{ 
				longLat[0]=Double.valueOf(res.getString("long"));
                longLat[1]=Double.valueOf(res.getString("lat"));
	
			}
			
		}catch(Exception e)
		{
			System.out.println("error");
		}finally
		{
			DBConnection.Close_Connection();
		}
		return longLat;
	}
	public static boolean followUser(int userId,int followerId)
	{boolean done =true;
		try{
			DBConnection.getActiveConnection();
			DBConnection.ExecuteUpdate("insert into followers values("+userId+", "+followerId+")");
			
		}catch(Exception e)
		{
			done = false;
		}finally
		{
			DBConnection.Close_Connection();
		}
		return done;
	}

	
	public static ArrayList<UserModel> Get_Followers(int id_)
	{
		ArrayList<UserModel> Followers=new ArrayList<UserModel>();
		 UserModel user=new UserModel(); 
		try{
			DBConnection.getActiveConnection();
			ResultSet res=DBConnection.ExecuteQuery("select * FROM users u inner JOIN followers f ON (u.id = f.follower_id) "+
                                                                            "where f.user_id="+id_);
			while(res.next())
			{
				user.setEmail(res.getString("email"));
				user.setName(res.getString("name"));
				user.setLat(res.getDouble("lat"));
				user.setLon(res.getDouble("long"));
				user.setId(res.getInt("id"));
				Followers.add(user);
			}
		}catch(Exception e)
		{
			System.out.println("error");
		}finally
		{
			DBConnection.Close_Connection();
		}
		return Followers;
	}
	public static boolean unfollow(int user_id,int follower_id)
	{
		boolean done = true;
		try{
			DBConnection.getActiveConnection();
			DBConnection.ExecuteUpdate("delete from followers where user_id ="+user_id+" and follower_id ="+follower_id);
			
		}catch(Exception e)
		{
			System.out.println("erroe");
			done = false;
		}finally
		{
			DBConnection.Close_Connection();
		}
		return done;
	}


	public UserModel Get_User(int user_id)
	{
		UserModel user=new UserModel();
		try{
			ResultSet res=DBConnection.ExecuteQuery("select * from users where id ="+user_id);
			if(res.next())
			{
				user.setEmail(res.getString("email"));
				user.setName(res.getString("name"));
				user.setPass(res.getString("password"));
				user.setLat(res.getDouble("lat"));
				user.setLon(res.getDouble("long"));
				user.setId(res.getInt("id"));
			}
		}catch(Exception e)
		{
			System.out.println("error");
		}finally
		{
			DBConnection.Close_Connection();
		}
		return user;
	}
}
