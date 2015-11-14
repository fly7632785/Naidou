package com.itspeed.naidou.model.bean.JsonBean;

/**
 * Created by jafir on 10/27/15.
 */
public class ListData {
    //一共多少页
    private int pages;
    //现在是第几页
    private int currentPage;
    //每页多少条
    private int count;
    //一共多少条
    private int maxCount;


    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "ListData{" +
                "pages=" + pages +
                ", currentPage=" + currentPage +
                ", count=" + count +
                '}';
    }
}
