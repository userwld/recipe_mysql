package com.cooking.recipe.order.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.cooking.recipe.member.dao.IMemberDAO;
import com.cooking.recipe.member.dto.MemberDTO;
import com.cooking.recipe.order.dao.ICartDAO;
import com.cooking.recipe.order.dao.IOrderDAO;
import com.cooking.recipe.order.dto.CartDTO;
import com.cooking.recipe.order.dto.DeliveryDTO;
import com.cooking.recipe.order.dto.OrderDTO;
import com.cooking.recipe.order.dto.OrderDetailDTO;
import com.cooking.recipe.product.dao.IProductDAO;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@Service
public class OrderServiceImpl implements IOrderService{
	@Autowired IOrderDAO dao;
	@Autowired IMemberDAO memberDao;
	@Autowired ICartDAO cartDao;
	@Autowired IProductDAO productDao;
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
	
	/* 장바구니 페이지에서 주문하기 눌렀을 때 주문페이지 셋팅 - 장바구니페이지에서 체크한 항목들만 주문페이지에서 출력 */
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

	/* 주문페이지 - 결제버튼 클릭시 최종적으로 상품 재고가 남아있는지 확인 후 주문가능 여부 반환 */
	@Override
	public boolean stockCheck(String state) {
		if(state.equals("now")) {
			Map<String, Object> orderInfo = (Map<String, Object>) session.getAttribute("orderInfo");
			int stock = productDao.selectStock((int)orderInfo.get("productNum"));
			if(stock <= 0) return false;
		}else {
			ArrayList<CartDTO> orderInfo = (ArrayList<CartDTO>) session.getAttribute("orderInfo");
			for(CartDTO dto : orderInfo) {
				int productNum = dto.getProductNum();
				int stock = productDao.selectStock(productNum);
				if(stock <= 0) return false;
			}
		}
		return true;
	}

