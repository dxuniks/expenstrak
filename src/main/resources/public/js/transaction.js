$(function () {

	
	//DISPLAY ALL Transactions
    var $transactionTable = $('#transactionTable');

    //$transactionTable.append( "<tr class='header'><th>Date</th><th>Transaction</th><th>Category</th><th>Amount</th><th>Description</th><th>User</th><th>Action</td></tr>" );
    var transactionTemplate = $( "#transaction-template" ).html();
    

    $.ajax ( {
        type: 'GET',
        url: '/rest/transactions',
        success: function(payload) {
            var transactions=payload.data;
            $.each(transactions, function (i, transaction) {
            	addTransactionRow($transactionTable, transactionTemplate, transaction);
            });
            uiRefresh();
        },
    });
    
    
    buildCategoryOptionString($("#tabAdd .selectCategory"));
    buildAccountOptionString($("#tabAdd .selectAccount"));	
    buildUserOptionString($("#tabAdd .selectUser"));
    
    buildCategoryOptionString($("#tabSearch .selectCategory"));
    buildAccountOptionString($("#tabSearch .selectAccount"));	
    buildUserOptionString($("#tabSearch .selectUser"));    
    
    //ADD TRANSACTION
    $( "#btnAddTransaction" ).on("click", function() {
    	addTransaction();
    	uiRefresh();
    });
    
    //SEARCH Transactions
    $( "#btnSearchTransactions" ).on("click", function() {
    	searchTransactions(transactionTemplate);

    });    
    

    $( "#transactionTabs" ).tabs({
      activate: function( event, ui ) {
    	  drawCategoryTotalAmountPie($.datepicker.formatDate('yy/mm', new Date()));
    	  if ($('#transactionTabs').tabs('option', 'active') == 2) { 
  	    	$("#monthdatepicker").datepicker("setDate", new Date());
  	    	browseTransactions(transactionTemplate);
    	  } 
      }
    });
    
    //$("#tabBrowse").on("tabsactivate", function(event, ui) {
    	//alert("je");
   //});
    
   			


    
    
    //Browse Transaction
    $( "#btnBrowseTransactions" ).on("click", function() {
    	browseTransactions(transactionTemplate);
    });    
        
    
    
    //DELETE TRANSACTION
    $transactionTable.delegate (( ".deleteTransaction" ),"click", function () {
    	var $tr = $(this).closest('tr');
    
    	$.ajax({
    		type: 'DELETE',
    		url: '/rest/transactions/' + $(this).attr("transaction-id"),
    		success: function () {$tr.fadeOut(300, function (){
    				$(this).remove();
    				uiRefresh() ;
    			});
    		}
    	});
    	
    }); 
    
    //EDIT TRANSACTION
    $transactionTable.delegate (( ".editTransaction" ),"click", function () {
    	var $tr = $(this).closest("tr");
    	//$tr.find("input.date").val($tr.find("span.date").html());
    	$tr.find("input.date").datepicker({dateFormat: "yy-mm-dd",}).datepicker("setDate", $tr.find("span.date").html());
    	$tr.find("input.description").val($tr.find("span.description").html());
    	$tr.find("input.amount").val($tr.find("span.amount").html());
    	
    	//populate the drop down lists - get the latest
        var $selectCategory = $tr.find("#transactionEditCategoryList"); 	
        buildCategoryOptionString($selectCategory);
        var $selectAccount = $tr.find("#transactionEditAccountList"); 	
        buildAccountOptionString($selectAccount);     
        var $selectUser = $tr.find("#transactionEditUserList"); 	
        buildUserOptionString($selectUser);
        $tr.addClass("edit");
    });
 
    $transactionTable.delegate (( ".cancelEditTransaction" ),"click", function () {
    	var $tr = $(this).closest("tr").removeClass("edit");
    }); 
    
    $transactionTable.delegate (( ".saveEditTransaction" ),"click", function () {
    	var $tr = $(this).closest("tr");
    	var transDate = $tr.find("input.date").val();
    	var transaction = {
    			transactionDate: transDate,		
        		amount: 		$tr.find("input.amount").val(),
        		description: 	$tr.find("input.description").val(),
     	    	accountId : 	$tr.find("select.account").val(),
	    		categoryId : 	$tr.find("select.category").val(),
     	    	userId : 		$tr.find("select.user").val(),
        	};  

    	$.ajax({
    		type: "PUT",
    		url: "/rest/transactions/" + $(this).attr("transaction-id"),
    		data: JSON.stringify(transaction),
    	    dataType: 'json',
    	    contentType: "application/json; charset=utf-8",
    		success: function (payload) {
    			var transaction=payload.data[0];
    			$tr.find("span.date").html(transDate);
    			$tr.find("span.amount").html(transaction.amount);
    			$tr.find("span.description").html(transaction.description);
    			$tr.find("span.account").html(transaction.account.name);
    			$tr.find("select.account").attr("preValue", transaction.account.id);
    			$tr.find("span.category").html(transaction.category.name);
    			$tr.find("select.category").attr("preValue", transaction.category.id);
    			$tr.find("span.user").html(transaction.user.firstName + ' ' + transaction.user.lastName);
    			$tr.find("select.user").attr("preValue", transaction.user.id);
    			$tr.removeClass("edit");
    			uiRefresh() ;
    		},
    		error: function(xhr,err){
    		    alert("Error Updating User!\n" + xhr.responseText);
    		}
    	});
    	  	
    });     
 
});


