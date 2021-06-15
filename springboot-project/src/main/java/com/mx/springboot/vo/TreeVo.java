package com.mx.springboot.vo;


import lombok.Data;

import java.util.List;

@Data
public class TreeVo {

    /**
     * 标题
     */
    private String title;

    /**
     * Id
     */
    private Long Id;

    /**
     * 子树
     */
    private List<TreeVo> children;

    /**
     * 是否选中
     */
    private boolean checked;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public List<TreeVo> getChildren() {
        return children;
    }

    public void setChildren(List<TreeVo> children) {
        this.children = children;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
