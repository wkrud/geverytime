package com.zea.geverytime.info.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zea.geverytime.info.model.service.InfoService;
import com.zea.geverytime.info.model.vo.Info;

/**
 * Servlet implementation class InfoAllListServlet
 */
@WebServlet("/info/allList")
public class InfoAllListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private InfoService infoService = new InfoService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		
	}

}
