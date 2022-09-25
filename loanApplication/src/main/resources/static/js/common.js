let videoReturn;


$(document).ready(function() {
	
	
	 $(".date").datepicker({
                format: "dd/mm/yyyy"
            });
	
	
	$(".backDate").click(function(){
		
		$('#navigationId').val('back');
		
		$('#dailySheetForm').submit();
		
	});
	
	$(".forwardDate").click(function(){
		
		$('#navigationId').val('forward');
		
		$('#dailySheetForm').submit();
		
	});
	
	
	
	
	
	 //$('[data-toggle=tooltip]').tooltip();
		 $.ajax({
            type: "GET",
            url: "/typeAhead",
            success: function(responseData) {
			$("#place").typeahead({ source:responseData.place });
			$("#street").typeahead({ source:responseData.street });
            }
        });
        
          $("#firstName").keyup(function(){
			if($('#firstName').val()!=undefined && $('#firstName').val().length >= 2){
        		$("#firstName").typeahead({ source: typeAheadName($('#firstName').val())});
        	}
    	  });
    	  
    	   $("#lastName").keyup(function(){
			if($('#lastName').val()!=undefined && $('#lastName').val().length >= 2){
        		$("#lastName").typeahead({ source: typeAheadName($('#lastName').val())});
        	}
    	  });
    	  
    	  $(".dailyDate").change(function(){
				$('#dailySheetForm').submit();
			});
    	  
         
                
       
  	
    //$('[data-toggle=tooltip]').tooltip();
	$('#viewLoanGo').on('click',function(){
		 $.ajax({
            type: "GET",
            url: "/viewLoan/"+$('#loanNo').val(),
            success: function(responseData) {
				$("#loanResults").html("");
                $("#loanResults").append(responseData);
            }
        }) 
		
	});
	
	
	 $('#returnPlusIcon').on('click', function() {
        
        $('#loanNolist  tr:last').after('<tr><td><input class="form-control col-12" name="loanNo[]" maxlength="5" required /></td>'+
        '</tr>');
    });
    
    
    $(document).on('click', '.returnDeleteIcon', function() {
    $(this).closest('tr').remove();
	});
    
    $('#returnLoan').on('click', function() {
	
	var returnLoanList = $("input[name='loanNo[]']")
              .map(function(){return $(this).val().toUpperCase();}).get();
	$.ajax({
            type: "POST",
            url:"/calculateInt",
            contentType: "application/json",
            data: JSON.stringify(returnLoanList),
            success: function(data) {
				$("#returnLoanSummary").html("");
                $("#returnLoanSummary").append(data);
                
        }
       
	});
	});
	
	  $(document).on('click', '.deleteReturnLoan', function() {
    	$(this).closest('tr').remove();
			calcTotalSum();
			
});

});


function interestRtnChg(obj){
	 var loanAmt = Number($(obj).closest('tr').find('.loanAmt').text());
     var interestRtn = Number($(obj).closest('tr').find('.interestRtn').val()); 
     $(obj).closest('tr').find('.sum').text(loanAmt+interestRtn);
     calcTotalSum();
     
}


