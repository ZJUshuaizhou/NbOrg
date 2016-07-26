package com.nb.org.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nb.org.dao.IAppOAuthDao;
import com.nb.org.domain.AppOAuth;
import com.nb.org.service.IAppOAuthService;

/**
 * @author huangxin
 * xin
 * 2016年1月22日
 */
@Service("appOAuthService")
public class AppOAuthServiceImpl implements IAppOAuthService {
	
	@Autowired
	private IAppOAuthDao dao;

	@Override
	public int insertOAuth(AppOAuth app) {
		// TODO Auto-generated method stub
		int c = dao.insertOAuth(app);
		AppOAuth oauth = dao.getOAuthByAppName(app.getAppName());
		return oauth.getId();
	}

	@Override
	public int updateOAuth(AppOAuth app) {
		// TODO Auto-generated method stub
		return dao.updateOAuth(app);
	}

	@Override
	public int deleteOAuth(int id) {
		// TODO Auto-generated method stub
		return dao.deleteOAuth(id);
	}

	@Override
	public AppOAuth getOAuthById(int id) {
		// TODO Auto-generated method stub
		return dao.getOAuthById(id);
	}

}
