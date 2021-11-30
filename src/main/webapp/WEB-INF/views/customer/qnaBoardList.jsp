<%@page import = "com.zea.geverytime.customer.model.vo.QnaBoard" %>
<%@page import="java.util.List"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="/WEB-INF/views/common/header.jsp" %>
  <link rel="stylesheet" href="<%=request.getContextPath()%>/css/customer/qnaList.css" /> 
 <section id="qnaBoard-container">
 <div id="total">
        <h3>Q&A</h3>
 <div id="leftbox">
            <span>Q&A</span>
            <br>
            <span>FAQ</span>
            <br>
            <span>신고내역</span>
        </div>
        <div class="board_list_wrap">
            <table class="board_list">
                <caption>게시판 목록</caption>
                <thead>
                    <tr>
                        <th>No</th>
                         <th>분류</th>
                        <th></th>
                        <th>제목</th>
                        <th>작성자</th>
                        <th>날짜</th>
                    </tr>
                </thead>
                <tbody>
 <%
	List<QnaBoard> list = (List<QnaBoard>) request.getAttribute("list");
	for(QnaBoard qnaBoard : list){
		if(qnaBoard.getReplyLevel() == 1){
%>
 
                    <tr>
                        <td><%= qnaBoard.getNo() %></td>
                        <td><%= qnaBoard.getCategory() %></td>
                         <td></td>
                        <td class="tit">
                          <a href="<%= request.getContextPath() %>/customer/qnaBoardView?no=<%= qnaBoard.getNo() %>"><%= qnaBoard.getTitle() %></a>
                        </td>
                        <td><%= qnaBoard.getWriter() %></td>
                        <td><%=qnaBoard.getRegDate() %></td>
                    </tr>
                     
                </tbody>
  
<%
	}else{
		 
%>

  <tr>
  
                        <td><%= qnaBoard.getNo() %></td>
                        <td><%= qnaBoard.getCategory() %></td>
                         <td></td>
                        <td class="tit">
                          <a href="<%= request.getContextPath() %>/customer/qnaBoardView?no=<%= qnaBoard.getNo() %>"><p></p>ㄴ>[답변] <%= qnaBoard.getTitle() %></a>
                          
                        </td>
                        <td><%= qnaBoard.getWriter() %></td>
                        <td><%=qnaBoard.getRegDate() %></td>
                    </tr>	

<%
	}
		 
%>
<%
	}
		 
%>
 
            </table>
          
        </div>
        <input type="button" value="글쓰기" id="btn-add" 
        	onclick="location.href='<%= request.getContextPath() %>/customer/qnaBoardForm'"/>
	<div id='pageBar'><%= request.getAttribute("pagebar") %></div>
 </div> 
</section>
 <%@ include file="/WEB-INF/views/common/footer.jsp" %>