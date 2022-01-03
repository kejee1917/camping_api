package kr.co.gocamping.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

import kr.co.gocamping.vo.Qna;

public class DataUtil {
	
	private static Map<String, List<String>> doNmData(){
		
		
		Map<String, List<String>> doNmMap = new HashMap<String, List<String>>();
		String[] doNmArray1 = {"경기","서울","인천"};
		String[] doNmArray2 = {"강원"};
		String[] doNmArray3 = {"전라"};
		String[] doNmArray4 = {"경상"};
		String[] doNmArray5 = {"충청"};
		String[] doNmArray6 = {"제주"};
		
		doNmMap.put("1", Arrays.asList(doNmArray1));
		doNmMap.put("2", Arrays.asList(doNmArray2));
		doNmMap.put("3", Arrays.asList(doNmArray3));
		doNmMap.put("4", Arrays.asList(doNmArray4));
		doNmMap.put("5", Arrays.asList(doNmArray5));
		doNmMap.put("6", Arrays.asList(doNmArray6));
		
		return doNmMap;
	}

	private static Map<String, List<String>> lctClData(){
		
		Map<String, List<String>> lctClMap = new HashMap<String, List<String>>();
		String[] lctClArray1 = {"산","숲"};
		String[] lctClArray2 = {"호수","강"};
		String[] lctClArray3 = {"해변"};
		
		lctClMap.put("1", Arrays.asList(lctClArray1));
		lctClMap.put("2", Arrays.asList(lctClArray2));
		lctClMap.put("3", Arrays.asList(lctClArray3));
		
		return lctClMap;
	}
	
	private static JSONArray selectCampingData() {
    	
	   	 StringBuffer result = new StringBuffer();
	   	 JSONArray jsonArray = new JSONArray();
	        try {
	            StringBuilder urlBuilder = new StringBuilder("http://api.visitkorea.or.kr/openapi/service/rest/GoCamping/basedList"); /*URL*/
	            urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=zzaOhY2a2Mjjjj%2F7z11%2BBg7I3eI6J7UZk3lDK5IeEH%2Br53qgVlMKiX38%2FzDcu0bIA8v9xOYDw9VdtQyuJdOCzg%3D%3D"); /*Service Key*/
	            urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
	            //urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("2390", "UTF-8")); /*한 페이지 결과수*/
	            urlBuilder.append("&" + URLEncoder.encode("MobileOS","UTF-8") + "=" + URLEncoder.encode("ETC", "UTF-8")); /*IOS(아이폰),AND(안드로이드),WIN(윈도우폰),ETC*/
	            urlBuilder.append("&" + URLEncoder.encode("MobileApp","UTF-8") + "=" + URLEncoder.encode("AppTest", "UTF-8")); /*서비스명=어플명*/
	            urlBuilder.append("&_type=json"); /*결과 json 포맷*/
	            URL url = new URL(urlBuilder.toString());
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            conn.setRequestMethod("GET");
	            BufferedReader rd;
	            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
	                rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
	            } else {
	                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
	            }
	            String line;
	            while ((line = rd.readLine()) != null) {
	                result.append(line + "\n");
	            }
	            rd.close();
	            conn.disconnect();
	
	            jsonArray = new JSONObject(result.toString()).getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONArray("item");
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return jsonArray;
  }
	
	public static List<Object> selectResultData(Map<String, Qna> selectNoMap){
		//가공 전 전체 data
		JSONArray jsonArray = selectCampingData();
		
		List<Object> jsonlist = new ArrayList<>();
		
		//외부 변수를 내부에서 사용할 때 상수로 선언해야만 함.
		final Predicate<Object>[] PRED_ARR = predIntegrate(selectNoMap);
		
		jsonlist = jsonArray.toList().stream()
		.filter(jsonobj -> {
			return Arrays.stream(PRED_ARR).allMatch( pred-> pred.test(jsonobj));
		})
		.collect(Collectors.toList());
		
		//shuffle
		Collections.shuffle(jsonlist);
		if(jsonlist != null) {
			jsonlist = jsonlist.stream().limit(3).collect(Collectors.toList());
		}
		
		return jsonlist;
	}
	
	private static Predicate<Object>[] predIntegrate(Map<String, Qna> selectNoMap) {
		Predicate<Object>[] predArr = new Predicate[7];
		 
		predArr[0] = predDoNm(selectNoMap);
		predArr[1] = predLctCl(selectNoMap);
		predArr[2] = predInduty(selectNoMap);
		predArr[3] = predSbrsCl(selectNoMap);
		predArr[4] = predToiletCo(selectNoMap);
		predArr[5] = predSwrmCo(selectNoMap);
		predArr[6] = predAnimalCmgCl(selectNoMap);;
		
		return predArr;
	}
	
	
	private static Predicate<Object> predDoNm(Map<String, Qna> selectNoMap) {
		return jsonobj -> {
			String doNm = (String)((Map)jsonobj).get("doNm");
			return doNmData().get(selectNoMap.get("1").getSelectNo()).stream().anyMatch(city -> doNm.contains(city));
		};
	}
	
	private static Predicate<Object> predLctCl(Map<String, Qna> selectNoMap) {
		return jsonobj -> {
	    	String lctCl = (String)((Map)jsonobj).get("lctCl");
	    	return selectNoMap.get("2").getSelectNo().equals("4")
	    			?lctCl!=null && lctCl.length()>0
	    			:lctCl!=null && lctClData().get(selectNoMap.get("2").getSelectNo()).stream().anyMatch(env -> lctCl.contains(env));
		};
	}
	
	private static Predicate<Object> predInduty(Map<String, Qna> selectNoMap) {
		return jsonobj -> {
	    	String induty = (String)((Map)jsonobj).get("induty");
	    	String answer = selectNoMap.get("3").getAnswer().substring(0, 2);
	    	return induty!=null && induty.contains(answer);
		};	
	}
	
	private static Predicate<Object> predSbrsCl(Map<String, Qna> selectNoMap) {
		return jsonobj -> {
	    	String sbrsCl = (String)((Map)jsonobj).get("sbrsCl");
	    	String answer = selectNoMap.get("4").getAnswer();
	    	return selectNoMap.get("4").getSelectNo().equals("4")
	    			?sbrsCl!=null && sbrsCl.length()>0
	    			:sbrsCl!=null && sbrsCl.contains(answer);
		};				
	}
	
	
	private static Predicate<Object> predToiletCo(Map<String, Qna> selectNoMap) {
		return jsonobj -> {
	    	int toiletCo = (int)((Map)jsonobj).get("toiletCo");
	    	return selectNoMap.get("5").getSelectNo().equals("1")
	    			?toiletCo > 0
	    			:toiletCo >= 0;
		};			
	}
	
	private static Predicate<Object> predSwrmCo(Map<String, Qna> selectNoMap) {
		return jsonobj -> {
	    	int swrmCo = (int)((Map)jsonobj).get("swrmCo");
	    	return selectNoMap.get("6").getSelectNo().equals("1")
	    			?swrmCo > 0
	    			:swrmCo >= 0;
		};			
	}
	
	private static Predicate<Object> predAnimalCmgCl(Map<String, Qna> selectNoMap) {
		return jsonobj -> {
	    	String animalCmgCl = (String)((Map)jsonobj).get("animalCmgCl");
	    	return selectNoMap.get("7").getSelectNo().equals("1")
	    			?animalCmgCl!= null && !animalCmgCl.equals("불가능")
	    			:animalCmgCl!= null;
		};			
	}

}
