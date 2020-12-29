package de.froesche.nz.server;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.glassfish.jersey.servlet.ServletContainer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.IOException;

public class FroescheServer {
   private static Logger logger = Logger.getLogger(FroescheServer.class);
    //Logger logger = LoggerFactory.getLogger(de.froesche.nz.server.FroescheServer.class);

    private static final String CONFIG_LOCATION_PACKAGE = "de.froesche.nz.rest";
    private static final String CONTEXT_PATH = "/";
    private static final String MAPPING_URL = "/";
    private static final String WEBAPP_DIRECTORY = "webapp";

    public static void main(String[] args) throws Exception {
        BasicConfigurator.configure();
        Server froescheServer = new Server();
        ServerConnector connector = new ServerConnector(froescheServer);
        connector.setPort(18080);
        froescheServer.setConnectors(new Connector[] {connector});


/*
        WebAppContext webAppContext = new WebAppContext();
      //  ServletContextHandler contextHandler = new ServletContextHandler();
        webAppContext.setContextPath("/rest");
        webAppContext.setResourceBase("server/src/main/resources/webapp/");
        webAppContext.setDescriptor("server/src/main/resources/webapp/WEB-INF/web.xml");
        webAppContext.setServer(froescheServer);

        WebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();



        DispatcherServlet dispatcherServlet = new DispatcherServlet(applicationContext);
        ServletHolder servletHolder = new ServletHolder(dispatcherServlet);
        ServletContainer servletContainer = new ServletContainer(servletHolder);
        webAppContext.
        froescheServer.setHandler(applicationContext);

        froescheServer.setHandler(webAppContext);
 */
        WebAppContext webAppContext = new WebAppContext();
        webAppContext.setContextPath("/rest");
        webAppContext.setBaseResource(Resource.newClassPathResource("/webapp"));
        webAppContext.setDescriptor("server/src/main/resources/webapp/WEB-INF/web.xml");
        ServletContextHandler contextHandler = new ServletContextHandler();
        //DefaultServlet defaultServlet = new DefaultServlet();
        //ServletHolder holderPwd = new ServletHolder("default", defaultServlet);
        //webAppContext.addServlet(holderPwd,"/webapp");

        //ServletContainer servletContainer = new ServletContainer();

       // ApplicationContext ctx =
        //        new ClassPathXmlApplicationContext("/webapp/WEB-INF/spring/applicationContext.xml");
       // XmlWebApplicationContext xmlWebApplicationContext = new XmlWebApplicationContext();
       // xmlWebApplicationContext.setServletContext(webAppContext.getServletContext());
      //  xmlWebApplicationContext.setParent(ctx);
        //xmlWebApplicationContext.setServletContext(servletContainer);
        //xmlWebApplicationContext.setServletContext(contextHandler.getServletContext());
        //xmlWebApplicationContext.refresh();
        //webAppContext.getServletContext().setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, xmlWebApplicationContext);
/*DispatcherServlet dispatcherServlet = new DispatcherServlet();
dispatcherServlet.setApplicationContext(new ClassPathXmlApplicationContext("/webapp/WEB-INF/spring/applicationContext.xml"));
      ServletHolder sd = new ServletHolder();
      sd.setServlet(dispatcherServlet);
        ServletContextHandler contextHandler1 = new ServletContextHandler();
        webAppContext.addServlet(sd,"/rest2");*/
        //froescheServer.setHandler(getServletContextHandler());
        froescheServer.setHandler(webAppContext);

        //addRuntimeShutdownHook(froescheServer);


        froescheServer.start();
        froescheServer.join();
    }


    private static ServletContextHandler getServletContextHandler() throws IOException {
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS); // SESSIONS requerido para JSP
        contextHandler.setErrorHandler(null);

        contextHandler.setResourceBase(new ClassPathResource(WEBAPP_DIRECTORY).getURI().toString());
        contextHandler.setContextPath(CONTEXT_PATH);

        // Spring
        WebApplicationContext webAppContext = getWebApplicationContext();
        DispatcherServlet dispatcherServlet = new DispatcherServlet(webAppContext);
        ServletHolder springServletHolder = new ServletHolder("mvc-dispatcher", dispatcherServlet);
        contextHandler.addServlet(springServletHolder, MAPPING_URL);
        contextHandler.addEventListener(new ContextLoaderListener(webAppContext));

        return contextHandler;
    }

    private static WebApplicationContext getWebApplicationContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation(CONFIG_LOCATION_PACKAGE);
        return context;
    }



    private static void addRuntimeShutdownHook(final Server server) {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                if (server.isStarted()) {
                    server.setStopAtShutdown(true);
                    try {
                        server.stop();
                    } catch (Exception e) {
                        System.out.println("Error while stopping jetty server: " + e.getMessage());
                        logger.error("Error while stopping jetty server: " + e.getMessage(), e);
                    }
                }
            }
        }));
    }


}


