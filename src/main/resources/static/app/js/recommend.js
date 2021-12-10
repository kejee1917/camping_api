var $recommend = {
	
	/**
	 * 초기화
	 */
	init : function() {
		this.fnAddEventListener();
	},
	
	/**
	 * 이벤트 등록
	 */
	fnAddEventListener : function() {
		
		this.setResultForm();
		
		$("#retryBtn").off("click").on("click", function(e) {
			$recommend.firstSetForm();
		});
		
		$(".goPage").off("click").on("click", function(e) {
			$recommend.goPage( $(this) );
		});
		
	},	
	setResultForm: function() {
		
		if($("#recommend-result").length != 0) {
			
			let mapX = $("#map-x").val();
			let mapY = $("#map-y").val();
			let mapContainer = document.getElementById('map'), // 지도를 표시할 div
				mapOption = {
				center: new kakao.maps.LatLng(mapY, mapX), // 지도의 중심좌표
				level: 3 // 지도의 확대 레벨
			};
			// 지도를 표시할 div와 지도 옵션으로 지도를 생성합니다
			let map = new kakao.maps.Map(mapContainer, mapOption);
			
			// 마커가 표시될 위치입니다
			let markerPosition = new kakao.maps.LatLng(mapY, mapX);
			// 마커를 생성합니다
			let marker = new kakao.maps.Marker({
				position: markerPosition
			});
			// 마커가 지도 위에 표시되도록 설정합니다
			marker.setMap(map);
		}else {
			$("#map").remove();
		}
	},
	
	firstSetForm : function() {
		location.href = "/gocamp";
	},
	
	goPage : function(clickNum) {
		let pageNo = clickNum.attr("data-value");
		location.href = "/gocamp/recommend?pageNo=" + pageNo;

/*		$(".fa-circle").removeClass("clicked");
		$(this).addClass("clicked");*/
		
/*		$.ajax({
			url: "recommend",
			type: "get",
			data: { 'pageNo':pageNo },
			success: function(data) {
				
			},
			error: function() {
				console.log("error");
			},
			
		});	*/	
	}
	
	

};

$(function(){
	$recommend.init();
});