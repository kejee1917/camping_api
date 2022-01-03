package kr.co.gocamping.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.gocamping.vo.Qna;

@Mapper
@Repository
public interface QnaMapper {
	
	List<Qna> selectQnaData(Qna qna);
	
}
