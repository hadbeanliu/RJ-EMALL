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
        thJQuerySource: "/cij/jquery/jquery-2.1.4.min.js",
        thDefaultPrefix: "th",
        thDefaultDataPrefix: "data",
        thDefaultPrecision: 10,
        thDefaultProtocol: "",
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
    var loadScript = function(script, params, func) {
        var el = document.createElement("script");
        el.setAttribute("data-th-remove", "all");
        
        if (typeof func === "function" && func !== null) {
        	if (el.readyState) {
        		if (el.complete) {
        			func();
        		} else {
        			el.onreadystatechange = function() {
        				if (el.readyState === "complete" || a.readyState === "loaded") {
        					func();
        				}
        			};
        		}
        	} else {
        		el.onload = function() {
        			func();
        		};
        	}
        }
        
        el.async = false;
        if (typeof script !== "undefined" && script !== null) {
            el.src = script;
        }
        if (typeof params !== "undefined" && params !== null) {
            el.src += params.charAt(0) === "?" ? params : "?" + params;
        }
        el.type = "text/javascript";
        (document.getElementsByTagName("HEAD")[0] || document.body).appendChild(el);
    };
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
    var thymolSrc = script.getAttribute("data-thymol-src");
    if (!!thymolSrc) {
        thymol.thScriptName = thymolSrc;
    }
    var parameters = null;
    for (var i = 0, iLimit = scripts.length; i < iLimit; i++) {
        parameters = scripts[i].getAttribute("data-thymol-parameters");
        if (!!parameters) {
            break;
        }
    }
    
//    var config = script.getAttribute("data-config");
////    if (config) {
////    	config = JSON.parse(config);
////    	for ( var prop in config) {
////    		window[prop] = config[prop];
////		}
////    }
//    config = config ? config.split(';') : [];
//    for ( var i = 0; i < config.length; i++) {
//    	var kv = config[i].split('=');
//		window[kv[0]] = kv[1];
//	}
    
    loadScript(thymol.thLocation + thymol.thScriptName, parameters, function(){
    	
    	thymol.executeElement = function(elem) {
    		var base = new ThNode(elem, true, null, elem.firstChild, elem.nextSibling, elem.ownerDocument.nodeName, "::", true, elem);
    		thymol.Thymol.prototype.process(base);
    		return elem;
    	};
    	
    	function ThNode(thDocParam, visitedParam, parentDocParam, firstChildParam, nextSiblingParam, fileNameParam, fragNameParam, isNodeParam, elementParam) {
            this.thDoc = thDocParam;
            this.visited = visitedParam;
            this.parentDoc = parentDocParam;
            this.firstChild = firstChildParam;
            this.nextSibling = nextSiblingParam;
            this.fileName = fileNameParam;
            this.fragName = fragNameParam;
            this.isNode = isNodeParam;
            this.element = elementParam;
        }
    	
		if (thymol.thThymeleafPrefixList.length === 0) {
			if (typeof thymol.protocol === "undefined") {
				thymol.protocol = "";
			}
			if (typeof thymol.root === "undefined") {
				thymol.root = "";
			}
			if (typeof thymol.path === "undefined") {
				thymol.path = "";
			}
			thymol.thDocument = document;
			var theWindow = thymol.thWindow;
			if (typeof thymol.thWindow === "undefined") {
				if (typeof doc.defaultView !== "undefined") {
					theWindow = doc.defaultView;
				} else if (typeof doc.parentWindow !== "undefined") {
					theWindow = doc.parentWindow;
				}
			}
			thymol.thWindow = theWindow;
			var theTop = thymol.thTop;
			if (typeof thymol.thTop === "undefined") {
				if (typeof top !== "undefined") {
					theTop = top;
				}
			}
			thymol.thTop = theTop;
			thymol.init();
			
			thymol.configurePreExecution( function() {
				
				if (typeof Cfg !== 'undefined' ) {
					thymol.resourcePath=Cfg.path;
					
					thymol.applicationContext.createVariable("contextPath", Cfg.path );
					thymol.applicationContext.createVariable("tempPath", Cfg.basePath);
					thymol.applicationContext.createVariable("tempInternalPath", Cfg.baseDomains + Cfg.basepath);
				}
				
				var ds = script.getAttribute("data-src");
				if (ds) {
					var json = $.ajax({
						url: ds,
						async: false
					}).responseText;
					
					var data = JSON.parse(json);
					for ( var prop in data) {
						thymol.requestContext.createVariable(prop, data[prop]);
					}
				}
			});
		}

    });
})();

thymolAuto = typeof Cfg !== 'undefined' && Cfg.thymolAuto ? Cfg.thymolAuto : false;
