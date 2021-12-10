package kr.co.gocamping.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("Camping")
public class CampingVo {
	
	private String doNm;		//도 이름
	private String lctCl;		//주변환경
	private String induty;		//캠핑종류
	private String sbrsCl;		//제공
	private String toiletCo;	//화장실 개수
	private String swrmCo;		//샤워장 개수
	private String animalCmgCl; //반려동물 동반(가능/불가능)
	
	private String facltNm;		//캠핑장 이름
	private String firstImageUrl; //대표이미지
	private String homepage; 	//홈페이지
	private String intro;		//소개글
	private String addr1;		
	private String addr2;
	private String mapX;		//경도
	private String mapY;		//위도
	private String tel;			//전화
	
}
