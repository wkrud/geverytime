package com.zea.geverytime.myPage.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/myPage/myPagePoint")
public class MypagePointServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request
            .getRequestDispatcher("/WEB-INF/views/myPage/myPagePoint.jsp")
            .forward(request, response);
    }

    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }

}