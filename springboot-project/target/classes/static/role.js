
var table = layui.table;
/**
 * 初始化表格数据
 */
var tableIns = table.render({
    id:'test',
    elem:'#roleList',
    url:'/role/list', //数据接口
    parseData:function(res){
        return {
            "code":res.code,
            "msg":res.msg,
            "count":res.data.count,
            "data":res.data.records,
        };
    },
    page:true,//开启分页
    cols:[[//表头
        {field: 'roleName',title:'角色名称'},
        {field: 'createTime',title:'创建时间'},
        {title:'操作',toolbar:'#barDemo'},
    ]]
});

/**
 * 按条件查询
 */
function query() {
    tableIns.reload({
        where:{
            roleName: $("#roleName").val(),
        },
        page:{
            curr:1
        }
    });
}

/**
 * 新增角色
 */
function intoAdd() {

    openLayui('/role/toAdd','新增角色');

    showTree('/role/listResource','resource');

    mySubmit('addSubmit',"POST",addIds);
}

/**
 * 通用资源树方法
 * @param url
 * @param id
 */
function showTree(url,id,showCheckbox) {

    if (typeof(showCheckbox) == 'undefined'){
        showCheckbox = true;
    }

    $.ajax({
        url:url ,//获取地址
        async:false,//同步，关闭后查询列表
        type: 'GET',
        success:function (res) {
            if (res.code == 0){  //相应成功

                layui.tree.render({
                    elem: '#'+id,
                    data:res.data,
                    id: id,
                    showCheckbox:showCheckbox
                });
            }
        }
    });
}

/**
 * 追加选中的资源Id
 * @param field
 */
var addIds = function(field) {
    let checkedData = layui.tree.getChecked('resource');
    field.resourceIds = getIds(checkedData,[]);
}

/**
 * 获取树形Ids
 * @param checkedData
 * @param arr
 * @returns {*}
 */
function getIds(checkedData,arr){
    for (let i in checkedData) {
        arr.push(checkedData[i].id);
        arr = getIds(checkedData[i].children,arr)
    }
    return arr;
}


//工具条事件
table.on('tool(test)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
    var data = obj.data; //获得当前行数据
    var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
    var tr = obj.tr; //获得当前行 tr 的 DOM 对象（如果有的话）

    let roleId = data.roleId;//当前行的ID

    if(layEvent === 'detail'){ //查看客户详情
        openLayui('/role/toDetail/'+roleId,'账号详情');
        /**
         * 此处”1/0“ 对应后台 flag 参数，
         * 1代表详情界面，用于不显示未选中的角色资源。没有复选框
         * 0代表编辑界面，代表选中之前选中的角色资源。有复选框
         */
        showTree('/role/listResource/'+roleId+'/1','resource',false);


    } else if(layEvent === 'del'){ //删除客户信息
        layer.confirm('真的删除行么', function(index){
            layer.close(index);
            myDel("/role/"+roleId);

            //向服务端发送删除指令
        });
    } else if(layEvent === 'edit'){ //编辑客户信息
        openLayui('/role/toUpdate/'+roleId,'编辑角色');

        showTree('/role/listResource/'+roleId+'/0','resource');

        mySubmit('updateSubmit','PUT',addIds);
    }
});　　　
