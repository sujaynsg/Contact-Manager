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

public class LaunchTomcat {

	public static void main(String[] args) throws IOException, ServletException, LifecycleException  {

		
		  Tomcat tomcat = new Tomcat(); tomcat.setPort(8080); String currentDir =
		  new File(".").getCanonicalPath(); String webRoot = currentDir +
		  File.separatorChar + "webapps"; tomcat.addWebapp("", new
		  File("WebContent\\WEB-INF").getAbsolutePath());
		  System.out.println("configuring app with basedir: " + new
		  File("WebContent\\WEB-INF").getAbsolutePath()); tomcat.start();
		  tomcat.getServer().await();
		 
		/*Catalina cl = new Catalina();
		cl.start();
		while (true) {
			continue;
		}*/
	}

}
