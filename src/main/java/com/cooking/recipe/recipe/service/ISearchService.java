package com.cooking.recipe.recipe.service;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.ui.Model;

import com.cooking.recipe.product.dto.ProductDTO;

public interface ISearchService {

	/* 레시피 검색시 레시피명(완전일치)으로 정보요청, 응답받은 element 가공해서 해당 레시피의 기본정보들 담아서 리턴*/
	public Map<String, Object> searchRecipe(String searchWord);

	/* 레시피 상세정보 요청시 기본정보, 재료정보, 과정정보 메소드들 호출해서 리턴받은값 model에 담아서 전달, 검색 DB에 검색기록 저장 */
	public void recipeDetail(String recipeName, Model model);

	/* 검색창에 상품 검색 했을 때 - 일부일치시에도 결과나옴*/
	public ArrayList<ProductDTO> searchProduct(String searchWord);

	/* 메인 화면에 로드시 베스트 레시피 셋팅, 주간/일간 버튼 눌렀을 때*/
	public void bestRecipe(String term);
	
	
	
	
}