	/* 주문페이지 - 결제버튼 클릭시 카카오 페이 결제 준비(map에 담긴 state변수로 바로주문인지, 장바구니에서 온 주문인지 구분해서 파라미터값 다르게 가져감) */
	@Override		
	public String payProc(Map<String, String> map) {
		String cid = "TC0ONETIME"; String partner_order_id = "partner_order_id";	// 테스트용 / 승인에서도 같은값으로 써야하기 때문에 변수로 만들어서 전달
		String id = (String)session.getAttribute("loginId");
		String item = "";
		Integer qnt = 0;
		if(map.get("state").equals("now")) {
			Map<String, Object> orderInfo = (Map<String, Object>) session.getAttribute("orderInfo");
			item = (String) orderInfo.get("productName");
			qnt = (int) orderInfo.get("amount");
		}else {
			ArrayList<CartDTO> orderInfo = (ArrayList<CartDTO>) session.getAttribute("orderInfo");
			if(id == null || orderInfo == null) return "잘못된 접근입니다.";
			
			item = orderInfo.size() == 1 ? (String) orderInfo.get(0).getProductName() : (String) orderInfo.get(0).getProductName() + " 외 "+ orderInfo.size() + "건";
			qnt = (int) orderInfo.get(0).getAmount();
		}
		
		Integer totalPrice = Integer.parseInt(map.get("totalPrice"));
		Integer tax_free = (int)((double)totalPrice * 0.1);
		String approve = "http://localhost:8085/recipe/payApprove?state="+map.get("state");
		String fail = "http://localhost:8085/recipe/payFail?state="+map.get("state");
		String cancel = "http://localhost:8085/recipe/payCancel?state="+map.get("state");
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
	        
			String resURL = element.getAsJsonObject().get("next_redirect_pc_url").getAsString();
			
	        return resURL;
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
	
	/* 결제 취소 또는 실패시 이전 페이지로 돌아가기 위한 파라미터들 */
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

	/* 결제 승인 성공시, 회원테이블에 이름, 주소 업데이트 */
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
	
	/* 단건 결제 승인 성공시(바로주문), 주문테이블에 단건 주문 삽입 / 배송테이블 배송지 정보 삽입 메소드 호출 */
	@Override
	public void orderInsert() {
		String id = (String)session.getAttribute("loginId");
		if(id == null) return;
		
		Map<String,Object> orderInfo = (Map<String,Object>)session.getAttribute("orderInfo");
		Map<String,String> payInfo = (Map<String, String>) session.getAttribute("payInfo");
				
		String orderNum = createOrderNum();
		OrderDTO order = new OrderDTO();
		order.setOrderNum(orderNum);
		order.setId(id); order.setProductName((String)orderInfo.get("productName"));
		order.setAmount((int)orderInfo.get("amount")); order.setProductNum((int)orderInfo.get("productNum"));
		order.setTotalPrice(Integer.parseInt(payInfo.get("totalPrice")));
		
		dao.insertOrder(order);
		deliveryInsert(payInfo,orderNum,id);
	}
	
	/* 결제 승인 후 배송테이블에 배송정보 삽입 -> 주문테이블에 주문삽입 된 후 호출됨*/
	private void deliveryInsert(Map<String,String> payInfo, String orderNum, String id) {
		DeliveryDTO delivery = new DeliveryDTO();
		
		delivery.setId(id); delivery.setAddr1(payInfo.get("addr1")); delivery.setAddr2(payInfo.get("addr2"));
		delivery.setName(payInfo.get("name")); delivery.setOrderNum(orderNum);
		dao.insertDelivery(delivery);
	}
	
	/* 단건 결제 승인 성공시(바로주문), 상품테이블에 단건 상품 재고 수정 */
	@Override
	public void stockUpdate() {
		Map<String,Object> orderInfo = (Map<String,Object>)session.getAttribute("orderInfo");
		
		int productNum = (int)orderInfo.get("productNum");
		int amount = (int)orderInfo.get("amount");
		
		dao.updateStock(productNum, amount);
		
		session.removeAttribute("orderInfo");
		session.removeAttribute("payInfo");
	}
	
	/* 다건 결제 승인 성공시(장바구니 담긴 품목 주문), 주문테이블에 다건 주문 삽입 / 배송테이블 배송지 정보 삽입 메소드 호출 */
	@Override
	public void ordersInsert() {
		String id = (String)session.getAttribute("loginId");
		if(id == null) return;
		
		ArrayList<CartDTO> orderInfo = (ArrayList<CartDTO>)session.getAttribute("orderInfo");
		Map<String,String> payInfo = (Map<String, String>) session.getAttribute("payInfo");
		
		String orderNum = createOrderNum();

		for(CartDTO cart : orderInfo) {
			OrderDTO order = new OrderDTO();
			order.setOrderNum(orderNum);
			order.setId(id); order.setProductNum(cart.getProductNum()); order.setProductName(cart.getProductName());
			order.setAmount(cart.getAmount()); order.setTotalPrice(Integer.parseInt(payInfo.get("totalPrice")));
			dao.insertOrder(order);
		}
		
		deliveryInsert(payInfo,orderNum,id);
	}

	/* 다건 결제 승인 성공시(장바구니 담긴 품목 주문), 상품테이블에 다건 상품 재고 수정 */
	@Override
	public void stocksUpdate() {
		ArrayList<CartDTO>orderInfo = (ArrayList<CartDTO>)session.getAttribute("orderInfo");
		
		for(CartDTO cart : orderInfo) {
			int productNum = cart.getProductNum();
			int amount = cart.getAmount();
			dao.updateStock(productNum, amount);
		}		
	}
	
	/* 다건 결제 승인 성공시(장바구니 담긴 품목 주문), 장바구니 테이블에 주문된 상품 삭제 */
	@Override
	public void cartDelete() {
		ArrayList<CartDTO>orderInfo = (ArrayList<CartDTO>)session.getAttribute("orderInfo");
		
		for(CartDTO cart : orderInfo) {
			int cartNum = cart.getCartNum();
			dao.deleteCart(cartNum);
		}
		
		session.removeAttribute("orderInfo");
		session.removeAttribute("payInfo");
		
	}
	
	/* 결제성공 후 주문 테이블 담을 때 사용하는 주문번호 생성 메소드 */
	private String createOrderNum() {		// 주문번호 11자리 랜덤으로 생성(오늘날짜6자리+영어+숫자조합 ex-220310N2R4F)
		SimpleDateFormat sdf = new SimpleDateFormat("YYMMdd");
		sdf.format(new Date());
		
		Random random = new Random();
		String orderNum=sdf.format(new Date());
				
	  for(int i =0; i < 5; i++) { 
		  char c = (char)(random.nextInt(27)+'A');
		  if(i % 2 == 0) orderNum+=c;
		  else orderNum+=random.nextInt(10);
	  }
	  
	  return orderNum;
	}
	
	/* 주문내역 페이지 셋팅 */
	@Override
	public void orderHistory(Model model) {
		String id = (String)session.getAttribute("loginId");
		
		if(id == null) return;
		ArrayList<OrderDetailDTO> orderHistory = dao.selectOrderHistory(id);
		ArrayList<String> numList = dao.selectOrderNum(id);		// 해당 아이디가 주문했던 모든 주문번호 리스트
		
		if(numList == null) return;
		
		// 주문번호를 키로, 주문번호에 해당하는 내역들을 값으로 순서유지해서 담아감(날짜 내림차순) -> orderHistory페이지에서 iterator사용해서 반복출력
		LinkedHashMap<String,ArrayList<OrderDetailDTO>> result = new LinkedHashMap<>(); 

		for(String num : numList) {
			ArrayList<OrderDetailDTO> eachOrder = new ArrayList<OrderDetailDTO>();
			for(OrderDetailDTO dto : orderHistory) {
				if(dto.getOrderNum().equals(num)) {
					eachOrder.add(dto);
				}
			}
			result.put(num, eachOrder);
		}
		session.setAttribute("result", result);	
	}

	/* 주문내역 페이지에서 장바구니 담기 */
	@Override
	public String putCart(int productNum) {
		int stock = productDao.selectStock(productNum);
		if(stock <= 0) return "현재 재고가 없습니다.";
		else {
			String id = (String)session.getAttribute("loginId");
			if(cartDao.isExistCart(productNum, id) != null) return "이미 장바구니에 담긴 상품입니다.";
			
			int amount = 1;
			cartDao.insertCart(productNum, amount, id);
			return "장바구니 담기 완료";
		}
	}



	
	
	
	
	
	

}
