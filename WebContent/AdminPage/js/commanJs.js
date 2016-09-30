/**
 * 
 */


Map = function(){
	 this.map = new Object();
	};   
	Map.prototype = {   
	    put : function(key, value){   
	        this.map[key] = value;
	    },   
	    get : function(key){   
	        return this.map[key];
	    },
	    containsKey : function(key){    
	     return key in this.map;
	    },
	    containsValue : function(value){    
	     for(var prop in this.map){
	      if(this.map[prop] == value) return true;
	     }
	     return false;
	    },
	    isEmpty : function(key){    
	     return (this.size() == 0);
	    },
	    clear : function(){   
	     for(var prop in this.map){
	      delete this.map[prop];
	     }
	    },
	    remove : function(key){    
	     delete this.map[key];
	    },
	    keys : function(){   
	        var keys = new Array();   
	        for(var prop in this.map){   
	            keys.push(prop);
	        }   
	        return keys;
	    },
	    values : function(){   
	     var values = new Array();   
	        for(var prop in this.map){   
	         values.push(this.map[prop]);
	        }   
	        return values;
	    },
	    size : function(){
	      var count = 0;
	      for (var prop in this.map) {
	        count++;
	      }
	      return count;
	    }
	};
	
	
	

	$(document).ready( function() {
				$('#sourceTree').fileTree({
							root: "upload/32/das/source",
							script: 'SourceTree.jsp',
							multiFolder: false },
							function(file){
							
								$("pre code").load(file);
							}); 						
		});
	$('.uploadDate').datetimepicker({
        language:  'eu',
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		minView: 2,
		forceParse: 0
    });

	$('.allowDate').datetimepicker({
        language:  'eu',
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		minView: 2,
		forceParse: 0
    });
 
	$(document).ready( function() {
			$("#viewSource").on('click', function() {
			   alert($("#widgetTable").closest('tr').index());
			   _selectedWidgetIdx = $(this).closest('tr').index()-1;
			});
	});
	
$( document ).ready(function() {
	$("#acceptWidget").on( 'click', function() {
		alert(wId);
	 var data = "";
	 var wId = _widgets($(this).closest('tr').index()).get("developerId");
	 var evalId = _widgets($(this).closest('tr').index()).get("evalId");
	 
		
	 //jQuery의 Post함수로 입력받은 id값 전달 
				  $.ajax({
					   url: 'acceptWidget.do',
					   type: 'POST',
					   data: {'wId':wId,"evalId":evalId },
					   dataType: 'json',
					   success: function(data){
					    alert(data); // 결과 텍스트를 경고창으로 보여준다.
					   		}
				  });
	});
});
/*
$( document ).ready(function() {
	$('#widetTable').on( 'over', 'tr', function() {

         var selected = $(this).hasClass("highlight");
            $("#widetTable tr").removeClass("highlight");
            if(!selected)
                    $(this).addClass("highlight");

          alert('You clicked row '+ ($(this).index()+1) );
        });
});

*/

$(function () {
 $('#datetimepicker10').datetimepicker({
     viewMode: 'years',
     format: 'MM/YYYY'
 });
});

	