//thDebug = true;

thymol.configurePreExecution( function() {

	thymol.resourcePath="/emall";

    thymol.applicationContext.createVariable("contextPath", "/emall" );
    thymol.applicationContext.createVariable("tempPath", "/emall/tpl/dsww/002/" );
    thymol.applicationContext.createVariable("tempInternalPath", "http://192.168.4.200:8888/emall/tpl/dsww/002/" );
    
    var json = $.ajax({
	  url: "http://192.168.4.200:8888/emall/pay/info.json?orderId=0ick6qm4p06y222u",
	  async: false
	}).responseText;
    
    var data = JSON.parse(json);
    for ( var prop in data) {
    	thymol.requestContext.createVariable(prop, data[prop]);
	}
});

