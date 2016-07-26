package com.nb.org.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nb.org.dao.IAppSTSDao;
import com.nb.org.domain.AppSTS;
import com.nb.org.service.IAppSTSService;

/**
 * @author huangxin
 * xin
 * 2016年1月22日
 */
@Service("appSTSService")
public class AppSTSServiceImpl implements IAppSTSService {
	
	@Autowired
	private IAppSTSDao dao;

	@Override
	public int insertSTS(AppSTS app) {
		// TODO Auto-generated method stub
		dao.insertSTS(app);
		AppSTS STS = dao.getSTSByAppName(app.getAppName());
		return STS.getId();
	}

	@Override
	public int updateSTS(AppSTS app) {
		// TODO Auto-generated method stub
		return dao.updateSTS(app);
	}

	@Override
	public int deleteSTS(int id) {
		// TODO Auto-generated method stub
		return dao.deleteSTS(id);
	}

	@Override
	public AppSTS getSTSById(int id) {
		// TODO Auto-generated method stub
		return dao.getSTSById(id);
	}

	@Override
	public AppSTS getSTSByAppName(String name) {
		// TODO Auto-generated method stub
		return dao.getSTSByAppName(name);
	}

}
