package kr.co.gocamping.controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.gocamping.service.MainService;
import kr.co.gocamping.vo.Qna;
import kr.co.gocamping.vo.Results;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/gocamp")
@RequiredArgsConstructor
public class MainController {
	
	private final MainService mainService;
	
	Map<String, Qna> selectNoMap = new HashMap<String, Qna>();
	
    /**
     *  메인
     *
     * @param model
     * @param param
     * @return
     */
    @GetMapping( value={"/",""} )
    public String main(Model model) {
            
        return "main";
    }
    
    /**
     *  질답
     *
     * @param model
     * @param qna
     * @return
     */
    @GetMapping("/question")
    public String question(Model model, Qna qna) {
    	
    	switch (qna.getQueNo()==null?"null":qna.getQueNo()) {
		case "null":
			//초기화
			selectNoMap = new HashMap<String, Qna>();
    		qna.setQueNo("1"); 
			break;
		default:
			int queNo = Integer.parseInt(qna.getQueNo());
			queNo = queNo - 1;
			selectNoMap.put(queNo + "", qna);
			break;
		}
    	
    	Results results  = mainService.selectQnaData(qna);
    	
    	model.addAttribute("results", results);
    	return "qna";
    }
    
    /**
     *  결과
     *
     * @param model
     * @param qna
     * @return
     */
    @GetMapping("/result")
    @ResponseBody
    public Results result(Model model, Qna qna, String pageNum) {
    	
    	Results results = new Results();
    	selectNoMap.put(qna.getQueNo(), qna);
    	log.debug("sel map>>>" + selectNoMap.toString());
    	
    	if(selectNoMap.size() == 7) {
    		results.setSuccess(true);
    	}else {
    		results.setSuccess(false);
    		results.setMessage("잠시 후 다시 시도해주십시오.");
    	}
    	
    	return results;
    }
    
    
    @GetMapping("/recommend")
    public String recommend(Model model, String pageNo) {
    	
    	log.debug(pageNo);
    	Results results = mainService.selectResultData(selectNoMap, pageNo);
    	
    	model.addAttribute("results", results);
    	
    	return "recommend";
    }
    

}
