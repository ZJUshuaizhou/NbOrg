'use strict';

var fakename = 0;

var Tester = function Tester() {
    $.mockjax({
        url: '/getTree',
        responseTime: 1000,
        response: function response(settings) {
            this.responseText = {
                rootId: settings.data.id,
                nodes: [{
                    text: 'item-' + ++fakename,
                    type: 'staff',
                    isLeaf: true
                }, {
                    text: 'item-' + ++fakename,
                    type: 'section'
                }]
            };
        }
    });
};
var Samples = {
    rootId: 0, //默认0
    nodes: [{
        text: 'item-' + ++fakename,
        type: 'section',
        url: 'www.baidu.com',
        isExpanded: true,
        nodes: [{
            url: 'http://www.baidu.com',
            text: 'item-' + ++fakename,
            type: 'section'
        }, {
            url: 'http://www.baidu.com',
            text: 'item-' + ++fakename,
            type: 'staff',
            isLeaf: true
        }]
    }, {
        url: 'http://www.baidu.com',
        text: 'item-' + ++fakename,
        type: 'staff',
        isLeaf: true
    }]
};
$(function () {
  //  Tester();
    var CONFIG = {
        lazyLoad: true,
        xhrConf: {
            type: 'GET',
            url: 'department/getTree',
            dataType: 'json'
        },
        loadingImg: 'assets/img/loading.gif',
        types: {
            section: {
                icon: 'glyphicon glyphicon-home',
                btn: {
                    defaultName: '显示人员',
                    activeName: '隐藏人员',
                    handler: function handler(id, action) {
                        /**
                        * callback function to excute after user clicked this button
                        * @param {string} id - the identify of this node
                        * @param {SwitchAction} [action=0|1] - the action name, 0:OFF or 1:ON
                        **/
                        console.log(id, action ? 'ON' : 'OFF');
                    }
                }
            },
            staff: {
                icon: 'glyphicon glyphicon-user'
            }
        }
    };
    //初始化
    var vtree = $('.tree-box').vtree(CONFIG);
    $("#search").bind('keypress',function(evrnt){
    	    if(event.keyCode == 13){
    	    	var search = $('.tree-box').vtree({ lazyLoad: true,
    	            xhrConf: {
    	                type: 'GET',
    	                url: 'department/searchTree',
    	                dataType: 'json'
    	            },
    	            loadingImg: 'assets/img/loading.gif',
    	            keyword: $("#search").val(),
    	            types: {
    	                section: {
    	                    icon: 'glyphicon glyphicon-home',
    	                    btn: {
    	                        defaultName: '显示人员',
    	                        activeName: '隐藏人员',
    	                        handler: function handler(id, action) {
    	                            /**
    	                            * callback function to excute after user clicked this button
    	                            * @param {string} id - the identify of this node
    	                            * @param {SwitchAction} [action=0|1] - the action name, 0:OFF or 1:ON
    	                            **/
    	                            console.log(id, action ? 'ON' : 'OFF');
    	                        }
    	                    }
    	                },
    	                staff: {
    	                    icon: 'glyphicon glyphicon-user'
    	                }}});
    	    }
    	});

    function TreeNodeDelete() {
        function handler(e, id, $elem) {
        	 console.log('event :', e, 'id :', id, 'element :', $elem);
        	 $.ajax(
      	    	    {url:"department/treenodedelete", 
      	        	 dataType:"json",
      	        	 contentType: "application/x-www-form-urlencoded; charset=utf-8", 
      	        	 type:"GET",
      	        	 data:{"id":id},
      			});
        }
        //绑定折叠事件
        vtree.on('collapse', handler);
    }
    TreeNodeDelete();
});