

var table = layui.table;
/**
 * 初始化表格数据
 */
var tableIns = table.render({
    id:'test',
    elem:'#accountList',
    url:'/account/list', //数据接口
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
        {field: 'username',title:'用户名'},
        {field: 'realName',title:'真实姓名'},
        {field: 'roleName',title:'角色名称'},
        {field: 'email',title:'邮箱'},
        {field: 'sex',title:'性别'},
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
            email: $("#email").val(),
            createTimeRange: $("#createTimeRange").val()
        },
        page:{
            curr:1
        }
    });
}

/**
 * 新增账号
 */
function toAdd() {

    openLayui('/account/toAdd','新增账户');

    //更新渲染弹窗界面
    layui.form.render();

    mySubmit('addSubmit','POST');

}


//工具条事件
table.on('tool(test)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
    var data = obj.data; //获得当前行数据
    var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
    var tr = obj.tr; //获得当前行 tr 的 DOM 对象（如果有的话）

    let accountId = data.accountId;//当前行的ID

    if(layEvent === 'detail'){ //查看客户详情
        openLayui('/account/toDetail/'+accountId,'账号详情');
        //do somehing
    } else if(layEvent === 'del'){ //删除客户信息
        layer.confirm('真的删除行么', function(index){
            layer.close(index);
            myDel("/account/"+accountId);

            //向服务端发送删除指令
        });
    } else if(layEvent === 'edit'){ //编辑客户信息
        openLayui('/account/toUpdate/'+accountId,'修改账户信息');

        //更新渲染弹窗界面
        layui.form.render();

        mySubmit('updateSubmit','PUT');
    }
});　　　


/**
 * 渲染日期范围
 */
layui.use('laydate', function(){
    var laydate = layui.laydate;

    //执行一个laydate实例
    laydate.render({
        elem: '#createTimeRange',//指定元素
        range:true
    });
});

/**
 * 检测账号重复
 */
layui.form.verify({
    checkUsername:function (value,item) {  //value 表单的值，  item表单的DOM对象
        let error = null;

        let accountId = $("input[name='accountId']").val();
        let url = '/account/'+value;
        if (typeof (accountId) != 'undefined'){
            url = url + '/'+accountId;
        }

        $.ajax({
            url:url,//获取地址
            async:false,//同步，关闭后查询列表
            type: 'GET',
            success:function (res) {
                if (res.code == 0){  //相应成功
                    if (res.data > 0){
                        error = "用户名已经存在！"
                    }
                }else {
                    error = "用户名检测出错！"
                }
            },error:function (){
                error = "用户名检测出错！"
        }
        });
        if (error!= null){
            return error;
        }
    }

});
