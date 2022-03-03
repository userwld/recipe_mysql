package com.cooking.recipe.member.dao;

import org.springframework.stereotype.Repository;

import com.cooking.recipe.member.dto.MemberDTO;

@Repository
public interface IMemberDAO {

	public MemberDTO isExistId(String id);
	
	public int isExistSocial(String social);

	public void insertMember(MemberDTO member);

}
