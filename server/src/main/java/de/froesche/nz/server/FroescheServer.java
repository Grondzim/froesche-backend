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

        WebAppContext webAppContext = new WebAppContext();
        webAppContext.setContextPath("/rest");
        webAppContext.setBaseResource(Resource.newClassPathResource("/webapp"));
        webAppContext.setDescriptor("server/src/main/resources/webapp/WEB-INF/web.xml");
        froescheServer.setHandler(webAppContext);

        addRuntimeShutdownHook(froescheServer);

        froescheServer.start();
        froescheServer.join();
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


