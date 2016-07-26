package com.nb.org.dao;

import java.util.List;

import com.nb.org.domain.SyncOrg;

public interface ISyncOrgDao {
	int insert(SyncOrg syncOrg);
	int deteleByOrgCoding(String OrgCoding);
	SyncOrg selectByOrgCoding(String OrgCoding);
	SyncOrg selectByDepId(int depId);
}
