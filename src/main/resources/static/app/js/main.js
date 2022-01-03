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
	},
	
};

$(function(){
	$main.init();
});