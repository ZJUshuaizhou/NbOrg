package com.nb.org.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nb.org.dao.ISyncOrgDao;
import com.nb.org.domain.SyncOrg;
import com.nb.org.service.ISyncOrgService;
@Service("syncOrgEntityService")
public class SyncOrgServiceImpl implements ISyncOrgService{
	@Autowired
	private ISyncOrgDao iSyncOrgDao;
	
	@Override
	public int insert(SyncOrg syncOrg) {
		int res = 0;
		res = iSyncOrgDao.insert(syncOrg);
		return res;
	}

	@Override
	public int deteleByOrgCoding(String OrgCoding) {
		int res = 0;
		res = iSyncOrgDao.deteleByOrgCoding(OrgCoding);
		return res;
	}

	@Override
	public SyncOrg selectByOrgCoding(String OrgCoding) {
		SyncOrg syncOrg = null;
		syncOrg = iSyncOrgDao.selectByOrgCoding(OrgCoding);
		return syncOrg;
	}

	@Override
	public SyncOrg selectByDepId(int depId) {
		SyncOrg syncOrg = null;
		syncOrg = iSyncOrgDao.selectByDepId(depId);
		return syncOrg;
	}
}
