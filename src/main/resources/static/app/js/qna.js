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
			
			let answer = $(".active #hidden-answer").val(); 
			
			$.ajax({
				url: "question",
				type: "get",
				dataType: "text",
				data: {'queNo':queNo, 'selectNo':selectNo, 'answer':answer},
				success: function(data) {
					let html = $('.main').html(data);
					let content = html.find("div.main").html();
					$('.main').html(content);
					
					if(queNo == "7") {
						$("#nextBtn").css("display", "none");
						$("#lastBtn").css("display", "block");
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
			let queNo = $("#hidden-queNo").val(); 
			let selectNo = $(".active").val();
			let answer = $(".active #hidden-answer").val(); 
			
			$.ajax({
				url: "result",
				type: "get",
				data: {'queNo':queNo, 'selectNo':selectNo, 'answer':answer},
				success: function(data) {
					if (data.success) {
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