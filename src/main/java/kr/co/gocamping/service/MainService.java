package kr.co.gocamping.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import kr.co.gocamping.mapper.QnaMapper;
import kr.co.gocamping.util.DataUtil;
import kr.co.gocamping.vo.CampingVo;
import kr.co.gocamping.vo.Qna;
import kr.co.gocamping.vo.Results;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MainService {
	
	private final QnaMapper qnaMapper;
	
	List<Object> jsonlist = new ArrayList<>();
	
	public Results selectQnaData(Qna qna) {
		
		List<Qna> qnaList = qnaMapper.selectQnaData(qna);
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("qna", qnaList); 
		
		return Results.builder().data(data).success(true).build();
	}
	

	public Results selectResultData(Map<String, Qna> selectNoMap, String pageNo) {
		
		List<Object> resultlist = new ArrayList<>();
		// 맨 처음 결과 뿌려 줄 때
		if(pageNo == null) {
			pageNo = "1";
			
			jsonlist = DataUtil.selectResultData(selectNoMap);
			log.debug(jsonlist.size() + "");
		}
		
		resultlist= jsonlist.stream().skip(Integer.parseInt(pageNo)-1).collect(Collectors.toList());
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("totalCount", jsonlist.size()); 
		data.put("resultList", resultlist);
		
        return Results.builder().data(data).success(true).build();
	}
	

	
	


	

}
