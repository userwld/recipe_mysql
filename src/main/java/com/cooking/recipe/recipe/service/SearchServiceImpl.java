package com.cooking.recipe.recipe.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.cooking.recipe.product.dto.ProductDTO;
import com.cooking.recipe.recipe.dao.ISearchDAO;
import com.cooking.recipe.recipe.dto.SearchDTO;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class SearchServiceImpl implements ISearchService{
	@Autowired HttpSession session;
	@Autowired ISearchDAO dao;

	/* URL 매개변수로 전달해주면, 검색해서 JSON으로 받은 응답을 JsonElement로 파싱해서 리턴해줌*/
	private JsonElement getElement(String reqURL) {	
		JsonElement element = null;
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();	
			conn.setRequestMethod("GET");							// GET으로 안하면 500번 서버오류남
			conn.setDoOutput(true);
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("contentType", "application/json");

			int responseCode = conn.getResponseCode();
//			System.out.println(responseCode);
//			System.out.println(conn.getResponseMessage());
			
	        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        String line = "";
	        String result = "";
	    
	        while ((line = br.readLine()) != null) {
	            result += line;
	        }
	        
	        System.out.println("response body : " + result);
	        System.out.println("result.split : "+result.split(","));
			
	        JsonParser parser = new JsonParser();
	        element = parser.parse(result);
	        
		} catch (Exception e) {
			e.printStackTrace();
		}		
        return element;
	}
	
	/* 레시피 검색시 레시피명(완전일치)으로 정보요청, 응답받은 element 가공해서 해당 레시피의 기본정보들 담아서 리턴*/
	@Override
	public Map<String, Object> searchRecipe(String searchWord) {
		Map<String, Object> recipeInfo = new HashMap<String, Object>();
							
		try {
			StringBuilder urlBuilder = new StringBuilder("http://211.237.50.150:7080/openapi/");
			String api_key = "71cc47d877902d6e6ca57af127428b905f70b69ca2f5d2c0230a1050ef10c1e8";
			urlBuilder.append(api_key);
			urlBuilder.append("/json/Grid_20150827000000000226_1/1/1");		// 파라미터 전달시 무조건 UTF-8 인코딩 해야함 -> 안그러면 row 못가져옴!
			urlBuilder.append("?" + URLEncoder.encode("RECIPE_NM_KO","UTF-8") + "=" + URLEncoder.encode(searchWord,"UTF-8"));
			
			JsonElement element = getElement(urlBuilder.toString());
	        JsonObject grid = element.getAsJsonObject().get("Grid_20150827000000000226_1").getAsJsonObject();
	        JsonArray rowArr = grid.getAsJsonObject().get("row").getAsJsonArray();

	        if(rowArr.size() != 0) {	        	
		        JsonObject row = rowArr.get(0).getAsJsonObject();
		        System.out.println(row);
			    
		        String recipeId = row.getAsJsonObject().get("RECIPE_ID").getAsString();
		        String recipeName = row.getAsJsonObject().get("RECIPE_NM_KO").getAsString();
		        String summary = row.getAsJsonObject().get("SUMRY").getAsString();
		        String category = row.getAsJsonObject().get("TY_NM").getAsString();
		        String cookingTime = row.getAsJsonObject().get("COOKING_TIME").getAsString();
		        String calorie = row.getAsJsonObject().get("CALORIE").getAsString();
		        String qnt = row.getAsJsonObject().get("QNT").getAsString();	       
		        String level = row.getAsJsonObject().get("LEVEL_NM").getAsString();
		        String img = row.getAsJsonObject().get("IMG_URL").getAsString();
		        
		        recipeInfo.put("recipeId", recipeId);
		        recipeInfo.put("recipeName", recipeName); recipeInfo.put("summary", summary);
		        recipeInfo.put("category", category); recipeInfo.put("cookingTime", cookingTime);
		        recipeInfo.put("calorie", calorie); recipeInfo.put("qnt", qnt);
		        recipeInfo.put("level", level); recipeInfo.put("img", img);     
	        }else {
	        	recipeInfo.put("recipeName","없음");
	        }
	            
		} catch (Exception e) {
			e.printStackTrace();
		}			
		return recipeInfo;
	}
	
	/* 검색한 레시피 상세정보 요청시 레시피의 재료정보 레시피 아이디 통해 요청, 응답받은 element 가공해서 해당 레시피의 재료정보 리턴*/
	private Map<String,Object> searchIngredient(String recipeId){
		Map<String,Object> resultIngredient = new HashMap<String,Object>();
		
		try {
			StringBuilder urlBuilder = new StringBuilder("http://211.237.50.150:7080/openapi/");
			String api_key = "71cc47d877902d6e6ca57af127428b905f70b69ca2f5d2c0230a1050ef10c1e8";
			urlBuilder.append(api_key);
			urlBuilder.append("/json/Grid_20150827000000000227_1/1/100");		
			urlBuilder.append("?" + URLEncoder.encode("RECIPE_ID","UTF-8") + "=" + URLEncoder.encode(recipeId,"UTF-8"));
			
			JsonElement element = getElement(urlBuilder.toString());
	        JsonObject grid = element.getAsJsonObject().get("Grid_20150827000000000227_1").getAsJsonObject();
	        JsonArray rowArr = grid.getAsJsonObject().get("row").getAsJsonArray();
	        
	        for(int i=0; i < rowArr.size(); i++) {
	        	String ingName = rowArr.get(i).getAsJsonObject().get("IRDNT_NM").getAsString();
	        	String ingQnt = rowArr.get(i).getAsJsonObject().get("IRDNT_CPCTY").getAsString();
	        	
	        	resultIngredient.put("ing"+i, ingName + " - " + ingQnt);
	        }
						
		}catch(Exception e) {
			e.printStackTrace();
		}
		return resultIngredient;
	}
	
	/* 검색한 레시피 상세정보 요청시 레시피의 과정정보 레시피 아이디 통해 요청, 응답받은 element 가공해서 해당 레시피의 과정정보 리턴*/
	private Map<String,Object> searchCookingStep(String recipeId){
		Map<String,Object> resultStep = new HashMap<String,Object>();
		
		try {
			StringBuilder urlBuilder = new StringBuilder("http://211.237.50.150:7080/openapi/");
			String api_key = "71cc47d877902d6e6ca57af127428b905f70b69ca2f5d2c0230a1050ef10c1e8";
			urlBuilder.append(api_key);
			urlBuilder.append("/json/Grid_20150827000000000228_1/1/50");		
			urlBuilder.append("?" + URLEncoder.encode("RECIPE_ID","UTF-8") + "=" + URLEncoder.encode(recipeId,"UTF-8"));
			
			JsonElement element = getElement(urlBuilder.toString());
	        JsonObject grid = element.getAsJsonObject().get("Grid_20150827000000000228_1").getAsJsonObject();
	        JsonArray rowArr = grid.getAsJsonObject().get("row").getAsJsonArray();
	        
	        for(int i=0; i < rowArr.size(); i++) {
	        	String dc = rowArr.get(i).getAsJsonObject().get("COOKING_DC").getAsString();
	        	resultStep.put("dc"+i, dc);
	        }
	          						
		}catch(Exception e) {
			e.printStackTrace();
		}
		return resultStep;
	}

	/* 레시피 상세정보 요청시 위에 기본정보, 재료정보, 과정정보 메소드들 호출해서 리턴받은값 model에 담아서 전달, 검색 DB에 검색기록 저장 */
	@Override
	public void recipeDetail(String recipeName, Model model) {
		Map<String,Object> resultBasic = searchRecipe(recipeName);
		String recipeId = (String) resultBasic.get("recipeId");
		
		Map<String,Object> resultIngredient = searchIngredient(recipeId);
		Map<String,Object> resultStep = searchCookingStep(recipeId);
		
		model.addAttribute("resultBasic", resultBasic);
		model.addAttribute("resultIng", resultIngredient);
		model.addAttribute("resultStep", resultStep);
		
		// 검색 DB에 넣기(해당 레시피가 오늘 검색한적이 있으면 검색횟수만 올리고, 검색한적이 없으면 오늘날짜로 넣음)
		SimpleDateFormat sdf = new SimpleDateFormat("YY-MM-dd");
		String nowDate = sdf.format(new Date());
		
		if(dao.isExistView(recipeName,nowDate) == 0) {		// 메인영역에 베스트 부분에 이미지 필요하므로 db에 같이저장
			dao.insertView(recipeName, (String)resultBasic.get("img"));
		}else {
			dao.updateView(recipeName,nowDate);
		}
	}

	/* 검색창에 상품 검색 했을 때 - 일부일치시에도 결과나옴*/
	@Override
	public ArrayList<ProductDTO> searchProduct(String searchWord) {
		return dao.searchProduct(searchWord);
	}

	/* 메인 화면에 로드시 베스트 레시피 셋팅, 주간/일간 버튼 눌렀을 때*/
	@Override
	public void bestRecipe(String term) {		// term에 따라 주간(현재날짜-8일~ 현재날짜-1일)/일간(현재날짜-1일) 나눠서 db에서 조회
		ArrayList<SearchDTO> bestRecipe = dao.selectBestRecipe(term);
		
		session.setAttribute("bestRecipe", bestRecipe);
		session.setAttribute("recipeTerm", term);

	}
	
	
	
	
	
	
	
	
}
