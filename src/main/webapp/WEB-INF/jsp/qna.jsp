<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>     
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>                     
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-3.5.1.min.js"></script>
<script type="text/javascript" src="/app/js/qna.js"></script>

<link rel="stylesheet" href="/css/main.css">
</head>
<body>

<div class="main">
	<div class="img-box">
		<img id="img" alt="" src="/images/camping_qna.png" width="100%" height="500px">
	</div>

	<div class="text-box">
		<div class="question-box">
			<c:if test="${empty results.data.qna}"> 
				<p>제공되는 문제가 없습니다.</p>
			</c:if>
			
			<c:forEach var="list" items="${results.data.qna}" varStatus="i" end="0">
				<p class="question">${list.queNo}. ${list.question}</p>
				<input type="hidden" id="hidden-queNo" name="queNo" value="${list.queNo}">
			</c:forEach>	
			
		</div>	
		
		<div class="answer-box">
			
			<c:forEach var="list" items="${results.data.qna}" varStatus="i">
				<button class="answer-eachbox" value="${list.ansNo}">
				${list.ansNo}. ${list.answer}
				<input type="hidden" id="hidden-answer" name="answer" value="${list.answer}">
				</button>
				
			</c:forEach>	
		</div>
	</div>
	
	<div class="btn-box">
		<button class="btn" id="nextBtn">다음으로</button>
		<button class="btn" id="lastBtn">내게 꼭 맞는 캠핑장은?</button>
	</div>
</div>



</body>
</html>