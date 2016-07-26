'use strict';

(function ($) {
    var tree = {};
    var idCounter = 0;
    var $elem = undefined;
    /**
    * Enum Switch States
    * @enum {number}
    **/
    var SwitchAction = {
        ON: 1,
        OFF: 0
    };
    /**
    * Enum Events
    * @enum {string}
    **/
    var Events = {
        EXPAND: 'expand',
        COLLAPSE: 'collapse'
    };
    var defaultOpts = {
        lazyLoad: false,
        types: {
            default: {
                icon: 'glyphicon glyphicon-home'
            }
        },
        expandIcon: 'glyphicon glyphicon-chevron-right',
        collapseIcon: 'glyphicon glyphicon-chevron-down'
    };
    var userOpts = {};

    function logError() {
        if (typeof console !== 'undefined') {
            var _console;

            (_console = console).log.apply(_console, ['[treeview]: '].concat(Array.prototype.slice.call(arguments)));
        }
    }
    function init(elem, opts) {
        $elem = $(elem);
        $elem.addClass('vtree');
        $.extend(true, userOpts, defaultOpts, opts);
    }

    function render(cur, $cur) {
        if (!Array.isArray(cur.nodes) || cur.nodes.length === 0) {
            return;
        }
        var $ul = $(document.createElement('ul'));
        $ul.addClass('list-unstyled tree-group');
        var _iteratorNormalCompletion = true;
        var _didIteratorError = false;
        var _iteratorError = undefined;

        try {
            for (var _iterator = cur.nodes[Symbol.iterator](), _step; !(_iteratorNormalCompletion = (_step = _iterator.next()).done); _iteratorNormalCompletion = true) {
                var node = _step.value;

                node.id = node.id || ++idCounter;
                if (!userOpts.types.hasOwnProperty(node.type)) {
                    node.type = 'default';
                }
                var $li = $(document.createElement('li'));
                $li.addClass('tree-group-item');
                $li.attr('data-id', node.id);
                $li.attr('data-type', node.type);
                if (node.expanded) $li.addClass('expanded');
                var ctrl = '';
                if (!node.leaf) {
                    ctrl = '<span class="' + userOpts.expandIcon + ' expand-icon"></span>\n                        <span class="' + userOpts.collapseIcon + ' collapse-icon"></span>';
                }
                $li.html('<div class=\'item-content\'>\n                ' + ctrl + '\n                <span class="' + userOpts.types[node.type].icon + ' "></span>\n                <a href=\'' + (node.url ? node.url : 'javascript:void(0)') + '\'>' + node.text + '</a>\n                </div>');
                if (userOpts.types[node.type].hasOwnProperty('btn')) {
                    var $btn = $(document.createElement('button'));
                    $btn.addClass('btn btn-default');
                    $btn.text(userOpts.types[node.type].btn.defaultName);
                    $li.children('.item-content').prepend($btn);
                }
                $ul.append($li);
                //dfs
                render(node, $li);
            }
        } catch (err) {
            _didIteratorError = true;
            _iteratorError = err;
        } finally {
            try {
                if (!_iteratorNormalCompletion && _iterator.return) {
                    _iterator.return();
                }
            } finally {
                if (_didIteratorError) {
                    throw _iteratorError;
                }
            }
        }

        $cur.append($ul[0]);
    }
    // 根据data建树
    function build(data) {
        data.rootId = data.rootId || 0;
        var $root = $elem;
        if (data.rootId) {
            $root = $elem.find('[data-id=' + data.rootId + ']');
            if ($root.length === 0) {
                logError('cannot find tree root', '{id:' + data.rootId + '}');
                return;
            }
        }
        $root.children('ul').remove();
        render(data, $root);
     //   adjustLine();
    }
    /**
    * @param params {object} - ajax请求的参数
    * @param cb {function} - callback function
    */

    function load() {
        var params = arguments.length <= 0 || arguments[0] === undefined ? {} : arguments[0];
        var cb = arguments[1];

        var $img = $(document.createElement('img'));
        $img.attr('src', userOpts.loadingImg);
        if (!params.hasOwnProperty('id')) {
            params.id = 0;
        }
        if (params.id === 0) {
            $elem.children('ul').fadeOut();
            userOpts.loadingImg && $elem.before($img);
        } else {
            userOpts.loadingImg && $elem.find('li[data-id=' + params.id + ']  span.collapse-icon').eq(0).after($img);
        }

        $.ajax($.extend({}, userOpts.xhrConf, {
            data: params
        })).done(function (data) {
            build(data);
        }).fail(function (XMLHttpRequest) {
            logError(XMLHttpRequest.status + ': ' + XMLHttpRequest.responseText);
        }).always(function () {
            $img.remove();
            $elem.children('ul').fadeIn();
            cb && cb();
        });
    }
    function adjustLine() {
        var $ul = arguments.length <= 0 || arguments[0] === undefined ? $elem.find('ul') : arguments[0];

        var $li = $ul.children('li:visible').last();
        $li.addClass('last-child').siblings().removeClass('last-child');
    }
    function _expandNode($li, cb) {
        var callback = function callback() {
            cb && cb();
            $elem.trigger(Events.EXPAND, [id, $li]);
        };
        if ($li.hasClass('expanded')) {
            callback();
            return;
        }
        var id = $li.data('id');
        $li.addClass('expanded');
        if (userOpts.lazyLoad === false) {
            callback();
            return;
        }
        var $subul = $li.children('ul');
        if ($subul.length === 0 || $subul.children('li').length === 0) {
            load({
                id: id
            }, callback);
        } else {
            callback();
        }
    }
    function _collapseNode($li) {
        $li.removeClass('expanded');
        $elem.trigger(Events.COLLAPSE, [$li.data('id'), $li]);
    }
    function bind() {
        //bind event
        $elem.on('click', 'span.expand-icon', function () {
            _expandNode($(this).closest('li'));
        });

        $elem.on('click', 'span.collapse-icon', function () {
            _collapseNode($(this).closest('li'));
        });

        $elem.on('click', 'button', function () {
            var $self = $(this);
            var $li = $self.closest('li');
            _expandNode($li, function () {
                var type = $li.data('type');
                var ACTION = $self.text() === userOpts.types[type].btn.defaultName ? SwitchAction.ON : SwitchAction.OFF;
                userOpts.types[type].btn.handler.call(this, $li.data('id'), ACTION);
                switch (ACTION) {
                    case SwitchAction.ON:
                        $self.text(userOpts.types[type].btn.activeName);
                        $li.addClass('active');
                        break;
                    case SwitchAction.OFF:
                        $self.text(userOpts.types[type].btn.defaultName);
                        $li.removeClass('active');
                        break;
                }
                adjustLine($li.children('ul'));
            });
        });
    }

    $.fn['vtree'] = function (data, opts) {
        if (this.length === 0) return;
        if (typeof opts === 'undefined') {
            opts = data;
            data = {};
        }
        init(this[0], opts);
        build(data);
        if (userOpts.lazyLoad && typeof userOpts.keyword === 'undefined') {
            load();
            bind();
        } else if(typeof userOpts.keyword !== 'undefined'){
        	 load({
 	            keyword: userOpts.keyword
 	        });
        }
        return {
            load: load,
            build: build,
            expandNode: function expandNode(id, cb) {
                var $li = $('li[data-id=' + id + ']', $elem);
                _expandNode($li, cb);
            },
            collapseNode: function collapseNode(id) {
                var $li = $('li[data-id=' + id + ']', $elem);
                _collapseNode($li);
            },
            on: function on(eventName, callback) {
                $elem.on(eventName, callback);
            },
            off: function off(eventName, callback) {
                $elem.off(eventName, callback);
            }
        };
    };
})(jQuery);