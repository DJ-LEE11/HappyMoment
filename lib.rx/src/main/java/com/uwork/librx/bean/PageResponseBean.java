package com.uwork.librx.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author 李栋杰
 * @time 2018/3/15  上午10:35
 * @desc ${TODD}
 */
public class PageResponseBean<T> implements Serializable {

    /**
     * content : [{"avatar":"string","content":"string","createTime":"string","id":0,"momentsCommentResponseBeans":[{"content":"string","createTime":"string","id":0,"sendUserId":0,"toUserId":0}],"momentsFavourResponseBeans":[{"avatar":"string","id":0,"name":"string"}],"name":"string","picture":["string"],"userId":0}]
     * first : false
     * last : false
     * pageNum : 0
     * pageSize : 0
     * totalElements : 0
     * totalPages : 0
     */

    private boolean first;
    private boolean last;
    private int pageNum;
    private int pageSize;
    private int totalElements;
    private int totalPages;
    private List<T> content;

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }
}
