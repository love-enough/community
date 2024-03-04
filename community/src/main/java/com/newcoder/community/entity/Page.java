package com.newcoder.community.entity;

/**
 * @Description 封装分页相关信息
 * @author GuoZihan
 * @date 10:49 2024/3/2
 */
public class Page {
    //当前页码
    private int current =  1;
    //显示上限
    private int limit = 10;
    //数据总数（计算总页数）
    private int rows;
    //查询路径
    private String path;


    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        if(current >= 1) {
            this.current = current;
        }
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        if(limit >= 1 && limit <= 10) {
            this.limit = limit;
        }
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        if(rows >= 0) {
            this.rows = rows;
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @Description 获取当前页的起始行
     * @author GuoZihan
     * @date 10:54 2024/3/2
     */
    public int getoffset() {
        return (current - 1) * limit;
    }
    /**
     * @Description 获取总页数
     * @author GuoZihan
     * @date 10:55 2024/3/2
     */
    public int getTotal() {
        if(rows % limit == 0) {
            return rows / limit;
        } else {
            return rows / limit + 1;
        }
    }
    /**
     * @Description 获取起始页码
     * @author GuoZihan
     * @date 10:59 2024/3/2
     */
    public int getFrom() {
        int from = current - 2;
        return from < 1 ? 1 : from;
    }
    /**
     * @Description 获取结束页码
     * @author GuoZihan
     * @date 10:59 2024/3/2
     */
    public int getTo() {
        int to = current + 2;
        int total = getTotal();
        return to > total ? total : to;
    }
}
