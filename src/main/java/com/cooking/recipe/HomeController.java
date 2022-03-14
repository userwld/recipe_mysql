package com.cooking.recipe;

import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cooking.recipe.order.service.IOrderService;
import com.cooking.recipe.recipe.service.ISearchService;

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	@Autowired IOrderService orderService;			// 메인에 베스트 상품과 베스트 레시피 구하기 위해 
	@Autowired ISearchService searchService;
	
	 @RequestMapping(value = "/")
	   public String main(Model model, HttpSession session) {
		 model.addAttribute("formpath", "main");
	     return "index";
	   }
	 
	   @RequestMapping(value = "/main")
	   public void main(HttpSession session) {
		   if(session.getAttribute("sales") == null)
				 orderService.bestSales("week");		// 기본적으로 주간 베스트로 셋팅
		   if(session.getAttribute("bestRecipe") == null)
				 searchService.bestRecipe("week");
	   }
	   
	   @RequestMapping(value = "/index")
	   public String index(Model model, HttpSession session, String formpath) {
	      if(formpath == null) {
	    	  formpath="main";
	      }	      
	      model.addAttribute("formpath", formpath);
	      logger.warn("formpath : " + formpath);
	      return "index";
	   }
	   
	   @RequestMapping(value="/join")
	   public String join() {
		   return "member/join";
	   }
	   @RequestMapping(value="/memberList")
	   public String memberList() {
		   return "member/memberList";
	   }
	   
	   @RequestMapping(value="/search")
	   public String search() {
		   return "recipe/search";
	   }
	   
	   @RequestMapping(value="/recipeDetail")
	   public String recipeDetail() {
		   return "recipe/recipeDetail";
	   }
	
	   @RequestMapping(value="/productDetail")
	   public String productDetail() {
		   return "product/productDetail";
	   }
	   
	   @RequestMapping(value="/productList")
	   public String productList() {
		   return"product/productList";
	   }
	   
	   @RequestMapping(value="/cart")
	   public String cart() {
		   return "order/cart";
	   }
	   
	   @RequestMapping(value="/order")
	   public String order() {
		   return "order/order";
	   }
	   
	   @RequestMapping(value="/orderDetail")
	   public String orderDetail() {
		   return "order/orderDetail";
	   }
	   
	   @RequestMapping(value="/orderHistory")
	   public String orderHistory() {
		   return "order/orderHistory";
	   }
}
