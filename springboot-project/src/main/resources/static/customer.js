var table = layui.table;
/**
 * 初始化表格数据
 */
var tableIns = table.render({
    elem:'#customerList',
    url:'/customer/list', //数据接口
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
        {field: 'realName',title:'姓名'},
        {field: 'sex',title:'性别'},
        {field: 'age',title:'年龄'},
        {field: 'phone',title:'手机号码'},
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
            realName: $("#realName").val(),
            phone: $("#phone").val()
        },
        page:{
            curr:1
        }
    });
}

/**
 * 新增客户
 */
function toAdd() {

    openLayui('/customer/toAdd','新增客户');

    //更新渲染弹窗界面
    layui.form.render();

    mySubmit('addSubmit','POST');

}


//工具条事件
table.on('tool(test)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
    var data = obj.data; //获得当前行数据
    var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
    var tr = obj.tr; //获得当前行 tr 的 DOM 对象（如果有的话）

    let customerId = data.customerId;//当前行的ID

    if(layEvent === 'detail'){ //查看客户详情
        openLayui('/customer/toDetail/'+customerId,'客户详情');
        //do somehing
    } else if(layEvent === 'del'){ //删除客户信息
        layer.confirm('真的删除行么', function(index){
            layer.close(index);
            myDel("/customer/"+customerId);

            //向服务端发送删除指令
        });
    } else if(layEvent === 'edit'){ //编辑客户信息
        openLayui('/customer/toUpdate/'+customerId,'修改客户');

        //更新渲染弹窗界面
        layui.form.render();

        mySubmit('updateSubmit','PUT');
    }
});
