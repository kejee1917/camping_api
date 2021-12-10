package kr.co.gocamping.vo;

import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Alias("Qna")
@EqualsAndHashCode(callSuper=false)
public class Qna {
	
	private String no;
	private String queNo;
	private String ansNo;
	private String answer;
	private String regDt;
	
	private String question;
	
	private String selectNo;
	
}
