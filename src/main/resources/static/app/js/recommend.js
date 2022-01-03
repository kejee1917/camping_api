let selected = null;

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
		
		$("#shuffleBtn").off("click").on("click", function(e) {
			$recommend.shuffleFunc();
		});
		
		$(".name").attr("draggable", "true");
		
		$(".name").off("dragstart").on("dragstart", function() {
			$recommend.dragStart(event);
		});
		$(".name").off("dragover").on("dragover", function() {
			$recommend.dragOver(event);
		});
		$(".name").off("dragend").on("dragend", function() {
			$recommend.dragEnd();
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
		pageNo = clickNum.attr("data-value");
		location.href = "/gocamp/recommend?pageNo=" + pageNo;
	},
	
	shuffleFunc: function() {
		location.href = "/gocamp/recommend";
	},
	
	copyFunc: function() {
		let copyText = $("#addrText").text();
		alert(copyText);
		
	},
	
	
	
	//selected 마우스로 선택 된 라인
	dragStart: function (e) { 
		console.log("dragStart e>> " +e.target);
		e.dataTransfer.effectAllowed = 'move';
		e.dataTransfer.setData('text/plain', null);
		selected = e.target;
		console.log("dragStart selected>> " + selected);
	},
	
	dragOver : function(e) { 
		
		//위쪽으로 이동했을 때
		if (this.isBefore(selected, e.target)) { 
			if(e.target.className == "name") {
				e.target.parentNode.insertBefore(selected, e.target);   //부모노드의 e.target앞에 selected 넣는다.
				
			}
		} else { 
			if(e.target.className == "name") {
				console.log("다음");
				e.target.parentNode.insertBefore(selected, e.target.nextSibling); //부모노드의 e.target 다음 형제 앞에 selected 넣는다.
			}
		} 
		
	}, 
		
	dragEnd: function () {
		selected = null;
	},
	
	isBefore: function (el1, el2) { 
		let cur;
		
		if (el2.parentNode === el1.parentNode) { //형제면~
			console.log(el1.previousSibling);
			for (cur = el1.previousSibling; cur != null; cur = cur.previousSibling) { //타고타고 위로 반복 null일때까지
				
				console.log(cur);
				if (cur === el2) { //이전노드와 타겟이 같으면
					return true; 
				}
			} 
		} 
		return false; 
	},
	
};

$(function(){
	$recommend.init();
	
});