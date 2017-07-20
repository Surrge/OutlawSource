package net.outlawsource;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.apache.log4j.Logger;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class SpringAppInitalizer implements WebApplicationInitializer {
	
	public static Logger log = Logger.getRootLogger();

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		log.warn("  +++  OutlawSource Application Initializer Found +++  ");
		
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(SpringConfig.class /*, SpringSecurityConfig.class*/);
		rootContext.setServletContext(servletContext);

		ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher",
				new DispatcherServlet(rootContext));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");

		//servletContext.addFilter("messageFilter", MessageFilter.class).addMappingForUrlPatterns(null, false, "/*");
	}
}
