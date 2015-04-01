package com.imagsky.dao;

import java.util.ArrayList;
import java.util.List;

import com.imagsky.exception.BaseDBException;
import com.imagsky.v81j.domain.Member;

public abstract class MemberDAO extends AbstractDbDAO{

	public static MemberDAO getInstance() {
		return MemberDAOImpl.getInstance();
	}
	
	public abstract Member validURL(Member member) throws BaseDBException;

	public abstract List<Object> findListWithSample(Object obj) throws BaseDBException;

	public abstract List<Object> findListWithSample(Object obj, ArrayList orderByPair)
			throws BaseDBException;

	public abstract Object create(Object obj) throws BaseDBException;
}
