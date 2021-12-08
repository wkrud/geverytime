package com.zea.geverytime.market.usedgoods.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.FileRenamePolicy;
import com.zea.geverytime.common.MvcFileRenamePolicy;
import com.zea.geverytime.common.MvcUtils;
import com.zea.geverytime.common.model.vo.Attachment;
import com.zea.geverytime.market.usedgoods.model.service.UsedGoodsService;
import com.zea.geverytime.market.usedgoods.model.vo.UsedGoodsBoard;

/**
 * Servlet implementation class UsedGoodBoardUpdateServlet
 */
@WebServlet("/ugGoods/boardUpdate")
public class UsedGoodBoardUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UsedGoodsService ugService = new UsedGoodsService();

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// 첨부파일 관리
			String saveDirectory = getServletContext().getRealPath("/upload/market/UgSale");
			int maxPostSize = 1024*1024*10;
			
			FileRenamePolicy policy = new MvcFileRenamePolicy();
			
			MultipartRequest multipartRequest = new MultipartRequest(request, saveDirectory, maxPostSize, "utf-8", policy);	
			
			// 요청 파라미터
			String title = multipartRequest.getParameter("title");
			int price = Integer.parseInt(multipartRequest.getParameter("price"));
			String content = multipartRequest.getParameter("summernote");
			int boardNo = Integer.parseInt(multipartRequest.getParameter("boardNo"));
			String writer = multipartRequest.getParameter("writer");
			
			// attachment DB 등록
			Enumeration fileNames = multipartRequest.getFileNames();
			List<Attachment> attachments = new ArrayList<>();
			while(fileNames.hasMoreElements()) {
				String fileName = (String) fileNames.nextElement();
				
				File upFile = multipartRequest.getFile(fileName);
				if(upFile != null) {
					Attachment attach = MvcUtils.makeAttachment(multipartRequest, fileName);
					attachments.add(attach);
				}
			}
			
			UsedGoodsBoard ugBoard = new UsedGoodsBoard(boardNo, title, content, null, null, writer, price);
			ugBoard.setAttachments(attachments);
			
			// 게시물 수정
			int result = ugService.updateUgBoard(ugBoard, attachments);
			
			String msg = "";
			if(result == 1) {
				msg = "게시글이 수정되었습니다.";
			} else {
				msg = "게시글이 수정되지 않았습니다.";
			}
			
			request.getSession().setAttribute("msg", msg);
			response.sendRedirect(request.getContextPath()+"/ugGoods/boardView?boardNo="+boardNo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}

}
