package org.nutz.spring.boot.service.entity;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.nutz.lang.Lang;
import org.nutz.lang.util.NutMap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Kerbores(kerbores@gmail.com)
 *
 * @param <T>
 *            实体类型
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false, of = {"paras", "dataList"})
public class Pagination<T extends Serializable> extends org.nutz.dao.pager.Pager {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 分页参数(带有一堆参数的分页)
     */
    private NutMap paras;

    /**
     * 数据列表
     */
    @NotNull(message = "数据列表非空,没有数据时为空列表")
    @Default
    private List<T> dataList = Lang.list();

    /**
     * @param page
     *            页面
     * @param pageSize
     *            页面大小
     */
    public Pagination(int page, int pageSize) {
        super(page, pageSize);
    }

    public static <T extends Serializable> Pagination<T> instance() {
        return Pagination.<T> builder()
                         .build();
    }

    public static <T extends Serializable> Pagination<T> instance(int page, int pageSize) {
        return Pagination.<T> builder()
                         .build()
                         .page(page)
                         .size(pageSize);
    }

    public static <T extends Serializable> Pagination<T> instance(List<T> dataList) {
        return Pagination.<T> builder()
                         .dataList(dataList)
                         .build();
    }

    public Pagination<T> dataList(List<T> dataList) {
        setDataList(dataList);
        return this;
    }

    public Pagination<T> page(int page) {
        setPageNumber(page);
        return this;
    }

    public Pagination<T> size(int size) {
        setPageSize(size);
        return this;
    }

    public Pagination<T> recordCount(int recordCount) {
        setRecordCount(recordCount);
        return this;
    }

    /**
     * 添加参数
     * 
     * @param key
     *            参数key
     * @param value
     *            参数值
     * @return 当前分页对象
     */
    public Pagination<T> addParam(String key, Object value) {
        if (this.paras == null) {
            this.paras = NutMap.NEW();
        }
        this.paras.addv(key, value);
        return this;
    }

}
