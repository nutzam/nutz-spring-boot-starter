package org.nutz.spring.boot.service.interfaces;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

import org.nutz.dao.Chain;
import org.nutz.dao.Condition;
import org.nutz.dao.FieldFilter;
import org.nutz.dao.FieldMatcher;
import org.nutz.dao.entity.Entity;
import org.nutz.dao.pager.Pager;
import org.nutz.lang.Each;
import org.nutz.lang.Mirror;
import org.nutz.spring.boot.service.ExtService;

/**
 * 
 * @author Kerbores(kerbores@gmail.com)
 *
 * @param <T>
 *            实体类型
 */
public interface EntityService<T extends Serializable> extends ExtService<T> {

    static final String EQ = "=";
    static final String NEQ = "!=";
    static final String LT = "<";
    static final String GT = ">";
    static final String IS = "is";
    static final String IS_NOT = "is not";
    static final String NOT = "not";
    static final String LIKE = "like";
    static final String IN = "in";
    static final String LT_AND_EQ = "=<";
    static final String GT_AND_EQ = ">=";

    @Override
    public default Class<T> getEntityType() {
        Class<T> entryClass = Mirror.getTypeParam(getClass(), 1);
        Mirror<T> mirror = Mirror.me(entryClass);
        return mirror.getType();
    }

    /**
     * @return
     * @see org.nutz.spring.boot.service.ExtService#getEntity()
     */
    @Override
    public default Entity<T> getEntity() {
        return dao().getEntity(getEntityType());
    }

    /**
     * 批量删除
     * 
     * @param cnd
     *            条件
     * @return 删除的条数
     */
    @Override
    public default int clear(Condition cnd) {
        return dao().clear(getEntityType(), cnd);
    }

    /**
     * 全表删除
     * 
     * @return 删除的条数
     */
    @Override
    public default int clear() {
        return dao().clear(getEntityType(), null);
    }

    /**
     * 根据条件分页查询
     * 
     * @param cnd
     *            查询条件
     * @param pager
     *            分页
     * @return 查询结果
     */
    @Override
    public default List<T> query(Condition cnd, Pager pager) {
        return dao().query(getEntityType(), cnd, pager);
    }

    /**
     * 遍历条件分页查询结果
     * 
     * @param cnd
     *            查询条件
     * @param pager
     *            分页
     * @param callback
     *            遍历回调
     * @return 遍历的总条数
     */
    public default int each(Condition cnd, Pager pager, Each<T> callback) {
        return dao().each(getEntityType(), cnd, pager, callback);
    }

    /**
     * 根据条件统计符合条件的记录数
     * 
     * @param cnd
     *            查询条件
     * @return 记录数
     */
    @Override
    public default int count(Condition cnd) {
        return dao().count(getEntityType(), cnd);
    }

    /**
     * 全表的总记录数
     * 
     * @return 总记录数
     */
    public default int countByNutz() {
        return dao().count(getEntityType());
    }

    /**
     * 查出符合条件的第一条记录
     * 
     * @param cnd
     *            查询条件
     * @return 实体,如不存在则为null
     */
    public default T fetch(Condition cnd) {
        return dao().fetch(getEntityType(), cnd);
    }

    /**
     * 复合主键专用
     * 
     * @param pks
     *            键值
     * @return 对象 T
     */
    public default T fetchx(Object... pks) {
        return dao().fetchx(getEntityType(), pks);
    }

    /**
     * 复合主键专用
     * 
     * @param pks
     *            键值
     * @return 对象 T
     */
    public default boolean exists(Object... pks) {
        return null != fetchx(pks);
    }

    /**
     * 批量更新
     * 
     * @param chain
     *            设置值的键值对
     * @param cnd
     *            需要更新的条件语句
     */
    public default int update(Chain chain, Condition cnd) {
        return dao().update(getEntityType(), chain, cnd);
    }

    /**
     * 更新@ManyMany关联表中的数据
     * 
     * @param regex
     *            关联字段的匹配正则表达式,如果为null则代表全部
     * @param chain
     *            键值对
     * @param cnd
     *            条件语句
     */
    public default int updateRelation(String regex, Chain chain, Condition cnd) {
        return dao().updateRelation(getEntityType(), regex, chain, cnd);
    }

    /**
     * 根据复合主键删除记录
     * 
     * @param pks
     *            复合主键,必须按@Pk的声明顺序传入
     * @return 删除的记录数
     */
    public default int deletex(Object... pks) {
        return dao().deletex(getEntityType(), pks);
    }

    /**
     * 根据一个实体的配置信息为其创建一张表
     * 
     * @param dropIfExists
     *            如果表存在是否强制移除
     * @return 实体对象
     */
    public default Entity<T> create(boolean dropIfExists) {
        return dao().create(getEntityType(), dropIfExists);
    }

