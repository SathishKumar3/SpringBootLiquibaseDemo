

$(document).ready(function() {



$(".editLoanSubmit").click(function(){	
		
		let spanField = ($('.validationAmtSpan').hasAttr('hidden') &&
		$('.validationFnameSpan').hasAttr('hidden') &&
		$('.validationLnameSpan').hasAttr('hidden') &&
		$('.validationPlaceSpan').hasAttr('hidden') &&
		$('.validationItemSpan').hasAttr('hidden') &&
		$('.validationWeightSpan').hasAttr('hidden') &&
		$('.validationInterestSpan').hasAttr('hidden'));
		
		let mandatoryFields = ($('.validationAmt').val() != 0 && 
		$('.validationFname').val()!='' && 
		$('.validationLname').length !='' && 
		$('.validationPlace').length !=''&& 
		$('.validationItem').length !='' && 
		$('.validationInterest').val() !=0 && 
		$('.validationWeight').val() != 0.0) 
		
		if(spanField && mandatoryFields){
		
		$('#editLoanForm').submit();
		}
		else{
					
		if($('.validationAmt').val() == 0){
			$('.validationAmtSpan').removeAttr("hidden");
			$('.validationAmt').focus();
		}
		
		else if($('.validationInterest').val() === '0'){
			$('.validationInterestSpan').removeAttr("hidden");
			$('.validationInterest').focus();
		}
		
		else if($('.validationFname').val() === ''){
			$('.validationFnameSpan').removeAttr("hidden");
			$('.validationFname').focus();
		}
		else if($('.validationLname').val() === ''){
			$('.validationLnameSpan').removeAttr("hidden");
			$('.validationLname').focus();
		}
		else if($('.validationPlace').val() === ''){
			$('.validationPlaceSpan').removeAttr("hidden");
			$('.validationPlace').focus();
		}
		
		else if($('.validationItem').val() === ''){
			$('.validationItemSpan').removeAttr("hidden");
			$('.validationItem').focus();
		}
		
		else if($('.validationWeight').val() == 0.0){
			$('.validationWeightSpan').removeAttr("hidden");
			$('.validationWeight').focus();
		}
		
		}
	});
	
 
 
    $('.validationAmt').on('blur', function(){
	  $('.validationAmtSpan').attr("hidden","hidden");
		if($(this).val() == undefined || $(this).val() == '' || $(this).val() == 0){ 
			$('.validationAmtSpan').removeAttr("hidden");
		}
});

	$('.validationFname').on('blur', function(){
		  $('.validationFnameSpan').attr("hidden","hidden");
			if($(this).val() == undefined || $(this).val() == '' || $(this).val() == 0){ 
				$('.validationFnameSpan').removeAttr("hidden");
			}
	});
	
	$('.validationLname').on('blur', function(){
		  $('.validationLnameSpan').attr("hidden","hidden");
			if($(this).val() == undefined || $(this).val() == '' || $(this).val() == 0){ 
				$('.validationLnameSpan').removeAttr("hidden");
			}
	});
	
	$('.validationPlace').on('blur', function(){
		  $('.validationPlaceSpan').attr("hidden","hidden");
			if($(this).val() == undefined || $(this).val() == '' || $(this).val() == 0){ 
				$('.validationPlaceSpan').removeAttr("hidden");
			}
	});
	
	$('.validationItem').on('blur', function(){
		  $('.validationItemSpan').attr("hidden","hidden");
			if($(this).val() == undefined || $(this).val() == '' || $(this).val() == 0){ 
				$('.validationItemSpan').removeAttr("hidden");
			}
	});
	
	$('.validationWeight').on('blur', function(){
		  $('.validationWeightSpan').attr("hidden","hidden");
			if($(this).val() == undefined || $(this).val() == '' || $(this).val() == 0){ 
				$('.validationWeightSpan').removeAttr("hidden");
			}
	});
	
	$('.validationInterest').on('blur', function(){
		  $('.validationInterestSpan').attr("hidden","hidden");
			if($(this).val() == undefined || $(this).val() == '0'){ 
				$('.validationInterestSpan').removeAttr("hidden");
			}
	});
	
	});
	
	
	
$.fn.hasAttr = function(val) {
	return this.attr(val)!==undefined;
}