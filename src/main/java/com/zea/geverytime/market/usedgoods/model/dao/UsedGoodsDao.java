package com.zea.geverytime.market.usedgoods.model.dao;

import static com.zea.geverytime.common.JdbcTemplate.close;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.zea.geverytime.common.model.vo.Attachment;
import com.zea.geverytime.market.usedgoods.model.vo.UsedGoodsBoard;

public class UsedGoodsDao {
	
	Properties prop = new Properties();
	
	public UsedGoodsDao() {
		String filepath = UsedGoodsDao.class.getResource("/usedGoods-query.properties").getPath();
		try {
			prop.load(new FileReader(filepath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int insertUgBoard(Connection conn, UsedGoodsBoard ugBoard) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertUgBoard");
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ugBoard.getTitle());
			pstmt.setString(2, ugBoard.getContent());
			pstmt.setString(3, ugBoard.getWriter());
			pstmt.setInt(4, ugBoard.getPrice());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}

	public int insertBoardAttachments(Connection conn, Attachment attach, String orCode) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertboardAttachments");
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, orCode);
			pstmt.setString(2, attach.getOriginalFilename());
			pstmt.setString(3, attach.getRenamedFilename());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}

	public int getLastBoard(Connection conn) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("getLastBoard");
		ResultSet rset = null;
		int boardNo = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				boardNo = rset.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
			close(rset);
		}
		return boardNo;
	}

	public UsedGoodsBoard getUgGoodsBoard(Connection conn, int boardNo) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("getUgGoodsBoard");
		ResultSet rset = null;
		UsedGoodsBoard board = new UsedGoodsBoard();
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				board.setNo(rset.getInt("no"));
				board.setTitle(rset.getString("title"));
				board.setContent(rset.getString("content"));
				board.setOrCode(rset.getString("or_code"));
				board.setRegDate(rset.getDate("reg_date"));
				board.setWriter(rset.getString("writer"));
				board.setPrice(rset.getInt("price"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return board;
	}

	public List<UsedGoodsBoard> getProductSaleBoardAll(Connection conn, int startNum, int endNum) {
		PreparedStatement pstmt = null;
		ResultSet rset= null;
		String sql = prop.getProperty("getProductSaleBoardAll");
		List<UsedGoodsBoard> list = new ArrayList<>();
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, startNum);
			pstmt.setInt(2, endNum);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				UsedGoodsBoard board = new UsedGoodsBoard();
				board.setNo(rset.getInt("no"));
				board.setTitle(rset.getString("title"));
				board.setOrCode(rset.getString("or_code"));
				board.setPrice(rset.getInt("price"));
				board.setWriter(rset.getString("writer"));
				board.setContent(rset.getString("content"));
				board.setRegDate(rset.getDate("reg_date"));
				list.add(board);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
			close(rset);
		}
		
		return list;
	}

	public List<Attachment> getUgBoardAttachment(Connection conn, String orCode) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("getUgBoardAttachment");
		List<Attachment> list = new ArrayList<>();
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, orCode);
			
			rset = pstmt.executeQuery();
			while(rset.next()) {
				Attachment attach = new Attachment();
				attach.setNo(rset.getInt("no"));
				attach.setCode(rset.getString("or_no"));
				attach.setOriginalFilename(rset.getString("original_filename"));
				attach.setRenamedFilename(rset.getString("renamed_filename"));
				attach.setRegDate(rset.getDate("reg_date"));
				
				list.add(attach);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
			close(rset);
		}
		return list;
	}

	public int getUgBoardCount(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("getUgBoardCount");
		int count = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				count = rset.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
			close(rset);
		}
		return count;
	}

	public int insertUgBoardState(Connection conn, int boardNo) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertUgBoardState");
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public String getUgGoodsBoardState(Connection conn, int no) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("getUgGoodsBoardState");
		ResultSet rset = null;
		String state = "";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				state = rset.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
			close(rset);
		}
		
		return state;
	}

	public int deleteAttachments(Connection conn, String orCode) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("deleteAttachments");
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, orCode);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}

	public int updateBoard(Connection conn, UsedGoodsBoard ugBoard) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("updateBoard");
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ugBoard.getTitle());
			pstmt.setInt(2, ugBoard.getPrice());
			pstmt.setString(3, ugBoard.getContent());
			pstmt.setInt(4, ugBoard.getNo());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}
}
