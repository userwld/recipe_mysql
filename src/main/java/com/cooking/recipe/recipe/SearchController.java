package com.cooking.recipe.recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cooking.recipe.product.dto.ProductDTO;
import com.cooking.recipe.recipe.service.ISearchService;

@Controller
public class SearchController {
	@Autowired ISearchService service;
	
	/* 메인화면 또는 헤더에서 레시피 / 상품 검색 */
	@RequestMapping(value="/searchProc")
	public String searchProc(String sel, String searchWord, Model model) {
		Map<String,Object> result = new HashMap<String,Object>();
		ArrayList<ProductDTO> proResult = new ArrayList<ProductDTO>();
		if(sel.equals("recipe")) {
			result = service.searchRecipe(searchWord);
			model.addAttribute("result", result);
		}else {
			proResult = service.searchProduct(searchWord);
			model.addAttribute("result", proResult);
		}
		model.addAttribute("searchWord", searchWord);
		model.addAttribute("sel", sel);
		return "forward:index?formpath=search";
	}
	
	/* 검색된 레시피 이미지 또는 레시피명 클릭시 디테일 페이지 셋팅해서 출력*/
	@RequestMapping(value="/recipeViewProc")
	public String recipeViewProc(String recipeName, Model model) {
		service.recipeDetail(recipeName, model);		// 레시피기본정보, 재료정보, 과정정보 메소드들 호출해서 가공후 model에 담아서 리턴
		return "forward:index?formpath=recipeDetail";
	}
	
	/* 메인 페이지에서 베스트 레시피 일간/주간 버튼 클릭시 */
	@RequestMapping(value="/bestRecipe", produces = "application/json; charset=utf-8")
	@ResponseBody
	public Map<String,String> bestRecipe(@RequestBody Map<String,String> map) {
		service.bestRecipe(map.get("term"));
		return map;
	}
	
	
}
