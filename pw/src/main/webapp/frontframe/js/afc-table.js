/**
 * 对DataTable进行简单封装。
 * 增加一个window.afcTable变量，导出以下函数：
 * 1. afcTable.createTable(parent, elementId, option, onselect);
 * 2. afcTable.curRowData();
 * 3. afcTable.clearAndFill(rows);
 */
(function () {
    window.afcTable = {};

    /*默认禁用排序和搜索*/
    $.extend($.fn.dataTable.defaults, {
        searching: false,
        ordering: false
    });
    /*如果要使用，请自行调用如下函数*/
    /*
     $('#example').DataTable( {
     ordering: true
     } );
     */
    /**
     * 创建一个新的表格
     * @param parent HTML元素所属的对象
     * @param elementId HTML的元素ID
     * @param option 创建表格时候，需要设置的属性对象，参考DataTable文档。
     *        对于"language"等特定属性，如果没有设置，本方法会设置一个默认值。
     * @param onselect 当选中某行时，激发该事件。
     */
    afcTable.createTable = function (parent, elementId, option, onselect) {
        if (typeof(option) == 'undefined' || option == null) {
            return null;
        }
        var lengthMenu_1 = [10, 20, 50];
        var lengthMenu_2 = [10, 20, 50];
        if (typeof(option.serverSide) == 'undefined' || option.serverSide != true) {
            // 服务端分页的查询，禁用“所有”菜单项
            lengthMenu_1.push(-1);
            lengthMenu_2.push('所有');
        }
        fillUndefinedProperty(option, "lengthMenu", [lengthMenu_1, lengthMenu_2]);
        fillUndefinedProperty(option, "pageLength", 10);
        fillUndefinedProperty(option, "pagingType", "full_numbers");
        fillUndefinedProperty(option, "language", language);
        var table = doCreateTable(parent, elementId, option);
        if (typeof(onselect) != 'undefined' && onselect != null) {
            table['afc-onselect'] = onselect;
        }

        /**
         * 返回当前选中行对应的数据
         */
        table.curRowData = function () {
            var curRow = table.row('.selected');
            if (typeof(curRow) == 'undefined' || curRow == null) {
                return null;
            }
            var data = curRow.data();
            if (typeof(data) == 'undefined' || data == null) {
                return null;
            }
            return data;
        }

        /**
         * 把之前的老数据清空掉，设置为新数据
         * @param rows
         */
        table.clearAndFill = function (rows) {
            table.clear();
            table.rows.add(rows);
            table.draw();
        }

        if (option.serverSide) {
            // 如果是服务器端分页，控制一下用户点击下一页的频率。太快了只会导致请求积压
            var lastTime = (new Date()).getTime();
            table.on('draw', function () {
                parent.find(".paginate_button").on("click", function (e) {
                    var curTime = (new Date()).getTime();
                    if (curTime - lastTime < 100) {
                        alert('查询速度过快');
                    }
                    else {
                        lastTime = curTime;
                    }
                });
            })
        }
        return table;
    }

    function fillUndefinedProperty(obj, propName, propValue) {
        if (typeof(obj[propName]) == 'undefined') {
            obj[propName] = propValue;
        }
    }

    function doCreateTable(parent, elementId, option) {
        var table = parent.find('#' + elementId).DataTable(option);

        parent.find('#' + elementId + ' tbody').on('click', 'tr', function () {
            if ($(this).hasClass('selected')) {
                $(this).removeClass('selected');
            }
            else {
                table.$('tr.selected').removeClass('selected');
                $(this).addClass('selected');
                var onselect = table['afc-onselect'];
                if (typeof(onselect) != 'undefined' && onselect != null) {
                    onselect();
                }
            }
        });
        return table;
    }

    var language = {
        "sProcessing": "处理中...",
        "sLengthMenu": "显示 _MENU_ 项结果",
        "sZeroRecords": "没有匹配结果",
        "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
        "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
        "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
        "sInfoPostFix": "",
        "sSearch": "搜索:",
        "sUrl": "",
        "sEmptyTable": "表中数据为空",
        "sLoadingRecords": "载入中...",
        "sInfoThousands": ",",
        "oPaginate": {
            "sFirst": "首页",
            "sPrevious": "上页",
            "sNext": "下页",
            "sLast": "末页"
        },
        "oAria": {
            "sSortAscending": ": 以升序排列此列",
            "sSortDescending": ": 以降序排列此列"
        }
    };

})();
