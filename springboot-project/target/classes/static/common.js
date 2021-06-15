
/**
 * 公共弹出层
 * @param url
 * @param title
 */
function openLayui(url,title) {
    $.ajaxSettings.async =false;
    $.get(url,function (res) {
        layer.open({
            type: 1,//页面
            title: title,
            area: ['800px', '450px'],//设置弹出宽高
            content: res  //显示内容
        });
    });
    $.ajaxSettings.async =true;
}

/**
 * 提交监听事件
 * @param filter
 * @param type
 */
function mySubmit(filter,type,func) {
    layui.form.on('submit('+filter+')', function(data){

        if (typeof(func) != 'undefined'){
            func(data.field);
        }

        $.ajax({
            url:data.form.action,//获取地址
            async:false,//同步，关闭后查询列表
            type: type,
            contentType:'application/json;charset=utf-8',
            data:JSON.stringify(data.field),
            success:function (res) {
                if (res.code == 0){  //相应成功
                    layer.closeAll(); //关闭当前弹窗
                    query();  //查询列表
                }else {
                    layer.alert(res.msg)
                }
            }
        });
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });
}

/**
 * 公共删除方法
 * @param url
 */
function myDel(url){
    $.ajax({
        url:url,//获取地址
        async:false,//同步，关闭后查询列表
        type: "DELETE",
        success:function (res) {
            if (res.code == 0){  //相应成功
                query();  //查询列表
            }else {
                layer.alert(res.msg)
            }
        }
    });
}