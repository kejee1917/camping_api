var $main = {
	
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
		
		$("#startBtn").off("click").on("click", function() {
			$main.startSetForm();
		});
	},	
	
	startSetForm : function() {
		location.href = "/gocamp/question";
		
/*			$.ajax({
			url: "question",
			type: "get",
			dataType: "text",
			success: function(data) {
				let html = $('<div>').html(data);
				let content = html.find("div.main").html();
				$('.main').html(content);
				
			},
			error: function() {
				console.log("error");
			},
		});*/
	}
};

$(function(){
	$main.init();
});