'use strict';

var fakename = 0;
$.mockjax({
    url: '/getTree',
    responseTime: 1000,
    response: function response(settings) {
        this.responseText = {
            rootId: settings.data.id,
            nodes: [{
                text: 'item-' + ++fakename,
                type: 'staff'
            }, {
                text: 'item-' + ++fakename,
                type: 'section'

            }]
        };
    }
});