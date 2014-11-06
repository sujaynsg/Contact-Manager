package com.contactmanager.tomcat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.core.StandardServer;
import org.apache.catalina.startup.Catalina;
import org.apache.catalina.startup.Tomcat;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LaunchTomcat {
	private static String createDBQuery;
	  private static String charSet = "utf8";
	  private static String newDB;
	  private static Statement stmt = null;
	  private static String url = "jdbc:mysql://localhost/contactmanager";
	 // private static String driver="com.mysql.jdbc.Driver";
	  private static String driver="org.gjt.mm.mysql.Driver";
	  private static String usrName = "root";
	  private static String pswd = "";
	  private static Connection connection = null;
	  private static ResultSet rs = null;

	public static void main(String[] args) throws IOException, LifecycleException, ServletException, ClassNotFoundException, SQLException  {
		
		  System.out.println("Hello Sujay");
		  Tomcat tomcat = new Tomcat(); tomcat.setPort(8081); 
		  tomcat.addWebapp("", new File("build").getAbsolutePath());
		  System.out.println("configuring app with basedir: " + new File("build").getAbsolutePath());
		  tomcat.start();
		  tomcat.getServer().await();
		  
		}

}