function calcTotalSum(){
	var totalSum = 0;
// For each item calculate the sum and increase the total sum
			$('.rowItem').each(function() {
				
			  var $this = $(this),
			      sum = Number($this.find('.sum').text());
			  		totalSum += sum;
			  		
			  		$('#onload').hide();
			  		$('#offload').show();
			  		
			  		$('#totalSumoffLoad').text(totalSum);
			});
}




  function viewLoan(val){
    let template = Handlebars.compile(this.fetchViewHTMLSrc(val));
    $('#loanStuff').html(template);
    $("#viewLoanModal").modal('show');
  }

  function fetchViewHTMLSrc(val){
	
	//'+
      //' </div>';
	
	let source = "<div>"
			+"<div class=\"float-child\">\n"
			+ "			 	<table>\n"
			+ "			 	<tr>\n"
			+ "			 		<td> Amount : </td>\n"
			+ "			 		<td> <b> "+ val.loanAmt +"</b> </td>\n"
			+ "			 	</tr>\n"
			+ "			 	<tr>\n"
			+ "			 		<td> Loan No : </td>\n"
			+ "			 		<td> <b> "+ val.loanNo +" </b> </td>\n"
			+ "			 	</tr>\n"
			+ "			 	<tr>\n"
			+ "			 		<td> Loan Date: </td>\n"
			+ "			 		<td> <b> "+ val.loanDateStr +" </b> </td>\n"
			+ "			 	</tr>\n"
			+ "			 	\n"
			+ "			 	<tr>\n"
			+ "			 		<td> InterestRate: </td>\n"
			+ "			 		<td> <b> "+ val.interestRate +" </b> </td>\n"
			+ "			 	</tr>\n"
			+ "			 	\n"
			+ "			 	<tr>\n"
			+ "			 		<td> Amount Given: </td>\n"
			+ "			 		<td> <b>  "+ val.amountGiven +" </b> </td>\n"
			+ "			 	</tr>\n"
			+ "			 	\n"
			+ "			 	<tr>\n"
			+ "			 		<td> Items: </td>\n"
			+ "			 		<td> <b> "+ val.items +" </b> </td>\n"
			+ "			 	</tr>\n"
			+ "			 	\n"
			+ "			 	<tr>\n"
			+ "			 		<td> Description: </td>\n"
			+ "			 		<td> <b> "+ val.description +" </b> </td>\n"
			+ "			 	</tr>\n"
			+ "			 	\n"
			+ "			 	<tr>\n"
			+ "			 		<td> Net Weight: </td>\n"
			+ "			 		<td> <b> "+ val.netWeight +" </b> </td>\n"
			+ "			 	</tr>\n"
			+ "			 	\n"
			
			+ "			 	<tr>\n"
			+ "			 		<td> Curr Month int: </td>\n"
			+ "			 		<td> <b> "+ val.calcFromCurrentMonth +" </b> </td>\n"
			+ "			 	</tr>\n"
			+ "			 	\n"
			+ "			 	<tr>\n"
			+ "			 		<td> AdditionalInfo: </td>\n"
			+ "			 		<td> <b> "+ val.additionalInfo +"  </b> </td>\n"
			+ "			 	</tr>\n"
			+ "			 	\n"
			+ " 			<tr>\n"
			+ "					 		<td> Returned Date: </td>\n"
			+ "					 		<td> <b> "+ val.returnDateStr +"  </b> </td>\n"
			+ "					 	</tr>\n"
			+ "			 	<tr>\n"
			+ "			 		<td> Retuner Info: </td>\n"
			+ "			 		<td> <b> "+ val.returnerInfo +"  </b> </td>\n"
			+ "			 	</tr>\n"
			+ "			 	\n"
			+ "			 	</table>\n"
			+ "				\n"
			+ "				\n"
			+ "			</div>\n"
			+ "			<div class=\"float-child\">\n"
			+ "			\n"
			+ "				<table>\n"
			+ "				\n"
			+ "				<tr>\n"
			+ "			 		<td> Fname: </td>\n"
			+ "			 		<td> <b> "+ val.fName +" </b> </td>\n"
			+ "			 	</tr>\n"
			+ "			 	\n"
			+ "			 	<tr>\n"
			+ "			 		<td> Lname: </td>\n"
			+ "			 		<td> <b> "+  val.relation +" "+val.lName +" </b> </td>\n"
			+ "			 	</tr>\n"
			+ "			 	\n"
			+ "			 	<tr>\n"
			+ "			 		<td> Place: </td>\n"
			+ "			 		<td> <b> "+ val.place +" </b> </td>\n"
			+ "			 	</tr>\n"
			+ "			 	\n"
			+ "			 	<tr>\n"
			+ "			 		<td> Street: </td>\n"
			+ "			 		<td> <b> "+ val.streetName +" </b> </td>\n"
			+ "			 	</tr>\n"
			+ "			 	\n"
			+ "			 	<tr>\n"
			+ "			 		<td> Mobile: </td>\n"
			+ "			 		<td> <b> "+ val.mobileNo +" </b> </td>\n"
			+ "			 	</tr>\n"
			+ "				</table>	\n"
			+ "				\n"
			+ "			</div>\n"
			+ "			<div class=\"float-child\">\n"
			+ "			\n"
			+ "				<table>\n"
			+ "				<tr>\n"
			+ "			 		<td> Loan By: </td>\n"
			+ "			 		<td> <img src="+ val.loanByImg+ "> </td>\n"
			+ "			 	</tr>\n"
			+ "             <br>\n"
			+ "              <tr>\n"
			+ "              <td> </td> \n"
			+ "              </tr>\n"
			+ " 			<tr>\n"
			+ "					 		<td> Returned By:  </td>\n"
			+ "					 		<td> <img src="+ val.returnByImg+ ">  </td>\n"
			+ "					 	</tr>\n"
			+ "				</table>\n"
			+ "			</div>\n"
			+ "			\n"
			+ "	</div>  ";
			
			
			
			

	
    
      
    return source;
  }
  
  
	
async function startCameraReturn()
{
	videoReturn = document.querySelector("#videoReturn");
		   	let stream = await navigator.mediaDevices.getUserMedia({ video: true, audio: false }).then()
			videoReturn.srcObject = stream;
}	

function clickPhotoReturn(){
	let canvasReturn = document.querySelector("#canvasReturn");
		let width = 150, height = 125;
		    let xAdjust = width * .001;
		    let  yAdjust = height * .001;
			
		   	canvasReturn.getContext('2d').drawImage(videoReturn, 0 - xAdjust, 0 - yAdjust, width,height);
		   
		 
			$('#returnBy').val(canvasReturn.toDataURL('image/jpeg'));
}


function returnSubmit(){
	
	
	let returnDetailsObj = [];
	let loanReturn = {};
   
    $("#returnTable tr").each(function(index){
	 if(index!==0){        
		var currentRow=$(this);
		
        var id = currentRow.find('td:eq(0)')[0].id.split("_");
        var interestAmt = currentRow.find("td:eq(2) input[type='text']").val();

        var returnDetails={};
        returnDetails.loanNo=id[0];
        returnDetails.actualIntAmt=id[1];
        returnDetails.interestAmt=interestAmt;
        returnDetailsObj.push(returnDetails);
       
        }
   });
   
   
   loanReturn.returnerInfo = $('#returnerInfo').val();
   loanReturn.returnByStr =  $('#returnBy').val();
   loanReturn.returnDetails = returnDetailsObj;
            
	$.ajax({
            type: "POST",
            url:"/returnSubmit",
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(loanReturn),
            dataType: "json",
            success: function(data) {
	
	$(".interestRtn").attr('disabled','disabled');
	$("#returnerInfo").attr('disabled','disabled');
	
	$(':button').prop('disabled', true);
	$('#returnMesage').removeAttr("style")				
                
        }
       
	});
	
}

function typeAheadName(input){
	
	let name= [];
			$.ajax({
            type: "GET",
            url: "/typeAheadName/"+input,
            success: function(responseData) {
				name = responseData;
            },
            async: false,
        });
        
        return name;
       } 
       
       
     function pageAction(pageNo) {
	$('#pageNo').val(pageNo);
	$('#searchForm').submit();
	
	
	
	
}


	
                


		
	

