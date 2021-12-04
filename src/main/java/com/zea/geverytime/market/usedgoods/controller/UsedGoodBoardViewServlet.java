package com.zea.geverytime.market.usedgoods.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zea.geverytime.common.model.vo.Attachment;
import com.zea.geverytime.market.usedgoods.model.service.UsedGoodsService;
import com.zea.geverytime.market.usedgoods.model.vo.UsedGoodsBoard;

/**
 * Servlet implementation class UsedGoodBoardViewServlet
 */
@WebServlet("/ugGoods/boardView")
public class UsedGoodBoardViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UsedGoodsService ugService = new UsedGoodsService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		System.out.println("뷰 boardNo : "+boardNo);
		
		UsedGoodsBoard board = ugService.getUgGoodsBoard(boardNo);
		
		String orCode = board.getOrCode();
		
		List<Attachment> attachments = ugService.getUgBoardAttachment(orCode);
		
		board.setAttachments(attachments);
		
		request.setAttribute("board", board);
		request.getRequestDispatcher("/WEB-INF/views/market/ugGoods/ugGoodsBoardView.jsp").forward(request, response);
	}
}
