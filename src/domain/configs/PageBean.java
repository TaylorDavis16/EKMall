package domain.configs;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class PageBean<T,C> implements Serializable {
    private int totalCount; // 总记录数
    private int totalPage ; // 总页码
    private List<T> typeList;
    private C config;
    private int currentPage ; //当前页码
    private int sizes;//每页显示的记录数
    
    
    {
        currentPage=1;
        sizes=8;
    }

    public PageBean() {
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<T> typeList) {
        this.typeList = typeList;
    }

    public C getConfig() {
        return config;
    }

    public void setConfig(C config) {
        this.config = config;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getSizes() {
        return sizes;
    }

    public void setSizes(int sizes) {
        this.sizes = sizes;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "totalCount=" + totalCount +
                ", totalPage=" + totalPage +
                ", typeList=" + typeList +
                ", config=" + config +
                ", currentPage=" + currentPage +
                ", sizes=" + sizes +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageBean<?, ?> pageBean = (PageBean<?, ?>) o;
        return totalCount == pageBean.totalCount &&
                totalPage == pageBean.totalPage &&
                currentPage == pageBean.currentPage &&
                sizes == pageBean.sizes &&
                config.equals(pageBean.config);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalCount, totalPage, config, currentPage, sizes);
    }
}
