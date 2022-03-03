package com.cooking.recipe.member.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.cooking.recipe.member.dto.MemberDTO;

@Repository
public interface IMemberDAO {

	public MemberDTO selectId(String id);		
	
	public int selectSocial(String social);

	public void insertMember(MemberDTO member);
	
	public int memberCount();
	
	public ArrayList<MemberDTO> selectAll(@Param("b")int begin, @Param("e")int end);
	
	public int searchCount(String searchWord);
	
	public ArrayList<MemberDTO> selectSearch(@Param("b")int begin, @Param("e")int end, @Param("word")String searchWord);

	public void deleteMember(String deleteId);
	

}
