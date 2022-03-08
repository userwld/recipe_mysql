package com.cooking.recipe.order.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.cooking.recipe.member.dao.IMemberDAO;
import com.cooking.recipe.member.dto.MemberDTO;
import com.cooking.recipe.order.dao.ICartDAO;
import com.cooking.recipe.order.dao.IOrderDAO;
import com.cooking.recipe.order.dto.CartDTO;
import com.cooking.recipe.order.dto.OrderDTO;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@Service
public class OrderServiceImpl implements IOrderService{
	@Autowired IOrderDAO dao;
	@Autowired IMemberDAO memberDao;
	@Autowired ICartDAO cartDao;
	@Autowired HttpSession session;

	/* 상품상세페이지에서 바로주문 눌렀을 때 주문페이지 셋팅*/
	@Override
	public boolean orderNowProc(String productNum, String productName, String price, String amount, Model model) {
		Map<String, Object> map = new HashMap<String,Object>();
		String id = (String)session.getAttribute("loginId");
		if(id == null) return false;
		
		map.put("id", id); map.put("productNum", Integer.parseInt(productNum)); map.put("productName", productName);
		map.put("price", Integer.parseInt(price)); map.put("amount", Integer.parseInt(amount));
		
		model.addAttribute("state", "now");
		session.setAttribute("orderInfo", map);
		
		MemberDTO member = memberDao.selectId(id);
		model.addAttribute("memberInfo", member);	
		return true;
	}
	
	/* 주문페이지 - 결제버튼 클릭시 카카오 페이 결제 준비 */
	@Override		
	public String payProc(Map<String, String> map) {
		String cid = "TC0ONETIME"; String partner_order_id = "partner_order_id";	// 테스트용 / 승인에서도 같은값으로 써야하기 때문에 변수로 만들어서 전달
		String id = (String)session.getAttribute("loginId");
		Map<String, Object> orderInfo = (Map<String, Object>) session.getAttribute("orderInfo");
		String item = (String) orderInfo.get("productName");
		Integer qnt = (int) orderInfo.get("amount");
		Integer totalPrice = Integer.parseInt(map.get("totalPrice"));
		Integer tax_free = (int)((double)totalPrice * 0.1);
		String approve = "http://localhost:8085/recipe/payApprove";
		String fail = "http://localhost:8085/recipe/payFail";
		String cancel = "http://localhost:8085/recipe/payCancel";
		String result = "";
			
		String admin_key = "5241637b723b0cbf3b3bce9e90e1b510";
		String reqURL = "https://kapi.kakao.com/v1/payment/ready";
		String parameter = "cid="+cid+"&partner_order_id="+partner_order_id+"&partner_user_id="+id;	
		parameter += "&item_name="+item+"&quantity="+qnt+"&total_amount="+totalPrice+"&tax_free_amount="+tax_free;
		parameter += "&approval_url="+approve+"&fail_url="+fail+"&cancel_url="+cancel;
		
//		System.out.println(parameter);
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", "KakaoAK "+admin_key);
			conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
			conn.setDoOutput(true);
			
			OutputStreamWriter osr = new OutputStreamWriter(conn.getOutputStream());	
			BufferedWriter bw = new BufferedWriter(osr);								
			bw.write(parameter); 	
			bw.close();  		 // 닫을때 flush 자동으로 됨
			
			int responseCode = conn.getResponseCode();
			System.out.println(responseCode);
			System.out.println(conn.getResponseMessage());
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	       
			String line = "";
	        while ((line = br.readLine()) != null) {
	            result += line;
	        }
			
	        System.out.println("response body : " + result);
	        
	        JsonParser parser = new JsonParser();
	        JsonElement element = parser.parse(result);
	        String tid = element.getAsJsonObject().get("tid").getAsString();
	        map.put("tid", tid); map.put("cid",cid); map.put("partner_order_id", partner_order_id); map.put("partner_user_id", id);
			session.setAttribute("payInfo", map);	// 배송주소, 이름, 결제승인파라미터 담겨있음 -> 결제승인파라미터는 결제승인에서, 나머지는 결제 후 멤버테이블에 정보수정에 사용
	        
	        return result;
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return result;
	}

