/*-------------------- Thymol - the flavour of Thymeleaf --------------------*

   Thymol version 2.0.0 Copyright (C) 2012-2015 James J. Benson
   jjbenson .AT. users.sf.net (http://www.thymoljs.org/)

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" basis,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either expressed or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

 *---------------------------------------------------------------------------*/

(function() {
    var thymolConfiguration = {
        thScriptName: "thymol-full.js",
        thJQuerySource: "http://code.jquery.com/jquery-1.11.1.min.js",
        thDefaultPrefix: "th",
        thDefaultDataPrefix: "data",
        thDefaultPrecision: 10,
        thDefaultProtocol: "file:///",
        thDefaultLocale: "en",
        thDefaultPrecedence: 2e4,
        thDefaultMessagePath: "",
        thDefaultResourcePath: "",
        thDefaultMessagesBaseName: "Messages",
        thDefaultRelativeRootPath: "",
        thDefaultExtendedMapping: false,
        thDefaultLocalMessages: true,
        thDefaultDisableMessages: false,
        thDefaultTemplateSuffix: ".html"
    };
    thymol = function(conf) {
        conf.ready = function(func) {
            if (typeof thymolDeferredFunctions === "undefined" || thymolDeferredFunctions === null) {
                thymolDeferredFunctions = [];
            }
            thymolDeferredFunctions.push(func);
        };
        return conf;
    }(thymolConfiguration);
    thymol.thDomParser = DOMParser;
    thymol.thWindow = window;
    var toc = 0;
    var scripts = document.getElementsByTagName("script");
    var script = document.currentScript || scripts[scripts.length - 1];
    var scriptSrc = script.getAttribute("src");
    var pathEnd = scriptSrc.lastIndexOf("/");
    if (pathEnd >= 0) {
        thymol.thLocation = scriptSrc.substring(0, 1 + pathEnd);
    }
//    var jquerySrc = script.getAttribute("data-jquery-src");
//    if (!!jquerySrc || "" === jquerySrc) {
//        thymol.thJQuerySource = jquerySrc;
//        if ("" !== thymol.thJQuerySource) {
//            loadScript(thymol.thJQuerySource);
//        }
//    } else if (typeof thymol.thJQuerySource !== "undefined" && thymol.thJQuerySource !== null && thymol.thJQuerySource.length > 0) {
//        var hasProtocol = thymol.thJQuerySource.indexOf(":/") >= 0;
//        if (hasProtocol || thymol.thJQuerySource.charAt(0) === "/") {
//            if (!hasProtocol) {
//                loadScript(thymol.thDefaultProtocol + thymol.thJQuerySource);
//            } else {
//                loadScript(thymol.thJQuerySource);
//            }
//        } else {
//            loadScript(thymol.thLocation + thymol.thJQuerySource);
//        }
//    }
//    var thymolSrc = script.getAttribute("data-thymol-src");
//    if (!!thymolSrc) {
//        thymol.thScriptName = thymolSrc;
//    }
//    var parameters = null;
//    for (var i = 0, iLimit = scripts.length; i < iLimit; i++) {
//        parameters = scripts[i].getAttribute("data-thymol-parameters");
//        if (!!parameters) {
//            break;
//        }
//    }
//    loadScript(thymol.thLocation + thymol.thScriptName, parameters);
})();


$(function() {
	$('[data-ajax="true"]').each( function() {
		var elem = this, $elem = $(elem);

		data = {
			temp : $elem.html()
		};

		data.temp = data.temp.replace(/(data-th-|th:)(\w+=".*?")/g, function(a, b, c) {
			return b + c.replace("<", "&lt;").replace( ">", "&gt;");
		});

		$elem.html("数据加载中...");

		$.ajax({
			type : "GET",
			url : $elem.data("url"), //!thDataUrl ? "tempGetHtml" : thDataUrl,
//			data : data,
			success : function(res) {
//				$elem.html(res);
				var htm = templateEngine.render({
					input: data.temp,
					data: res
				})
				$elem.html(htm);
			},
			error : function(e) {
				console.log(e);
				$elem.html("数据加载失败！！！");
			}
		});
	});
	
	templateEngine = {
			name: "thyemleaf",
			render: function(p) {
				var data = typeof(p.data) === "string" ? JSON.parse(p.data) : p.data;
				thVars = [];
				for (var prop in data) {
					thVars.push([ prop, data[prop] ]);
				}
				
				var jTemp = new DOMParser().parseFromString(p.input, "text/html");
				var rTemp = thymol.execute(jTemp);
				var rText = rTemp.body.innerHTML;
				return rText;
			}
	}
});

thymolDisable = true;
