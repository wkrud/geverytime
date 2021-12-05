<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String id = (String) request.getAttribute("memberId");
%>
<%@ page import="java.sql.*" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<table id="myPageBusinessInfo">
	<thead>
		<tr>
			<th>코드</th>
			<th>게시자</th>
			<th>상호명</th>
			<th>제목</th>
			<th>게시일</th>
			<th>승인상태</th>
		</tr>
	</thead>
	<tbody></tbody>
</table>

<script>
$(() => {
	checkYN();
});
const checkYN = () => {
	$.ajax({
		url: "<%= request.getContextPath() %>/myPage/business",
		method: "post",
		dataType: "json",
		data: {'id':'<%= id %>'},
		success(data){
			console.log(data.businessName);
			const $tbody = $("#myPageBusinessInfo tbody");					
				
			// 날짜포멧
			let rd = new Date(data.regDate);
			let value = `\${rd.getFullYear()}.\${(rd.getMonth() + 1)}.\${(rd.getDate())}`;
			
			// 상태
			var state = "상태";
			
			if(data.deleteCheck === "A")
				state = "삭제";
			if(data.regCheck === "O" && data.deleteCheck === "D")
				state = "보류";
			if(data.regCheck === "I" && data.deleteCheck === "D")
				state = "게시"									
			
			const tr = `<tr>
			<td>\${data.code}</td>
			<td>\${data.memberId}</td>
			<td><\${data.businessName}</td>
			<td>\${data.headContent}</td>
			<td>\${value}</td>
			<td>\${state}</td>
			</tr>`;
			
			$tbody.append(tr);
			
			
		},
		error: console.log
	});
};

</script>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>