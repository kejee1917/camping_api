<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>      
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-3.5.1.min.js"></script>
<!-- <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script> -->
<script type="text/javascript" src="/app/js/recommend.js"></script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=44ee4d40b15953be9dcbeb3e3e2fccea"></script>


<link rel="stylesheet" href="/css/main.css">
 <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.14.0/css/all.css" integrity="sha384-HzLeBuhoNPvSl5KYnjx0BT+WB0QEEqLprO+NBkkk5gbc67FTaL7XIGa2w1L0Xbgc" crossorigin="anonymous">

</head>
<body>

<div class="main">

			
	<div class="result-box">
		<div class="camp-info" align="center">
			<c:if test="${empty results.data.resultList}"> 
				<span id="no-recommend">조건에 맞는 캠핑장을 찾기 어려워요~
				<br><br>다시 한 번 도와드릴까요?
				</span>
			</c:if>
			
			
			<c:forEach var="list" items="${results.data.resultList}" varStatus="i" end="0">
				<div id="recommend-result">
					<br>
					<h1>당신과 찰떡인 캠핑장은?</h1>
					<img alt="" src="${list.firstImageUrl}" width="640px" height="426px">
					<ul id="recommend-list">
						<li class="name" >캠핑장 이름 <span>${list.facltNm}</span></li>
						<li class="name" >자연환경 <span>${list.lctCl}</span></li>
						<li class="name" >캠핑장 유형 <span>${list.induty}</span></li>
						<li class="name" >주요 시설 <span>${list.sbrsCl}</span></li>
						<li class="name" >화장실 개수 <span>${list.toiletCo} 개</span></li>
						<li class="name" >샤워장 개수 <span>${list.swrmCo} 개</span></li>
						<li class="name" >반려동물 동반여부 <span>${list.animalCmgCl}</span></li>
						<li class="name" >주소 <span>${list.addr1} ${list.addr2}</span></li>
						<li class="name" >전화 <span>${list.tel}</span></li>
					</ul>
				</div>
				<input type="hidden" id="map-x" value="${list.mapX}">
				<input type="hidden" id="map-y" value="${list.mapY}">			
			</c:forEach>	
		</div>
		
		
		<div id="map"></div>

		
		<div class="paging">
			<c:forEach var="pageNum" begin="1" end="${results.data.totalCount}" varStatus="i">
				<a href="#" class="goPage" data-value="${pageNum }"><i class="fas fa-circle"></i></a>
			</c:forEach>
		</div>
	</div>
	
	<div class="btn-box" style="padding-top: 2%">
		<button class="btn" id="retryBtn">다시 해볼게요!</button>
		<!-- <button class="btn" id="shuffleBtn">마음에 안들어요!</button> -->
	</div>
	
</div>



</body>                   
</html>