package kr.co.gocamping.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

import kr.co.gocamping.vo.Qna;

public class DataUtil {
	
	static public Map<String, List<String>> doNmData(){
		
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

	static public Map<String, List<String>> lctClData(){
		
		Map<String, List<String>> lctClMap = new HashMap<String, List<String>>();
		String[] lctClArray1 = {"산","숲"};
		String[] lctClArray2 = {"호수","강"};
		String[] lctClArray3 = {"해변"};
		
		lctClMap.put("1", Arrays.asList(lctClArray1));
		lctClMap.put("2", Arrays.asList(lctClArray2));
		lctClMap.put("3", Arrays.asList(lctClArray3));
		
		return lctClMap;
	}
	
	static public JSONArray selectCampingData() {
    	
	   	 StringBuffer result = new StringBuffer();
	   	 JSONArray jsonArray = new JSONArray();
	        try {
	            StringBuilder urlBuilder = new StringBuilder("http://api.visitkorea.or.kr/openapi/service/rest/GoCamping/basedList"); /*URL*/
	            urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=zzaOhY2a2Mjjjj%2F7z11%2BBg7I3eI6J7UZk3lDK5IeEH%2Br53qgVlMKiX38%2FzDcu0bIA8v9xOYDw9VdtQyuJdOCzg%3D%3D"); /*Service Key*/
	            urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
	            urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("2390", "UTF-8")); /*한 페이지 결과수*/
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
	
	static public List<Object> selectResultData(List<String> selectNoList, List<Qna> answerDataList){
		//가공 전 전체 data
		JSONArray jsonArray = selectCampingData();
		
		List<Object> jsonlist = new ArrayList<>();
		
		jsonlist = jsonArray.toList().stream()
		.filter(jsonobj -> {
			String doNm = (String)((Map)jsonobj).get("doNm");
			return DataUtil.doNmData().get(selectNoList.get(0)).stream().anyMatch(city -> doNm.contains(city));
		}).filter(jsonobj -> {
	    	String lctCl = (String)((Map)jsonobj).get("lctCl");
	    	return selectNoList.get(1).equals("4")
	    			?lctCl!=null && lctCl.length()>0
	    			:lctCl!=null && DataUtil.lctClData().get(selectNoList.get(1)).stream().anyMatch(env -> lctCl.contains(env));
	    }).filter(jsonobj -> {
	    	String induty = (String)((Map)jsonobj).get("induty");
	    	String answer = answerDataList.get(2).getAnswer().substring(0, 2);
	    	return induty!=null && induty.contains(answer);
	    }).filter(jsonobj -> {
	    	String sbrsCl = (String)((Map)jsonobj).get("sbrsCl");
	    	String answer = answerDataList.get(3).getAnswer();
	    	return selectNoList.get(3).equals("4")
	    			?sbrsCl!=null && sbrsCl.length()>0
	    			:sbrsCl!=null && sbrsCl.contains(answer);
	    }).filter(jsonobj -> {
	    	int toiletCo = (int)((Map)jsonobj).get("toiletCo");
	    	return selectNoList.get(4).equals("1")
	    			?toiletCo > 0
	    			:toiletCo >= 0;
	    }).filter(jsonobj -> {
	    	int swrmCo = (int)((Map)jsonobj).get("swrmCo");
	    	return selectNoList.get(5).equals("1")
	    			?swrmCo > 0
	    			:swrmCo >= 0;
	    }).filter(jsonobj -> {
	    	String animalCmgCl = (String)((Map)jsonobj).get("animalCmgCl");
	    	return selectNoList.get(6).equals("1")
	    			?animalCmgCl!= null && !animalCmgCl.equals("불가능")
	    			:animalCmgCl!= null;
	    }).limit(3)
		.collect(Collectors.toList());
		
		return jsonlist;
	}
	
//  list = jsonaArray.toList().stream()
//  .filter(jobj -> {
//  	String result3 = (String) ((Map)jobj).get("animalCmgCl");
//  	return result3!=null && !result3.equals("불가능");
//  	})
//  .filter(jobj -> {
//  	String result3 = (String) ((Map)jobj).get("lctCl");
//  	return result3!=null && result3.contains("숲");
//  	})
//  .map(jobj-> {
//  	Map temp =  ((Map)jobj);
//  	Map result3 = new HashMap();
//  	result3.put("facltNm", temp.get("facltNm"));
//  	result3.put("intro", temp.get("intro"));
//  	return result3;
//  }).limit(50)
//  .collect(Collectors.toList());
//  
	
	
	
//	.filter(jsonobj -> {
//	String result1 = (String)((Map)jsonobj).get("doNm");
//	return result1!=null 
//			&& (result1.contains("강원") 
//				|| result1.contains("서울") 
//				|| result1.contains("인천") );
//	})
//.filter(jobj -> {
//	String result1 = (String) ((Map)jobj).get("lctCl");
//	return result1!=null && result1.contains("");
//	})
//.filter(jobj -> {
//	String result1 = (String) ((Map)jobj).get("induty");
//	return result1!=null && result1.contains("");
//	})
//.filter(jobj -> {
//	String result1 = (String) ((Map)jobj).get("sbrsCl");
//	return result1!=null && result1.contains("");
//	})
//.filter(jobj -> {
//	String result1 = (String) ((Map)jobj).get("toiletCo");
//	return result1!=null && result1.contains("");
//	})
//.filter(jobj -> {
//	String result1 = (String) ((Map)jobj).get("swrmCo");
//	return result1!=null && result1.contains("");
//	})
//.filter(jobj -> {
//	String result1 = (String) ((Map)jobj).get("animalCmgCl");
//	return result1!=null && result1.contains("");
//	})
//.collect(Collectors.toList());	
}
