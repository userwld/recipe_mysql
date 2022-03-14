package com.cooking.recipe.member.config;
	
public class PageConfig {	/* 페이징 처리 */
	public static int[] setPage(int totalCount, int currentPage) {		// 전체 데이터 갯수(보통 db에서 count(*)한 수로 넘어옴), 현재 페이지 
		int pageBlock = 10;					// 한페이지에 들어갈 갯수
		int end = currentPage * pageBlock;
		int begin = end + 1 - pageBlock;
	
		int[] page = {begin, end, pageBlock, totalCount};				// 앞에 begin,end는 db에서 rownum 지정시 사용(between이용) /  뒤에 pageBlock과 totalCount는 getNavi메소드 호출시 사용
		
		return page;
	}
	
	public static String getNavi(int currentPage, int pageBlock, int totalPage, String url) {
		int blockCnt = totalPage / pageBlock;
		if(totalPage % pageBlock > 0)
			blockCnt++;
		String result = "";
		if(currentPage != 1)
			result += "<a class='prev' href='"+url+(currentPage-1)+"'> [이전] </a>";
		
		for(int i=1;i<=blockCnt;i++) {
			if(currentPage == i) result += "<b>";						// 현재페이지 b태그 사용해서 진하게 표시
			result+="<a class='current' href='"+url+i+"'>"+i+"</a>";
			if(currentPage == i) result += "</b>";
		}
		if(currentPage != blockCnt)
			result += "<a class='next' href='"+url+(currentPage+1)+"'> [다음] </a>";
		return result;													// ex [이전] 1 2 [다음]
	}

}