//helper
//== build category dropdown list with select
function buildCategoryOptionString(select) {
	select.html("");
	var allValue=select.attr("allValue");
	if (allValue == "Yes") {
		select.append('<option value="-999">All Categories</option>');	        	
	}
	$.ajax ( {
		type: 'GET',
		url: '/rest/categories',
		success: function(payload) {
			var categories=payload.data;
			$.each(categories, function (i, category) {
				select.append('<option value="' + category.id + '" categoryName="' + category.name + 
						'" categoryDescription="' + category.description + '">' + category.name + '</option>');	        	
	        });
			var preValue = select.attr("preValue");
			if (typeof preValue !== typeof undefined && preValue !== false) {
				select.val(preValue);   	
			    select.change();
			}
	    },
		error: function(xhr,err){
		    alert("Error Retrievig Users!\n" + xhr.responseText);
		}	    
	});	
}

//== build account dropdown list with select
function buildAccountOptionString(select) {
	select.html("");
	var allValue=select.attr("allValue");
	if (allValue == "Yes") {
		select.append('<option value="-999">All Accounts</option>');	        	
	}
	$.ajax ( {
		type: 'GET',
		url: '/rest/accounts',
		success: function(payload) {
			var accounts=payload.data;
			$.each(accounts, function (i, account) {
				select.append('<option value="' + account.id + '" accountName="' + account.name + 
						'" accountDescription="' + account.description + '">' + account.name + '</option>');	        	
	        });
			var preValue = select.attr("preValue");
			if (typeof preValue !== typeof undefined && preValue !== false) {
				select.val(preValue);   	
			    select.change();
			}
	    },
		error: function(xhr,err){
		    alert("Error Retrievig Users!\n" + xhr.responseText);
		}	    
	});	
}

//== build user dropdown list with select
function buildUserOptionString(select) {
	var allValue=select.attr("allValue");
	select.html("");
	if (allValue == "Yes") {
		
		select.append('<option value="-999">All Users</option>');	        	
	}
	$.ajax ( {
		type: 'GET',
		url: '/rest/users',
		success: function(payload) {
			var users=payload.data;
			$.each(users, function (i, user) {
				select.append('<option value="' + user.id + '" userFirstName="' + user.firstName + 
						'" userLastName="' + user.lastName + '" userEmailAddress="' + user.emailAddress +
						'">' + user.firstName + ' ' + user.lastName + '</option>');	        	
	        });
			var preValue = select.attr("preValue");
			if (typeof preValue !== typeof undefined && preValue !== false) {
				select.val(preValue);   	
			    select.change();
			}
		    
	    },
		error: function(xhr,err){
		    alert("Error Retrievig Users!\n" + xhr.responseText);
		}	    
	});	
}

