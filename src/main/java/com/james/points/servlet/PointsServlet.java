package com.james.points.servlet;

import com.james.points.entity.*;
import com.james.points.services.PointsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.james.points.services.PointsServiceException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.*;

@WebServlet(name = "PointsServlet", urlPatterns = {"/ps"})
public class PointsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PointsService ps = ServletUtil.getPointsService(this.getServletConfig().getServletContext());
        Integer targetPoints = Integer.parseInt(request.getParameter("targetPoints"));
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(java.util.Date.class, new DateSerializer());
        mapper.registerModule(module);

        try {
            if (targetPoints == null) {
                targetPoints = 0;
            }
            List<Transaction> deductionList = ps.deductPoints(targetPoints);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().print(mapper.writeValueAsString(deductionList));
        } catch (PointsServiceException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PointsService ps = ServletUtil.getPointsService(this.getServletConfig().getServletContext());
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(java.util.Date.class, new DateDeserializer());
        mapper.registerModule(module);

        Transaction trans = mapper.readValue(request.getReader(), Transaction.class);
        if (trans == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            ps.addTransaction(trans);
            response.getWriter().print("New transaction is successfully added!");
        } catch (PointsServiceException e) {
            throw new ServletException(e);
        }
    }
}
