package kr.co.gocamping.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    
	List<String> selectNoList = new ArrayList<String>();
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
    	
    	log.debug(">>>>" + qna.toString());
    	
//    	//초기 form
//    	if(qna.getQueNo() == null) { 
//    		//list 초기화
//    		selectList = new ArrayList<String>();
//    		qna.setQueNo("1");              
//    	}else if(qna.getQueNo().equals("8")) {
//    		selectList.add(qna.getSelectNo());
//    		log.debug(selectList.toString());
//    		return "redirect:/gocamp/result";
//    	}
    	
    	switch (qna.getQueNo()==null?"null":qna.getQueNo()) {
		case "null":
			//list 초기화
			selectNoList = new ArrayList<String>();
    		qna.setQueNo("1"); 
			break;
//		case "8":
//			selectNoList.add(qna.getSelectNo());
//    		log.debug(selectNoList.toString());
//    		return "redirect:/gocamp/result";
		default:
			selectNoList.add(qna.getSelectNo());
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
    @GetMapping("/result/{selectNo}")
    @ResponseBody
    public Results result(Model model, Qna qna, String pageNum, @PathVariable String selectNo) {
    	Results results = new Results();
    	selectNoList.add(selectNo);
    	log.debug("sel list>>>" + selectNoList.toString());
    	//log.debug("page>>>" + pageNum);
    	
//    	int pageFirst = 0;
//    	if(pageNum == null || pageNum.equals("1")) {
//    		pageFirst = 0;
//    	}else {
//    		pageFirst = Integer.parseInt( pageNum ) - 1;
//    	}
    	
    	if(selectNoList.size() == 7) {
    		results.setSuccess(true);
    	}else {
    		results.setSuccess(false);
    		results.setMessage("잠시 후 다시 시도해주십시오.");
    	}
    	
    	//Results results = mainService.selectResultData(selectNoList, pageFirst);
    	 
    	//model.addAttribute("results", results);
    	return results;
    }
    
    
    @GetMapping("/recommend")
    public String recommend(Model model, String pageNo) {
    	
    	log.debug(pageNo);
    	Results results = mainService.selectResultData(selectNoList, pageNo);
    	
    	model.addAttribute("results", results);
    	
    	return "recommend";
    }
    

}
