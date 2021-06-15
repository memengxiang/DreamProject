package com.mx.springboot.vo;

import lombok.Data;

import java.util.List;

@Data
public class ResourceVo {
    /**
     * 主键
     */
    private Long resourceId;

    /**
     * 资源名称
     */
    private String resourceName;

    /**
     * 请求地址
     */
    private String url;

    /**
     * 下级资源
     */
    private List<ResourceVo> children;
}
