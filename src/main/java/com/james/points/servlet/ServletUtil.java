package com.james.points.servlet;

import com.james.points.services.PointsService;

import javax.servlet.ServletContext;

public class ServletUtil {
    public static PointsService getPointsService(ServletContext servletContext) {
        PointsService ps = (PointsService)servletContext.getAttribute("pointService");
        if (ps == null) {
            ps = new PointsService();
            servletContext.setAttribute("pointService", ps);
        }

        return ps;
    }
}
