$(function () {
    $( ".button" ).button();
    
    //CATEGORY BLOCK
    //DISPLAY ALL CATEGORYS
    var $categoryTable = $('#categoryTable');

    $categoryTable.append( "<tr><th>Name</th><th>Description</th><th>action</th></tr>" );
    var categoryTemplate = $( "#category-template" ).html();
    
    function addCategoryRow(category){
    	$categoryTable.append(Mustache.render(categoryTemplate, category));
    }

    $.ajax ( {
        type: 'GET',
        url: '/rest/categories',
        success: function(payload) {
            var categories=payload.data;
            $.each(categories, function (i, category) {
            	addCategoryRow(category);
            });
        },
    });
    
    //ADD category
    $( "#btnAddCategoryDlg" ).on("click" , function () {
    	$("#dlgAddCategory").dialog("open");
    });
    
    $( "#btnAddCategory" ).on("click", function() {
    	$div = $("#dlgAddCategory");
 	    var category = {
    		name: $div.find("#name").val(),
    		description: $div.find("#description").val()
    	};
    
    	$.ajax({
    		type: "POST",
    		url: "/rest/categories",
    		data: JSON.stringify(category),
    	    dataType: 'json',
    	    contentType: "application/json; charset=utf-8",
    		success: function (payload) {
                var categorys=payload.data;
                $.each(categorys, function (i, category) {
                	addCategoryRow(category);
                });  						
    		},
    		error: function() {
    			alert ("error adding category");
    		}
    		
    	});
    	$("#dlgAddCategory").dialog("close");
    });
    
    
    //DELETE category
    $categoryTable.delegate (( ".deleteCategory" ),"click", function () {
    	var $tr = $(this).closest('tr');
    
    	$.ajax({
    		type: 'DELETE',
    		url: '/rest/categories/' + $(this).attr("category-id"),
    		success: function () {$tr.fadeOut(300, function (){
    				$(this).remove();
    			});
    		}
    	});
    	
    }); 
    
    //EDIT category
    $categoryTable.delegate (( ".editCategory" ),"click", function () {
    	var $tr = $(this).closest("tr");
    	$tr.find("input.name").val($tr.find("span.name").html());
    	$tr.find("input.description").val($tr.find("span.description").html());
    	$tr.addClass("edit");
    });
    
    $categoryTable.delegate (( ".cancelEditCategory" ),"click", function () {
    	var $tr = $(this).closest("tr").removeClass("edit");
    });    
 
    $categoryTable.delegate (( ".saveEditCategory" ),"click", function () {
    	var $tr = $(this).closest("tr");
    	var category = {
        		name: $tr.find("input.name").val(),
        		description: $tr.find("input.description").val(),
        	};  
    	
    	$.ajax({
    		type: "PUT",
    		url: "/rest/categories/" + $(this).attr("category-id"),
    		data: JSON.stringify(category),
    	    dataType: 'json',
    	    contentType: "application/json; charset=utf-8",
    		success: function (payload) {
    			var category=payload.data[0];
    			$tr.find("span.name").html(category.name);
    			$tr.find("span.description").html(category.description);
    			$tr.removeClass("edit");
    		},
    		error: function(xhr,err){
    		    alert("Error Updating category!\n" + xhr.responseText);
    		}
    	});
    });  
   
    
    //===============================================USER BLOCK======================================
    //DISPLAY ALL USERS
    var $userTable = $('#userTable');

    $userTable.append( "<tr><th>First Name</th><th>Last Name</th><th>Email</th><th>action</th></tr>" );
    var userTemplate = $( "#user-template" ).html();
    
    function addUserRow(user){
    	$userTable.append(Mustache.render(userTemplate, user));
    }

    $.ajax ( {
        type: 'GET',
        url: '/rest/users',
        success: function(payload) {
            var users=payload.data;
            $.each(users, function (i, user) {
            	addUserRow(user);
            });
        },
    });
    
    //ADD USER
    $( "#btnAddUserDlg" ).on("click" , function () {
    	$("#dlgAddUser").dialog("open");
    });
    
    $( "#btnAddUser" ).on("click", function() {
 	    var user = {
    		firstName: $("#firstName").val(),
    		lastName: $("#lastName").val(),
    		emailAddress: $("#emailAddress").val(),
    	};
    
    	$.ajax({
    		type: "POST",
    		url: "/rest/users",
    		data: JSON.stringify(user),
    	    dataType: 'json',
    	    contentType: "application/json; charset=utf-8",
    		success: function (payload) {
                var users=payload.data;
                $.each(users, function (i, user) {
                	addUserRow(user);
                });  						
    		},
    		error: function() {
    			alert ("error adding user");
    		}
    		
    	});
    	$("#dlgAddUser").dialog("close");
    });
    
    
    //DELETE USER
    $userTable.delegate (( ".deleteUser" ),"click", function () {
    	var $tr = $(this).closest('tr');
    
    	$.ajax({
    		type: 'DELETE',
    		url: '/rest/users/' + $(this).attr("user-id"),
    		success: function () {$tr.fadeOut(300, function (){
    				$(this).remove();
    			});
    		}
    	});
    	
    }); 
    
    //EDIT USER
    $userTable.delegate (( ".editUser" ),"click", function () {
    	var $tr = $(this).closest("tr");
    	$tr.find("input.firstName").val($tr.find("span.firstName").html());
    	$tr.find("input.lastName").val($tr.find("span.lastName").html());
    	$tr.find("input.emailAddress").val($tr.find("span.emailAddress").html());
    	$tr.addClass("edit");
    });
    
    $userTable.delegate (( ".cancelEditUser" ),"click", function () {
    	var $tr = $(this).closest("tr").removeClass("edit");
    });    
 
    $userTable.delegate (( ".saveEditUser" ),"click", function () {
    	var $tr = $(this).closest("tr");
    	var user = {
        		firstName: $tr.find("input.firstName").val(),
        		lastName: $tr.find("input.lastName").val(),
        		emailAddress: $tr.find("input.emailAddress").val(),
        	};  
    	
    	$.ajax({
    		type: "PUT",
    		url: "/rest/users/" + $(this).attr("user-id"),
    		data: JSON.stringify(user),
    	    dataType: 'json',
    	    contentType: "application/json; charset=utf-8",
    		success: function (payload) {
    			var user=payload.data[0];
    			$tr.find("span.firstName").html(user.firstName);
    			$tr.find("span.lastName").html(user.lastName);
    			$tr.find("span.emailAddress").html(user.emailAddress);
    			$tr.removeClass("edit");
    		},
    		error: function(xhr,err){
    		    alert("Error Updating User!\n" + xhr.responseText);
    		}
    	});
    });  
    
    //-------------------------------Accounts -------------------------------

    //DISPLAY ALL Accounts
    var $accountTable = $('#accountTable');

    $accountTable.append( "<tr><th>Name</th><th>Description</th><th>User Name</th><th>action</th></tr>" );
    var accountTemplate = $( "#account-template" ).html();
    
    function addAccountRow(account){
    	$accountTable.append(Mustache.render(accountTemplate, account));
    }

    $.ajax ( {
        type: 'GET',
        url: '/rest/accounts',
        success: function(payload) {
            var accounts=payload.data;
            $.each(accounts, function (i, account) {
            	addAccountRow(account);
            });
        },
    });
    
    var $userDropdown= $("#dlgAddAccount").find("select");
    var userDropdownTemplate = $( "#user-dropdown-template" ).html();
    function addUserDropdown(user) {
    	$userDropdown.append(Mustache.render(userDropdownTemplate, user));
    }
    $.ajax ( {
        type: 'GET',
        url: '/rest/users',
        success: function(payload) {
            var users=payload.data;
            $.each(users, function (i, user) {
            	addUserDropdown(user);
            });
        },
    });
    
    //ADD Account
    $( "#btnAddAccoutDlg" ).on("click" , function () {
    	$("#dlgAddAccount").dialog("open");
    });
    
    $( "#btnAddAccount" ).on("click", function() {
    	$div = $("#dlgAddAccount");
 	    var account = {
 	    	name: $div.find("#name").val(),
 	    	description: $div.find("#description").val(),
 	    	flowBalance: "0.00",
 	    	user : {
 	    		id: $div.find("#userList").val(),
 	    		firstName: $div.find("#userList option:selected", this).attr("user-firstname"),
 	    		lastName: $div.find("#userList option:selected", this).attr("user-lastName"),
 	    		emailAddress: $div.find("#userList option:selected", this).attr("user-emailAddress"),
 	    	}
    	};
 
    	$.ajax({
    		type: "POST",
    		url: "/rest/accounts",
    		data: JSON.stringify(account),
    	    dataType: 'json',
    	    contentType: "application/json; charset=utf-8",
    		success: function (payload) {
                var accounts=payload.data;
                $.each(accounts, function (i, account) {
                	addAccountRow(account);
                });  						
    		},
    		error: function() {
    			alert ("error adding account");
    		}
    		
    	});
    	$("#dlgAddAccount").dialog("close");
    });
    
    //DELETE ACCOUNT
    $accountTable.delegate (( ".deleteAccount" ),"click", function () {
    	var $tr = $(this).closest('tr');
    
    	$.ajax({
    		type: 'DELETE',
    		url: '/rest/accounts/' + $(this).attr("account-id"),
    		success: function () {$tr.fadeOut(300, function (){
    				$(this).remove();
    			});
    		}
    	});
    	
    }); 
    
    //EDIT ACCOUNT
    $accountTable.delegate (( ".editAccount" ),"click", function () {
    	var $tr = $(this).closest("tr");
    	$tr.find("input.name").val($tr.find("span.name").html());
    	$tr.find("input.description").val($tr.find("span.description").html());
    	//populate the drop down list - get the latest
 
        var $select = $tr.find("#accountEditUserList"); 	
        buildUserOptionString($select);
        $tr.addClass("edit");
    });
    
    $accountTable.delegate (( ".cancelEditAccount" ),"click", function () {
    	var $tr = $(this).closest("tr").removeClass("edit");
    });    
 
    $accountTable.delegate (( ".saveEditAccount" ),"click", function () {
    	var $tr = $(this).closest("tr");
    	var account = {
        		name: $tr.find("input.name").val(),
        		description: $tr.find("input.description").val(),
     	    	flowBalance: "0.00",
     	    	user : {
     	    		id: $tr.find("#accountEditUserList").val(),
     	    		firstName: $tr.find("#accountEditUserList option:selected", this).attr("user-firstname"),
     	    		lastName: $tr.find("#accountEditUserList option:selected", this).attr("user-lastName"),
     	    		emailAddress: $tr.find("#accountEditUserList option:selected", this).attr("user-emailAddress"),
     	    	}
        	};  
    	//console.log (account);
    	$.ajax({
    		type: "PUT",
    		url: "/rest/accounts/" + $(this).attr("account-id"),
    		data: JSON.stringify(account),
    	    dataType: 'json',
    	    contentType: "application/json; charset=utf-8",
    		success: function (payload) {
    			var account=payload.data[0];
    			$tr.find("span.name").html(account.name);
    			$tr.find("span.description").html(account.description);
    			$tr.find("span.user").html(account.user.firstName + ' ' + account.user.lastName);
    			$tr.find("#accountEditUserList").attr("pre-value", account.user.id);
    			$tr.removeClass("edit");
    		},
    		error: function(xhr,err){
    		    alert("Error Updating User!\n" + xhr.responseText);
    		}
    	});
    });  
        

    
    //===============================================================
    
    //add things
	$("#dlgAddCategory").dialog( {
		autoOpen  :   false,
		model     :   true,
		show      :   "blind",
		hide      :   "blind"
	});

    $("#dlgAddAccount").dialog( {
        autoOpen  :   false,
        model     :   true,
        show      :   "blind",
        hide      :   "blind"
    });
    
    $("#dlgAddUser").dialog( {
        autoOpen  :   false,
        model     :   true,
        show      :   "fade",
        hide      :   "fade"
    });
       
   // $( "#btnAddAccount" ).click(function () {addAccount()});
    //$( "#btnAddCategory" ).click(function () {addCategory()});
    
});

//helper
//== build user dropdown list with select
function buildUserOptionString(select) {
	select.html("");

	$.ajax ( {
		type: 'GET',
		url: '/rest/users',
		success: function(payload) {
			var users=payload.data;
			$.each(users, function (i, user) {
				select.append('<option value="' + user.id + '" user-firstname="' + user.firstName + 
						'" user-lastname="' + user.lastName + '" user-emailAddress="' + user.emailAddress +
						'">' + user.firstName + ' ' + user.lastName + '</option>');	        	
	        });
		    select.val(select.attr("pre-value"));   	
		    select.change();
	    },
		error: function(xhr,err){
		    alert("Error Retrievig Users!\n" + xhr.responseText);
		}	    
	});	
}