function addTransaction () {
	$div=$( "#tabAdd" );
	var $transactionTable = $('#transactionTable');
	var transDate = $div.find("#transdatepicker").val();
	var transaction = {
		amount: 			$div.find("#amount").val(),
		transactionDate: 	transDate,
		description: 		$div.find("#desctext").val(),
		categoryId:  		$div.find(".selectCategory").val(),
		accountId: 			$div.find(".selectAccount").val(),
		userId: 			$div.find(".selectUser").val(),	
	}
	
	var transactionTemplate = $( "#transaction-template" ).html();
	$.ajax({
		type: "POST",
		url: "/rest/transactions",
		data: JSON.stringify(transaction),
		dataType: "json",
		contentType: "application/json; charset=utf-8",
		success: function(payload) {
			var transaction=payload.data[0];
			transaction.transactionDate = transDate;
			addTransactionRowOnTop($transactionTable, transactionTemplate, transaction);
			$div.find("#amount").val("0.00");
			$div.find("#desctext").val("");
			$div.find(".selectCategory").val($div.find(".selectCategory option:first").val());
			$div.find(".selectAccount").val($div.find(".selectAccount option:first").val());
			$div.find(".selectUser").val($div.find(".selectUser option:first").val());
			$div.find("#transdatepicker").datepicker("setDate", new Date());
			uiRefresh() ;
		},
		error: function(xhr,err){
		    alert("Error Updating User!\n" + xhr.responseText);
		}
		
	});		
}

function searchTransactions(transactionTemplate) {
	var $search = $("#tabSearch");
	var searchParam = {
		startdate : $search.find("#startdatepicker").val(),
		enddate : $search.find("#enddatepicker").val(),
		category : $search.find(".selectCategory").val(),
		account : $search.find(".selectAccount").val(),
		user : $search.find(".selectUser").val()     		
	};
	var searchStr = jQuery.param(searchParam);
	$.ajax({
		type: "POST",
		url: "/rest/transactions/search?" + searchStr,
		//data: JSON.stringify(transaction),
		dataType: "json",
		contentType: "application/json; charset=utf-8",
		success: function(payload) {
		    var $transactionTable = $("#transactionTable");
		    //var transactionTemplate = $( "#transaction-template" ).html();			    
		    $("#transactionTable tr:gt(0)").remove();
		    //$transactionTable.append( "<tr class='header'><th>Date</th><th>Transaction</th><th>Category</th><th>Amount</th><th>Description</th><th>User</th><th>Action</td></tr>" );
	
		    
			var transactions=payload.data;			
            $.each(transactions, function (i, transaction) {
            	addTransactionRow($transactionTable, transactionTemplate, transaction);
            });
        	uiRefresh();
		},
		error: function(xhr,err){
		    alert("Error Searhing User!\n" + xhr.responseText);
		}
		
	});

};


function browseTransactions(transactionTemplate) {
	var $browse = $("#tabBrowse");
	var browseParam =  $browse.find("#monthdatepicker").val();

	$.ajax({
		type: "GET",
		url: "/rest/transactions/browse/" + browseParam,
		//data: JSON.stringify(transaction),
		dataType: "json",
		contentType: "application/json; charset=utf-8",
		success: function(payload) {
		    var $transactionTable = $("#transactionTable");
		    //var transactionTemplate = $( "#transaction-template" ).html();			    
		    $("#transactionTable tr:gt(0)").remove();
		    //$transactionTable.append( "<tr class='header'><th>Date</th><th>Transaction</th><th>Category</th><th>Amount</th><th>Description</th><th>User</th><th>Action</td></tr>" );
	
		    
			var transactions=payload.data;			
            $.each(transactions, function (i, transaction) {
            	addTransactionRow($transactionTable, transactionTemplate, transaction);
            });
        	uiRefresh();
        	drawCategoryTotalAmountPie(browseParam);
		},
		error: function(xhr,err){
		    alert("Error Searhing User!\n" + xhr.responseText);
		}
		
	});

};
function addTransactionRow(table, transactionTemplate, transaction){
	//table.append(Mustache.render(transactionTemplate, transaction));
	table.find("tr:last").after(Mustache.render(transactionTemplate, transaction));
}

