package ru.otus.slisenko.webserver.servlets;

import ru.otus.slisenko.webserver.util.PropertiesHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class LoginServlet extends HttpServlet {
    private static final String CONTENT_TYPE_TEXT = "text/html;charset=utf-8";
    private static final String ADMIN_PAGE_TEMPLATE = "admin.html";
    private static final String ADMIN_PROPERTIES_NAME = "cfg/admin.properties";

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        resp.setContentType(CONTENT_TYPE_TEXT);
        resp.getWriter().println(TemplateProcessor.instance().getPage(ADMIN_PAGE_TEMPLATE, pageVariables));
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user = req.getParameter("user");
        String password = req.getParameter("password");
        Properties properties = PropertiesHelper.getProperties(ADMIN_PROPERTIES_NAME);
        resp.setContentType(CONTENT_TYPE_TEXT);
        if (isValidLogin(user, properties) && isValidPassword(password, properties)) {
            saveToSession(req, user);
            resp.setStatus(HttpServletResponse.SC_OK);
        } else
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }

    private boolean isValidLogin(String user, Properties properties) {
        return user.equals(properties.getProperty("user"));
    }

    private boolean isValidPassword(String password, Properties properties) {
        return password.equals(properties.getProperty("password"));
    }

    private void saveToSession(HttpServletRequest req, String user) {
        req.getSession().setAttribute("login", user);
    }
}
