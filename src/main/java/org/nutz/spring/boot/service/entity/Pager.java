package org.nutz.spring.boot.service.entity;

import java.io.Serializable;
import java.util.List;

import org.nutz.lang.util.NutMap;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
    private List<T> dataList;

    /**
     * @param page
     * @param pageSize
     */
    public Pager(int page, int pageSize) {
        super(page, pageSize);
    }

    public Pager<T> addParam(String key, Object value) {
        if (this.paras == null) {
            this.paras = NutMap.NEW();
        }
        this.paras.addv(key, value);
        return this;
    }

}