	/* 카카오페이에서 결제 준비 성공했을 때, 결제 승인 단계*/
	@Override
	public int payApprove(String pg_token) {
		String admin_key = "5241637b723b0cbf3b3bce9e90e1b510";
		String reqURL = "https://kapi.kakao.com/v1/payment/approve";
		Map<String, String> payInfo = (Map<String, String>) session.getAttribute("payInfo");
		String parameter = "cid="+payInfo.get("cid")+"&tid="+payInfo.get("tid");
		parameter += "&partner_order_id="+payInfo.get("partner_order_id")+"&partner_user_id="+payInfo.get("partner_user_id");
		parameter += "&pg_token="+pg_token;
//		System.out.println(parameter);
		
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", "KakaoAK "+admin_key);
			conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
			conn.setDoOutput(true);
			
			OutputStreamWriter osr = new OutputStreamWriter(conn.getOutputStream());	
			BufferedWriter bw = new BufferedWriter(osr);								
			bw.write(parameter); 	
			bw.close();  		 
			
			int responseCode = conn.getResponseCode();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	       
			String result = "";
			String line = "";
	        while ((line = br.readLine()) != null) {
	            result += line;
	        }
//	        System.out.println("response body : " + result);
	        return responseCode;
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public String[] payFail() {
		Map<String,Object> orderInfo = (Map<String, Object>) session.getAttribute("orderInfo");
		String[] param = new String[4];
		
		param[0] = orderInfo.get("productNum")+"";
		param[1] = orderInfo.get("productName")+"";
		param[2] = orderInfo.get("price")+"";
		param[3] = orderInfo.get("amount")+"";
		
		return param;
	}

	@Override
	public void addrUpdate() {
		Map<String,String> addrInfo = (Map<String, String>) session.getAttribute("payInfo");
		String id = (String)session.getAttribute("loginId");
		if(id == null) return;
		
		MemberDTO member = new MemberDTO();
		member.setAddr1(addrInfo.get("addr1")); member.setAddr2(addrInfo.get("addr2"));
		member.setId(id); member.setName(addrInfo.get("name"));
		
		dao.updateAddr(member);
	}

	@Override
	public void orderInsert() {
		String id = (String)session.getAttribute("loginId");
		if(id == null) return;
		
		Map<String,Object> orderInfo = (Map<String,Object>)session.getAttribute("orderInfo");
		Map<String,String> payInfo = (Map<String, String>) session.getAttribute("payInfo");
		
		OrderDTO order = new OrderDTO();
		order.setId(id); order.setProductName((String)orderInfo.get("productName"));
		order.setAmount((int)orderInfo.get("amount")); order.setProductNum((int)orderInfo.get("productNum"));
		order.setTotalPrice(Integer.parseInt(payInfo.get("totalPrice")));
		
		dao.insertOrder(order);
	}

	@Override
	public void stockUpdate() {
		Map<String,Object> orderInfo = (Map<String,Object>)session.getAttribute("orderInfo");
		
		int productNum = (int)orderInfo.get("productNum");
		int amount = (int)orderInfo.get("amount");
		
		dao.updateStock(productNum, amount);
	}

	@Override
	public boolean orderCart(String[] orderItems, Model model) {
		ArrayList<CartDTO> cart = new ArrayList<CartDTO>();
		String id = (String)session.getAttribute("loginId");
		if(id == null) return false;
		
		for(int i=0; i < orderItems.length; i++) {
			int cartNum = Integer.parseInt(orderItems[i]);
			CartDTO dto = cartDao.selectCartNum(cartNum);
			cart.add(dto);
		}
		
		model.addAttribute("state", "cart");
		session.setAttribute("orderInfo", cart);
		
		MemberDTO member = memberDao.selectId(id);
		model.addAttribute("memberInfo", member);	
				
		return true;
	}


	
	
	
	
	
	

}
