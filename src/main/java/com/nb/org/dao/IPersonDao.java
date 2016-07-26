package com.nb.org.dao;

import java.util.List;

import com.nb.org.domain.Person;
import com.nb.org.domain.Position;
import com.nb.org.vo.PersonVO;

public interface IPersonDao {

	int insertPerson(Person per);

	Person selectPerson(int id);
	
	/*
	 * @author upshi
	 * @version 20160119
	 */
	Person selectPersonByUserName(String userName);

	int updatePerson(Person per);
	
	int updatePersonDepId(Person per);

	int delPerson(int id);

	int saveRelativity(Position pos);

	int deleteRelativity(Position pos);

	int updateRelativity(Position pos);

	List<Person> selectPersonsByName(String name);
	
	List<Person> getPagePer(PersonVO vo);
	
	int getTotalPerPages(PersonVO vo);
	
	List<Person> getPersonsByUserNames(List<String> spliceUserNames);

}