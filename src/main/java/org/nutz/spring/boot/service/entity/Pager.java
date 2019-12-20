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
 * @author kerbores(kerbores@gmail.com)
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false, of = {"paras", "dataList"})
public class Pager<T extends Serializable> extends org.nutz.dao.pager.Pager {

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
    @NotNull(message = "数据列表非空,没有数据是为空列表")
    @Default
    private List<T> dataList = Lang.list();

    /**
     * @param page
     *            页面
     * @param pageSize
     *            页面大小
     */
    public Pager(int page, int pageSize) {
        super(page, pageSize);
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
    public Pager<T> addParam(String key, Object value) {
        if (this.paras == null) {
            this.paras = NutMap.NEW();
        }
        this.paras.addv(key, value);
        return this;
    }

}
