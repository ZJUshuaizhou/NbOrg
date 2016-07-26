package com.nb.org.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nb.org.dao.IAppSAMLDao;
import com.nb.org.domain.AppSAML;
import com.nb.org.service.IAppSAMLService;

/**
 * @author huangxin
 * xin
 * 2016年1月22日
 */
@Service("appSAMLService")
public class AppSAMLServiceImpl implements IAppSAMLService {
	
	@Autowired
	private IAppSAMLDao dao;

	@Override
	public int insertSAML(AppSAML app) {
		// TODO Auto-generated method stub
		dao.insertSAML(app);
		AppSAML saml = dao.getSAMLByAppName(app.getAppName());
		return saml.getId();
	}

	@Override
	public int updateSAML(AppSAML app) {
		// TODO Auto-generated method stub
		return dao.updateSAML(app);
	}

	@Override
	public int deleteSAML(int id) {
		// TODO Auto-generated method stub
		return dao.deleteSAML(id);
	}

	@Override
	public AppSAML getSAMLById(int id) {
		// TODO Auto-generated method stub
		return dao.getSAMLById(id);
	}

	@Override
	public AppSAML getSAMLByAppName(String name) {
		// TODO Auto-generated method stub
		return dao.getSAMLByAppName(name);
	}
	
	@Override
	public AppSAML getSAMLByAppIssuer(String issuer) {
		// TODO Auto-generated method stub
		return dao.getSAMLByAppIssuer(issuer);
	}

	@Override
	public int setIssuer(AppSAML app) {
		// TODO Auto-generated method stub
		return dao.setIssuer(app);
	}

}
