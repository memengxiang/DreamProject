package com.mx.springboot.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;

import java.util.HashMap;
import java.util.Map;

public class ResultUtil {

    /**
     * 构建分页查询的返回结果
     * @param page
     * @return
     */
    public static R<Map<String,Object>> buildPage(IPage<?> page){
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("count",page.getTotal());
        map.put("records",page.getRecords());

        return R.ok(map);
    }

    /**
     * 成功或失败的相应信息
     * @param success
     * @return
     */
    public static R<Object> buildR(boolean success){

        if (success){
            return R.ok(null);
        }
        return R.failed("相应失败");
    }
}
