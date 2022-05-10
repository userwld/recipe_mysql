package com.cooking.recipe.recipe.dto;

import java.util.Date;

public class SearchDTO {
	private String recipeName;
	private int searchCount;
	private Date searchDate;
	private String img;
	private int totalSearch;			// 메인에서 베스트 레시피 추출하기 위해 / ex) 쿼리문 select sum(searchDount) as totalSearch ...
	
	public String getRecipeName() {
		return recipeName;
	}
	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}
	public int getSearchCount() {
		return searchCount;
	}
	public void setSearchCount(int searchCount) {
		this.searchCount = searchCount;
	}
	public Date getSearchDate() {
		return searchDate;
	}
	public void setSearchDate(Date searchDate) {
		this.searchDate = searchDate;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public int getTotalSearch() {
		return totalSearch;
	}
	public void setTotalSearch(int totalSearch) {
		this.totalSearch = totalSearch;
	}
	
	

}
