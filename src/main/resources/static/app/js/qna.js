var $qna = {
	
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
		
		$("#nextBtn").off("click").on("click", function(e) {
			$qna.nextSetForm();
		});
		
		$("#lastBtn").off("click").on("click", function() {
			$qna.resultSetForm();
		});
		
		$(".answer-eachbox").off("click").on("click", function() {
			//console.log(this.value);
			//$(this).css("background", "red");
			$(".answer-eachbox").removeClass("active");
			$(this).addClass("active");

		});
	},	
	
	nextSetForm : function() {
		
		if($(".active").val() == null) {
			alert("질문에 대한 답을 선택해주세요.");
			
		}else {
			let queNo = $("#hidden-queNo").val(); 
			let selectNo = $(".active").val();
			queNo = parseInt(queNo) + 1;

			$.ajax({
				url: "question",
				type: "get",
				dataType: "text",
				data: {'queNo':queNo, 'selectNo':selectNo},
				success: function(data) {
					let html = $('.main').html(data);
					let content = html.find("div.main").html();
					$('.main').html(content);
					
					if(queNo == "7") {
						$("#nextBtn").css("display", "none");
						$("#lastBtn").css("display", "block");
						//$("#nextBtn").text("내게 꼭 맞는 캠핑장은?");
					}
					
					
				},
				error: function() {
					console.log("error");
				},
				
			});
		}

	},
	
	resultSetForm : function() {
		if($(".active").val() == null) {
			alert("질문에 대한 답을 선택해주세요.");
			
		}else {
			let selectNo = $(".active").val();
			$.ajax({
				url: "result/" + selectNo,
				type: "get",
				//data: {'selectNo':selectNo},
				success: function(data) {
					if (data.success) {
					   /* $("body").hide();
				        $("body").fadeIn(1000);  // 1초 뒤에 사라 지자 
				     
				        setTimeout(function(){$("body").fadeOut(1000);},1000);
				        setTimeout(function(){location.href= "/gocamp/recommend?pageNum=0"},3000);*/
						location.href= "/gocamp/recommend";
					} else {
						alert(data.message);
					}
				},
				error: function() {
					console.log("error");
				},
			});	
		}	
	},
	
	

};

$(function(){
	$qna.init();
});