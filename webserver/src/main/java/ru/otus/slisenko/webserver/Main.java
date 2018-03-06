package ru.otus.slisenko.webserver;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import ru.otus.slisenko.webserver.orm.dbservices.DBService;
import ru.otus.slisenko.webserver.orm.dbservices.DBServiceImp;
import ru.otus.slisenko.webserver.servlets.LoginServlet;
import ru.otus.slisenko.webserver.servlets.CacheServlet;
import ru.otus.slisenko.webserver.util.PropertiesHelper;

import java.util.Properties;

public class Main {
    private static final String APPLICATION_PROPERTIES_NAME = "cfg/server.properties";
    private static final String PUBLIC_HTML = "public_html";
    private final DBService dbService = new DBServiceImp();

    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.startDb();
        main.startServer();
    }

    private void startServer() throws Exception {
            ResourceHandler resourceHandler = new ResourceHandler();
            Resource resource = Resource.newClassPathResource(PUBLIC_HTML);
            resourceHandler.setBaseResource(resource);

            ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
            context.addServlet(new ServletHolder(new LoginServlet()), "/admin");
            context.addServlet(new ServletHolder(new CacheServlet(dbService.getCache())), "/cache");

            Properties properties = PropertiesHelper.getProperties(APPLICATION_PROPERTIES_NAME);
            int port = Integer.valueOf(properties.getProperty("server.port"));
            Server server = new Server(port);
            server.setHandler(new HandlerList(resourceHandler, context));

            server.start();
            server.join();
    }

    private void startDb() throws Exception {
            dbService.createTable();
            Thread dbRequester = new Thread(new DBRequester(dbService));
            dbRequester.setName("dbRequester");
            dbRequester.start();
    }
}
