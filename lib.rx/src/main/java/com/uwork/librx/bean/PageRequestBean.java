package com.uwork.librx.bean;

/**
 * @author 李栋杰
 * @time 2018/3/9  下午3:55
 * @desc ${TODD}
 */
public class PageRequestBean {
    private int pageIndex;
    private int pageSize;

    public PageRequestBean(int pageIndex, int pageSize) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
