package com.nb.org.service;

import com.nb.org.domain.SyncOrg;

public interface ISyncOrgService {
	int insert(SyncOrg syncOrg);
	int deteleByOrgCoding(String OrgCoding);
	SyncOrg selectByOrgCoding(String OrgCoding);
	SyncOrg selectByDepId(int depId);
}