    /**
     * 与 insert(String tableName, Chain chain) 一样，不过，数据表名，将取自 POJO 的数据表声明，请参看
     * '@Table' 注解的详细说明
     * 
     * @param chain
     *            数据名值链
     */
    public default void insert(Chain chain) {
        dao().insert(getEntityType(), chain);
    }

    /**
     * 查询一组对象。你可以为这次查询设定条件
     * 
     * @param cnd
     *            WHERE 条件。如果为 null，将获取全部数据，顺序为数据库原生顺序<br>
     *            只有在调用这个函数的时候， cnd.limit 才会生效
     * @return 对象列表
     */
    @Override
    public default List<T> query(Condition cnd) {
        return dao().query(getEntityType(), cnd);
    }

    /**
     * 对一组对象进行迭代，这个接口函数非常适用于很大的数据量的集合，因为你不可能把他们都读到内存里
     * 
     * @param cnd
     *            WHERE 条件。如果为 null，将获取全部数据，顺序为数据库原生顺序
     * @param callback
     *            处理回调
     * @return 一共迭代的数量
     */
    public default int each(Condition cnd, Each<T> callback) {
        return dao().each(getEntityType(), cnd, callback);
    }

    /**
     * 对某一个对象字段，进行计算。
     * 
     * @param funcName
     *            计算函数名，请确保你的数据库是支持这个函数的
     * @param fieldName
     *            对象 java 字段名
     * @return 计算结果
     */
    public default int func(String funcName, String fieldName) {
        return dao().func(getEntityType(), funcName, fieldName);
    }

    /**
     * 对某一个对象字段，进行计算。
     * 
     * @param funcName
     *            计算函数名，请确保你的数据库是支持这个函数的
     * @param fieldName
     *            对象 java 字段名
     * @param cnd
     *            过滤条件
     * @return 计算结果
     */
    public default int func(String funcName, String fieldName, Condition cnd) {
        return dao().func(getEntityType(), funcName, fieldName, cnd);
    }

    /**
     * 从一个 ResultSet 中获取一个对象。
     * <p>
     * 因为 Dao 接口可以知道一个 POJO 的映射细节，这个函数可以帮你节省一点体力。
     * 
     * @param rs
     *            结果集
     * @param fm
     *            字段过滤器
     * @return 对象
     */
    public default T getObject(ResultSet rs, FieldMatcher fm) {
        return dao().getObject(getEntityType(), rs, fm);
    }

    public default T getObject(ResultSet rs, FieldMatcher fm, String prefix) {
        return dao().getObject(getEntityType(), rs, fm, prefix);
    }

    public default List<T> _query(final Condition cnd, final Pager pager, FieldMatcher matcher) {
        return dao().query(getEntityType(), cnd, pager, matcher);
    }

    public default List<T> _query(final Condition cnd, final Pager pager, String regex) {
        return dao().query(getEntityType(), cnd, pager, regex);
    }

    public default T _insert(T obj) {
        return dao().insert(obj);
    }

    public default T _fastInsert(T obj) {
        return dao().fastInsert(obj);
    }

    public default T _insert(T obj, FieldFilter filter) {
        return dao().insert(obj, filter);
    }

    public default T _insert(T t, boolean ignoreNull, boolean ignoreZero, boolean ignoreBlankStr) {
        return dao().insert(t, ignoreNull, ignoreZero, ignoreBlankStr);
    }

    public default T _insertWith(T obj, String regex) {
        return dao().insertWith(obj, regex);
    }

    public default T _insertLinks(T obj, String regex) {
        return dao().insertLinks(obj, regex);
    }

    public default T _insertRelation(T obj, String regex) {
        return dao().insertRelation(obj, regex);
    }

    public default int _update(T obj) {
        return dao().update(obj);
    }

    public default int _update(T obj, String regex) {
        return dao().update(obj, regex);
    }

    public default int _updateIgnoreNull(Object obj) {
        return dao().updateIgnoreNull(obj);
    }

    public default T _updateWith(T obj, String regex) {
        return dao().updateWith(obj, regex);
    }

    public default T _updateLinks(T obj, String regex) {
        return dao().updateLinks(obj, regex);
    }

    public default int _delete(T obj) {
        return dao().delete(obj);
    }

    public default int _deleteWith(T obj, String regex) {
        return dao().deleteWith(obj, regex);
    }

    public default int _deleteLinks(T obj, String regex) {
        return dao().deleteLinks(obj, regex);
    }

    public default T _fetch(T obj) {
        return dao().fetch(obj);
    }

    public default T _fetchLinks(T obj, String regex) {
        return dao().fetchLinks(obj, regex);
    }

    public default T _fetchLinks(T obj, String regex, Condition cnd) {
        return dao().fetchLinks(obj, regex, cnd);
    }

    public default T _clearLinks(T obj, String regex) {
        return dao().clearLinks(obj, regex);
    }

    public default void setExpert(T obj) throws Exception {
        dao().setExpert(obj);
    }

    public default List<T> queryByNutz() {
        return dao().query(getEntityType(), null);
    }

}
