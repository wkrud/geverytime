package com.zea.geverytime.customer.model.dao;

import static com.zea.geverytime.common.JdbcTemplate.close;
import static com.zea.geverytime.common.JdbcTemplate.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.zea.geverytime.customer.model.exception.BoardException;
import com.zea.geverytime.customer.model.vo.QnaBoard;

public class QnaBoardDao {
	
	private Properties prop = new Properties();
	
	public 	QnaBoardDao() {
		File file = new File(QnaBoardDao.class.getResource("/qnaBoard-query.properties").getPath());
		try {
			prop.load(new FileReader(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	 
//	//게시판 목록 조회//
//	public List<QnaBoard> selectAllQnaBoard(Connection conn, Map<String, Integer> param) {
//		PreparedStatement pstmt = null;
//		String sql = prop.getProperty("selectAllQnaBoard");
//		ResultSet rset = null;
//		List<QnaBoard> list = new ArrayList<>();
//		
//	try {
//		pstmt = conn.prepareStatement(sql);
//		pstmt.setInt(1, param.get("start"));
//		pstmt.setInt(2, param.get("end"));
//		
//		rset = pstmt.executeQuery();
//		
//		while(rset.next()) {
//			QnaBoard qnaBoard = new QnaBoard();
//			
//			qnaBoard.setNo(rset.getInt("no"));
//			qnaBoard.setTitle(rset.getString("title"));
//			qnaBoard.setWriter(rset.getString("writer"));
//			qnaBoard.setPassword(rset.getString("password"));
//			qnaBoard.setContent(rset.getString("content"));
//			qnaBoard.setReplyLevel(rset.getInt("reply_level"));
//			qnaBoard.setReplyRef(rset.getInt("reply_ref"));
//			qnaBoard.setCategory(rset.getString("category_a"));
//			qnaBoard.setRegDate(rset.getDate("reg_date"));
//			list.add(qnaBoard);
//	}
//		}catch(SQLException e) {
//			e.printStackTrace();
//		}finally {
//			close(rset);
//			close(pstmt);
//		}
//		
//		 
//		
//		return list;
//	}

	//게시판 목록 조회//
		public List<QnaBoard> selectAllQnaBoard(Connection conn, Map<String, Integer> param ) {
			PreparedStatement pstmt = null;
			String sql = prop.getProperty("selectAllQnaBoard");
			ResultSet rset = null;
			List<QnaBoard> list = new ArrayList<>();
			
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, param.get("start"));
			pstmt.setInt(2, param.get("end"));
			 
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				QnaBoard qnaBoard = new QnaBoard();
				
				qnaBoard.setNo(rset.getInt("no"));
				qnaBoard.setTitle(rset.getString("title"));
				qnaBoard.setWriter(rset.getString("writer"));
				qnaBoard.setPassword(rset.getString("password"));
				qnaBoard.setContent(rset.getString("content"));
				qnaBoard.setReplyLevel(rset.getInt("reply_level"));
				qnaBoard.setReplyRef(rset.getInt("reply_ref"));
				qnaBoard.setCategory(rset.getString("category_a"));
				qnaBoard.setRegDate(rset.getDate("reg_date"));
				list.add(qnaBoard);
		}
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				close(rset);
				close(pstmt);
			}
			
			 
			
			return list;
		}

	//페이징하기 위해 총 게시물수 구하기
	public int selectTotalQnaBoardCount(Connection conn) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("selectTotalQnaBoardCount");
		ResultSet rset = null;
		int totalCount = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				totalCount = rset.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return totalCount;
	}

	//게시글 상세보기
	public  QnaBoard selectOneQnaBoard(Connection conn, int no) {
		QnaBoard qnaBoard = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectOneQnaBoard");
	try {
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, no);
		rset = pstmt.executeQuery();
		
		if(rset.next()){
			qnaBoard = new QnaBoard();
			qnaBoard.setNo(rset.getInt("no"));
			qnaBoard.setTitle(rset.getString("title"));
			qnaBoard.setWriter(rset.getString("writer"));
			qnaBoard.setPassword(rset.getString("password"));
			qnaBoard.setContent(rset.getString("content"));
		 
			qnaBoard.setReplyLevel(rset.getInt("reply_level"));
			qnaBoard.setReplyRef(rset.getInt("reply_ref"));
			qnaBoard.setCategory (rset.getString("category_a"));
		 
			qnaBoard.setRegDate(rset.getDate("reg_date"));
		}
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		close(rset);
		close(pstmt);
	}
	return qnaBoard;
 
	 
	}
	
//게시글 등록
	public int insertQnaBoard(Connection conn, QnaBoard qnaBoard) {
		 
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertQnaBoard");
		int result = 0;
		
		//insertQnaBoard = insert into qna_board(no,title,writer,content,password,category_a) values (seq_qna_board_no.nextval, ?,?,?,?,?)
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, qnaBoard.getTitle());
			pstmt.setString(2, qnaBoard.getWriter());
			pstmt.setString(3, qnaBoard.getContent());
			pstmt.setString(4, qnaBoard.getPassword());
			pstmt.setString(5, qnaBoard.getCategory());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new BoardException("게시물 등록 오류",e);
		} finally {
			close(pstmt);
		}
		
		return result;
 
 
	}

	
	//게시글 수정
	public int updateQnaBoard(Connection conn, QnaBoard qnaBoard) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = prop.getProperty("updateQnaBoard"); 
		//updateQnaBoard = update qna_board set title = ?, writer=?, content = ?,password=?,category=? where no = ?
		
		try {
			//미완성쿼리문을 가지고 객체생성.
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, qnaBoard.getTitle());
			pstmt.setString(2, qnaBoard.getWriter());
			pstmt.setString(3, qnaBoard.getContent());
			pstmt.setString(4, qnaBoard.getPassword());
			pstmt.setString(5, qnaBoard.getCategory());
			pstmt.setInt(6, qnaBoard.getNo());
			
			//쿼리문실행 : 완성된 쿼리를 가지고 있는 pstmt실행(파라미터 없음)
			//DML은 executeUpdate()
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	 
	}

	//게시글 삭제
	public int deleteBoard(Connection conn, int no) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = prop.getProperty("deleteQnaBoard"); 
		
		try {
			//미완성쿼리문을 가지고 객체생성.
			pstmt = conn.prepareStatement(query);
			//쿼리문미완성
			pstmt.setInt(1, no);
			
			//쿼리문실행 : 완성된 쿼리를 가지고 있는 pstmt실행(파라미터 없음)
			//DML은 executeUpdate()
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}
	 
}