package com.nb.org.service;

import java.util.List;

import com.nb.org.domain.Position;

public interface IPositionService {
	
	public boolean isAdmin(String userName, String departmentSN);
	
	public Position selectPositionByPerDep(int perId, int depId);
	
	//20160303 upshi
	public List<Position> selectPositionsByPersonId(int personId);
}
