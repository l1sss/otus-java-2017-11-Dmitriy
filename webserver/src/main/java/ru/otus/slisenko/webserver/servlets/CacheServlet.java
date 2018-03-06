package ru.otus.slisenko.webserver.servlets;

import ru.otus.slisenko.webserver.orm.cache.CacheEngine;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CacheServlet extends HttpServlet {
    private static final String CONTENT_TYPE_TEXT = "text/html;charset=utf-8";
    private static final String CACHE_PAGE_TEMPLATE = "cache.html";
    private static final String REFRESH_VARIABLE_NAME = "refreshPeriod";
    private static final int PERIOD_MS = 5000;
    private final CacheEngine cache;

    public CacheServlet(CacheEngine cache) {
        this.cache = cache;
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isLogged(req)) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        Map<String, Object> pageVariables = createPageVariablesMap();
        resp.setContentType(CONTENT_TYPE_TEXT);
        resp.getWriter().println(TemplateProcessor.instance().getPage(CACHE_PAGE_TEMPLATE, pageVariables));
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private Map<String, Object> createPageVariablesMap() {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put(REFRESH_VARIABLE_NAME, String.valueOf(PERIOD_MS));
        pageVariables.put("maxElements", cache.getMaxElements());
        pageVariables.put("lifeTimeMS", cache.getLifeTimeMs());
        pageVariables.put("idleTimeMS", cache.getIdleTimeMs());
        pageVariables.put("isEternal", String.valueOf(cache.isEternal()));
        pageVariables.put("hitCount", cache.getHitCount());
        pageVariables.put("missCount", cache.getMissCount());
        return pageVariables;
    }

    private boolean isLogged(HttpServletRequest req) {
        return req.getSession().getAttribute("login") != null;
    }
}
