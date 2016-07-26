package com.nb.org.dao;

import java.util.List;

import com.nb.org.domain.Position;

public interface IPositionDao {

	Position selectPosition(int id);

	Position selectPositionByPerDep(int perId, int depId);

	int updatePosition(Position pos);
	
	//20160303 upshi
	public List<Position> selectPositionsByPersonId(int personId);
	
}