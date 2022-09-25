

$(document).ready(function() {

let camera_button = document.querySelector("#start-camera");
let video = document.querySelector("#video");
let click_button = document.querySelector("#click-photo");
let canvas = document.querySelector("#canvas");

camera_button.addEventListener('click', async function() {
   	let stream = await navigator.mediaDevices.getUserMedia({ video: true, audio: false }).then()
	video.srcObject = stream;
});

click_button.addEventListener('click', function() {
	
	
	let width = 150, height = 125;
    let xAdjust = width * .001;
    let  yAdjust = height * .001;
	
   	canvas.getContext('2d').drawImage(video, 0 - xAdjust, 0 - yAdjust, width,height);
 	$('.validationPhotoSpan').attr("hidden","hidden");
   	$('#loanBy').val(canvas.toDataURL('image/jpeg'));
   
});


$(".newLoanSubmit").click(function(){	
		
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
		$('.validationWeight').val() != 0.0) && 
		$('#loanBy').val() !='';
		
		if(spanField && mandatoryFields){
		
		$('#loanForm').submit();
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
		
		else if($('#loanBy').val() === ''){
			$('.validationPhotoSpan').removeAttr("hidden");
			$('.validationPhotoSpan').focus();
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
	
	
	

    $('#interestSelected').on('change', function() {
	
		let optionSelected = $(this).find("option:selected");
     	let valueSelected  = optionSelected.val();
     	
     	if(valueSelected==="0" || $('#loanAmt').val()===''){
			console.log("interest Not selected");
			 $("#loanInterestCalc").val('');
			 $("#appraiserFee").val('');
			 $("#amountToGive").val('');
			
		}else{
			let loanAmt = $('#loanAmt').val();
			
			 let calInterest = Math.ceil((loanAmt * valueSelected)/100);
			 
			 let appraiserFee = Math.ceil((loanAmt * 0.5)/100);
			 
			 let amountToDetect = +calInterest + +appraiserFee;
			 
			 let amountToGive = +loanAmt - +amountToDetect;
			 
			 $("#loanInterestCalc").val(calInterest);
			 $("#appraiserFee").val(appraiserFee);
			 $("#amountToGive").val(amountToGive);
		}
	
	});
	
	 $('#loanAmt').on('change', function() {
	
		let optionSelected = $('#interestSelected').val();
     	if(optionSelected==="0" || $('#loanAmt').val()===''){
			console.log("interest Not selected");
			 $("#loanInterestCalc").val('');
			 $("#appraiserFee").val('');
			 $("#amountToGive").val('');
			
		}else{
			let loanAmt = $('#loanAmt').val();
			
			 let calInterest = Math.ceil((loanAmt * optionSelected)/100);
			 
			 let appraiserFee = Math.ceil((loanAmt * 0.5)/100);
			 
			 let amountToDetect = +calInterest + +appraiserFee;
			 
			 let amountToGive = +loanAmt - +amountToDetect;
			 
			 $("#loanInterestCalc").val(calInterest);
			 $("#appraiserFee").val(appraiserFee);
			 $("#amountToGive").val(amountToGive);
		}
	
	});
	
	$('#appraiserFee').on('change',function(){
		
     	let appraiserFee = $("#appraiserFee").val();
     	
		 let loanAmt = $('#loanAmt').val();
		 
		 let calInterest = $('#loanInterestCalc').val();
		 
		 let amountToDetect = +calInterest + +appraiserFee;
			 
		 let amountToGive = +loanAmt - +amountToDetect;
		 
		 $("#loanInterestCalc").val(calInterest);
		 $("#appraiserFee").val(appraiserFee);
		 $("#amountToGive").val(amountToGive);
		
	});
	
	$('#loanInterestCalc').on('change',function(){
		
		if($('#loanInterestCalc').val()==0)	{
			$("#appraiserFee").val(0);
		 	$("#amountToGive").val(0);
		}
		
	});
	
	$('#calcFromCurrentMonth').on('click', function() {
		
			if($('#calcFromCurrentMonth').is(':checked')){
			 $("#loanInterestCalc").val(0);
			 $("#appraiserFee").val(0);
			 $("#amountToGive").val(0);
		}
		else{
			let valueSelected = $('#interestSelected').val();
			let loanAmt = $('#loanAmt').val();
			
			 let calInterest = Math.ceil((loanAmt * valueSelected)/100);
			 
			 let appraiserFee = Math.ceil((loanAmt * 0.5)/100);
			 
			 let amountToDetect = +calInterest + +appraiserFee;
			 
			 let amountToGive = +loanAmt - +amountToDetect;
			 
			 $("#loanInterestCalc").val(calInterest);
			 $("#appraiserFee").val(appraiserFee);
			 $("#amountToGive").val(amountToGive);
		}
	
	});
   
});

$.fn.hasAttr = function(val) {
	return this.attr(val)!==undefined;
}
	
		