function addTransactionRowOnTop(table, transactionTemplate, transaction){
	var newRow = Mustache.render(transactionTemplate, transaction);
	table.find("tr:first").after(newRow);
	//table.find(".header").after(newRow);
}


function setTwoNumberDecimal(event) {
	    this.value = parseFloat(this.value).toFixed(2);
}

function uiRefresh() {
	drawCategoryTotalAmountPie($.datepicker.formatDate('yy/mm', new Date()));
	$( "#transactionTabs" ).tabs();
    $( "#startdatepicker" ).datepicker({
 	  dateFormat: "yy-mm-dd",
    }).datepicker("setDate", -30);
    $( "#enddatepicker" ).datepicker({
 	  dateFormat: "yy-mm-dd",
    }).datepicker("setDate", new Date());  	      
    $( "#transdatepicker" ).datepicker({
 	  dateFormat: "yy-mm-dd",
    }).datepicker("setDate", new Date());
    $( "#monthdatepicker" ).datepicker( {
        changeMonth: true,
        changeYear: true,
        showButtonPanel: true,
        dateFormat: 'yy/mm',
        onClose: function(dateText, inst) { 
            $(this).datepicker('setDate', new Date(inst.selectedYear, inst.selectedMonth, 1));
        }
    });
    
    $( ".selectmenu" ).selectmenu();    
    $( ".button" ).button();
}

function drawCategoryTotalAmountPie(yearmonth) {	
	$.ajax ( {
		type: 'GET',
		url: '/rest/analysis/categorytotalamount/' + yearmonth,
		success: function(payload) {
			var categoryTotals = payload.data;
			var total = 0.0;
			if ( $.isArray(categoryTotals)  &&  categoryTotals.length > 0 ) {
				var displayData=[];
				$.each(categoryTotals, function (i, categoryTotal){
					//var item = [categoryTotal.categoryName + ' $' + categoryTotal.total.toString(), categoryTotal.total];
					var item = [categoryTotal.categoryName + ' $' + categoryTotal.total.toString(), categoryTotal.total];
					displayData.push(item);
					total=total+categoryTotal.total;
				});
			} else {
				displayData = [['No data $0.00', 1]];
			}
				$("#grandTotal").html("Grand Total For " + yearmonth + " : $" + total.toFixed(2).toString());
				var height = 400 + displayData.length *20;
				$("#monthCatgoryTotalPie").css('height', height.toString() + 'px' );
				$.jqplot('monthCatgoryTotalPie', [displayData],
					{ 
					    highlighter: {
					        show: true,
					        useAxesFormatters: false,
					        tooltipFormatString: '%s'
					      },

			      		seriesDefaults: {
			      			// Make this a pie chart.
			      			renderer: jQuery.jqplot.PieRenderer, 
			      			rendererOptions: {
			      				// Put data labels on the pie slices.
			      				// By default, labels show the percentage of the slice.
			      				
			      				showDataLabels: true,
			      				dataLabels : 'label',
			      				dataLabelFormatString: "%s"
			      			}
			      		}, 
			      		legend: { show:true, location: 's' }
					});
			},
		error: function(xhr,err){
		    alert("Error Retrievig Users!\n" + xhr.responseText);
		}	    
	});	
	
	
}