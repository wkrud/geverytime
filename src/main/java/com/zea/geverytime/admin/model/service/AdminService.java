package com.zea.geverytime.admin.model.service;
import static com.zea.geverytime.common.JdbcTemplate.*;

import java.sql.Connection;
import java.util.List;


import com.zea.geverytime.admin.model.dao.AdminDao;
import com.zea.geverytime.common.model.vo.Attachment;
import com.zea.geverytime.info.model.vo.Info;

public class AdminService {
	private AdminDao adminDao = new AdminDao();

//	public List<Info> selectInfoBoard() {
//		Connection conn = getConnection();
//		List<Info> list = adminDao.selectInfoBoard(conn);
//		close(conn);
//		return list;
//	}
	public List<Info> selectInfoBoard() {
		Connection conn = null;
		List<Info> list = null;
		try {
			conn = getConnection();
			list = adminDao.selectInfoBoard(conn);
			
		}catch(Exception e) {
			throw e;
		}finally {
			close(conn);
		}
		return list;
	}

}