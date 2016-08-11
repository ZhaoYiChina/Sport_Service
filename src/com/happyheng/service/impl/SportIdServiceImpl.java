package com.happyheng.service.impl;

import java.sql.Connection;

import com.happyheng.dao.SportRecordDao;
import com.happyheng.dao.UserDao;
import com.happyheng.dao.impl.SportRecordDaoImplement;
import com.happyheng.dao.impl.UserDaoImplement;
import com.happyheng.entity.result.SportRecordResult;
import com.happyheng.service.SportIdService;
import com.happyheng.utils.ConnectionFactory;

public class SportIdServiceImpl extends BaseService implements SportIdService{
	
	private UserDao userDao;
	private SportRecordDao recordDao;
	
	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public SportRecordDao getRecordDao() {
		return recordDao;
	}

	public void setRecordDao(SportRecordDao recordDao) {
		this.recordDao = recordDao;
	}

	@Override
	public SportRecordResult getSportId(String userKey) {
		Connection connection = null;
		connection = ConnectionFactory.getInstance().makeConnection();

		SportRecordResult recordResult = new SportRecordResult();

		try {

			// 1、先使用userKey得到对应的userId
			Integer userId = userDao.getUserId( userKey);

			if (userId != null) {
				// 2、给Sport表中插入userId,获取sportId
				int sportId = recordDao.insertUserId(connection, userId);
				
				recordResult.setCode(RESULT_CODE_SUCCESS);
				recordResult.setSportId(sportId);
				return recordResult;
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		recordResult.setCode(RESULT_CODE_ERROR);
		return recordResult;
	}
}
