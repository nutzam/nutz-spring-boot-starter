package org.nutz.spring.boot.service;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.dao.FieldFilter;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Entity;
import org.nutz.dao.entity.MappingField;
import org.nutz.dao.entity.Record;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.cri.Exps;
import org.nutz.dao.util.cri.SqlExpressionGroup;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.lang.Lang;
import org.nutz.lang.Mirror;
import org.nutz.lang.util.NutMap;
import org.nutz.log.Logs;
import org.nutz.spring.boot.service.entity.Pagination;

/**
 * 
 * @author kerbores(kerbores@gmail.com)
 */
public interface ExtService<T extends Serializable> {

    /**
     * 获取dao实例
     * 
     * @return dao实例
     */
    Dao dao();

    /**
     * 根据条件查询列表
     * 
     * @param cnd
     *            条件
     * @return 数据列表
     */
    List<T> query(Condition cnd);

    /**
     * 获取泛型类型class
     * 
     * @return 泛型class
     */
    Class<T> getEntityType();

    /**
     * 根据条件删除
     * 
     * @param cnd
     *            条件
     * @return 删除的数据记录数
     */
    int clear(Condition cnd);

    /**
     * 获取泛型的Entity描述
     * 
     * @return Entity描述
     */
    Entity<T> getEntity();

    /**
     * 根据条件进行分页查询
     * 
     * @param cnd
     *            条件
     * @param pager
     *            分页
     * @return 数据列表
     */
    List<T> query(Condition cnd, org.nutz.dao.pager.Pager pager);

    /**
     * 根据条件进行计数
     * 
     * @param cnd
     *            条件
     * @return 数量
     */
    int count(Condition cnd);

    /**
     * 默认分页大小
     * 
     * @return 分页大小,默认15
     */
    default int getDefaultPageSize() {
        return 15;
    }

    /**
     * 保存实体
     *
     * @param t
     *            待保存实体
     * @return 保存后的实体, 根据配置可能将产生 id 等其他属性
     */
    default T insert(T t) {
        return dao().insert(t);
    }

    /**
     * 保存数据
     * 
     * @param ts
     *            数据列表
     * @return 存入后的数据列表,结果返回 @see {@link Dao#fastInsert(Object)}
     * 
     */
    default List<T> insert(List<T> ts) {
        return dao().fastInsert(ts);
    }

    /**
     * 保存对象指定字段
     *
     * @param obj
     *            待保存对象
     * @param filter
     *            字段过滤器
     * @return 保存后的对象
     */
    default T save(final T obj, FieldFilter filter) {
        return dao().insert(obj, filter);
    }

    /**
     * 保存
     *
     * @param tableName
     *            表名
     * @param chain
     *            数据链
     */
    default void save(String tableName, Chain chain) {
        dao().insert(tableName, chain);
    }

    /**
     * 保存
     *
     * @param classOfT
     *            类
     * @param chain
     *            数据链
     */
    default void save(Class<?> classOfT, Chain chain) {
        dao().insert(classOfT, chain);
    }

    /**
     * 保存
     *
     * @param t
     *            数据对象
     * @param ignoreNull
     *            是否忽略空值
     * @param ignoreZero
     *            是否忽略零值
     * @param ignoreBlankStr
     *            是否忽略空字符串
     * @return 保存后的对象
     */
    default T save(final T t, boolean ignoreNull, boolean ignoreZero, boolean ignoreBlankStr) {
        return dao().insert(t, ignoreNull, ignoreZero, ignoreBlankStr);
    }

    /**
     * 保存指定字段
     *
     * @param obj
     *            待保存对象
     * @param regex
     *            字段正则
     * @return 保存后的对象
     */
    default T insertWith(T obj, String regex) {
        return dao().insertWith(obj, regex);
    }

    /**
     * 保存关联数据
     *
     * @param obj
     *            对象
     * @param regex
     *            管理字段正则
     * @return 保存后的对象
     */
    default T insertLinks(T obj, String regex) {
        return dao().insertLinks(obj, regex);
    }

    /**
     * 插入中间表
     *
     * @param obj
     *            对象
     * @param regex
     *            正则
     * @return 保存后的对象
     */
    default T insertRelation(T obj, String regex) {
        return dao().insertRelation(obj, regex);
    }

    /**
     * 查询全部
     *
     * @return 对象列表
     */
    default List<T> all() {
        return query(null);
    }

    /**
     * 根据条件查询
     * 
     * @param cnd
     *            条件
     * @return 对象列表
     */
    default List<T> list(Condition cnd) {
        return query(cnd);
    }

    /**
     * 分页查询
     *
     * @param condition
     *            条件
     * @param currentPage
     *            当前页码
     * @param pageSize
     *            页面大小
     * @return 对象列表
     */
    default List<T> list(Condition condition, int currentPage, int pageSize) {
        return dao().query(getEntityType(), condition, dao().createPager(currentPage, pageSize));
    }

    /**
     * 分页查询
     *
     * @param condition
     *            条件
     * @param currentPage
     *            页码
     * @return 对象列表
     */
    default List<T> list(Condition condition, int currentPage) {
        return list(condition, currentPage, getDefaultPageSize());
    }

    /**
     * 执行sql并返回对象列表
     * 
     * @param sql
     *            待执行sql
     * @return 对象列表
     */
    default List<T> list(Sql sql) {
        return list(sql, getEntityType());
    }

    /**
     * 执行sql并返回对象列表
     * 
     * @param <E>
     *            类型泛型
     * @param sql
     *            待执行sql
     * @param clazz
     *            返回的类型
     * @return 对象列表
     */
    default <E> List<E> list(Sql sql, Class<E> clazz) {
        sql.setCallback(Sqls.callback.entities());
        sql.setEntity(dao().getEntity(clazz));
        dao().execute(sql);
        return sql.getList(clazz);
    }

    /**
     * 执行sql并返回记录列表
     * 
     * @param sql
     *            待执行sql
     * @return 数据记录列表
     */
    default List<Record> listAsRecord(Sql sql) {
        sql.setCallback(Sqls.callback.records());
        dao().execute(sql);
        return sql.getList(Record.class);
    }

    /**
     * 执行sql并返回map列表,列别名作为key
     * 
     * @param sql
     *            待执行sql
     * @return 数据记录列表
     */
    default List<NutMap> listAsMap(Sql sql) {
        sql.setCallback(Sqls.callback.maps());
        dao().execute(sql);
        return sql.getList(NutMap.class);
    }

    /**
     * 根据指定字段查询(仅限唯一属性,非唯一属性返回第一个满足条件的数据)
     *
     * @param field
     *            字段
     * @param value
     *            值
     * @return 单个对象
     */
    default T fetchByField(String field, Object value) {
        return dao().fetch(getEntityType(), Cnd.where(field, "=", value));
    }

    /**
     * 执行sql并返回记录
     * 
     * @param sql
     *            待执行sql
     * @return 数据记录对象
     */
    default Record fetchAsRecord(Sql sql) {
        sql.setCallback(Sqls.callback.record());
        dao().execute(sql);
        return sql.getObject(Record.class);
    }

    /**
     * 执行sql并返回对象
     * 
     * @param sql
     *            待执行sql
     * @return 数据对象
     */
    default T fetch(Sql sql) {
        return fetch(sql, getEntityType());
    }

    /**
     * 执行sql并返回对象
     * 
     * @param <E>
     *            对象类型
     * @param sql
     *            待执行sql
     * @param claszz
     *            类型
     * @return 数据对象
     */
    default <E> E fetch(Sql sql, Class<E> claszz) {
        sql.setCallback(Sqls.callback.entity());
        sql.setEntity(dao().getEntity(claszz));
        dao().execute(sql);
        return sql.getObject(claszz);
    }

    /**
     * 执行删除或者更新语句
     * 
     * @param sql
     *            待执行sql
     * @return 如果当前语句为 DELETE | UPDATE | INSERT，返回执行后所影响的记录数。否则返回 -1
     */
    default int executDeleteOrUpdateSql(Sql sql) {
        sql.setCallback(Sqls.callback.integer());
        dao().execute(sql);
        return sql.getUpdateCount();
    }

    /**
     * 执行count sql
     * 
     * @param sql
     *            待执行sql,请确保sql为count且仅返回一列count数
     * @return 数量
     */
    default int executCountSql(Sql sql) {
        sql.setCallback(Sqls.callback.integer());
        dao().execute(sql);
        return sql.getInt(0);
    }

    /**
     * 分页查询
     *
     * @param page
     *            页码
     * @return 分页数据对象
     */
    default Pagination<T> searchByPage(int page) {
        return searchByPage(page, null);
    }

    /**
     * 分页查询
     *
     * @param page
     *            页码
     * @param condition
     *            条件
     * @return 分页对象
     */
    default Pagination<T> searchByPage(int page, Condition condition) {
        return searchByPage(page, getDefaultPageSize(), condition);
    }

    /**
     * 根据条件分页查询
     * 
     * @param page
     *            页码
     * @param pageSize
     *            页面大小
     * @param condition
     *            条件
     * @return 分页数据对象
     */
    default Pagination<T> searchByPage(int page, int pageSize, Condition condition) {
        return Pagination.<T> instance(page, pageSize)
                         .dataList(query(condition, new Pagination<T>(page, pageSize)))
                         .recordCount(count(condition));
    }

    /**
     * 根据条件及关键词进行分页查询
     * 
     * @param key
     *            关键词
     * @param page
     *            页码
     * @param cnd
     *            条件
     * @param fields
     *            关键词匹配的字段列表
     * @return 分页数据对象
     */
    default Pagination<T> searchByKeyAndPage(String key, int page, Cnd cnd, String... fields) {
        return searchByKeyAndPage(key, page, getDefaultPageSize(), cnd, fields);
    }

    /**
     * 根据条件及关键词进行分页查询
     * 
     * @param key
     *            关键词
     * @param page
     *            页码
     * @param pageSize
     *            页面大小
     * @param cnd
     *            条件
     * @param fields
     *            关键词匹配的字段列表
     * @return 分页数据对象
     */
    default Pagination<T> searchByKeyAndPage(String key, int page, int pageSize, Cnd cnd, String... fields) {
        if (cnd == null) {
            cnd = Cnd.NEW();
        }
        String searchKey = String.format("%%%s%%", key);
        SqlExpressionGroup expressionGroup = Exps.begin();
        int index = 0;
        for (String field : fields) {
            if (index == 0) {
                expressionGroup.and(field, "like", searchKey);
            } else {
                expressionGroup.or(field, "like", searchKey);
            }
            index++;
        }
        return searchByPage(page,
                            pageSize,
                            expressionGroup.getExps() == null || expressionGroup.getExps().isEmpty() ? cnd

                                                                                                     : cnd.and(expressionGroup));
    }

    /**
     * 关键词搜索
     *
     * @param key
     *            关键词
     * @param page
     *            页码
     * @param pageSize
     *            页面大小
     * @param fields
     *            检索字段列表
     * @return 分页对象
     */
    default Pagination<T> searchByKeyAndPage(String key, int page, int pageSize, String... fields) {
        return searchByKeyAndPage(key, page, pageSize, null, fields);
    }

    /**
     * 关键词搜索
     *
     * @param key
     *            关键词
     * @param page
     *            页码
     * @param fields
     *            检索字段列表
     * @return 分页对象
     */
    default Pagination<T> searchByKeyAndPage(String key, int page, String... fields) {
        return searchByKeyAndPage(key, page, getDefaultPageSize(), null, fields);
    }

    /**
     * 更新对象
     * 
     * @param obj
     *            待更新对象
     * @return 影响的记录条数
     */
    default int update(T obj) {
        return dao().update(obj);
    }

    /***
     * 更新字段的指定字段
     * 
     * @param obj
     *            待更新对象
     * @param regex
     *            字段正则
     * @return 影响的记录条数
     */
    default int updateFields(final T obj, String regex) {
        return dao().update(obj, regex);
    }

    /**
     * 更新对象的非空字段
     * 
     * @param obj
     *            待更新对象
     * @return 影响的记录条数
     */
    default int updateIgnoreNull(final T obj) {
        return dao().updateIgnoreNull(obj);
    }

    default T updateWith(T obj, final String regex) {
        return dao().updateWith(obj, regex);
    }

    default T updateLinks(T obj, final String regex) {
        return dao().updateLinks(obj, regex);
    }

    /**
     * 更新对象的指定字段,使用id或者主键作为条件
     * 
     * @param t
     *            待更新对象
     * @param fields
     *            字段列表
     * @return 是否更新成功
     */
    default boolean update(T t, String... fields) {
        return dao().update(t.getClass(), makeChain(t, fields), getCnd(t)) == 1;
    }

    default Chain makeChain(T t, String[] fields) {
        NutMap map = NutMap.NEW();
        // 获取数据库字段名称和对象值键值对
        for (String field : fields) {
            MappingField mf = getEntity().getField(field);
            map.put(mf.getColumnName(), mf.getValue(t));
        }
        return Chain.from(map);
    }

    default Condition getCnd(T t) {
        Mirror<T> clazzMirror = Mirror.me(t);// 获取类型的镜像
        Field idField = null;
        try {
            idField = clazzMirror.getField(Id.class);
        }
        catch (NoSuchFieldException e) {
            throw Lang.wrapThrow(e);
        }
        String fieldName = idField.getName();
        Object value = clazzMirror.getValue(t, idField);
        return Cnd.where(fieldName, "=", value);
    }

    /**
     * 根据条件更新数据
     * 
     * @param t
     *            更新目标数据样本
     * @param cnd
     *            条件
     * @param fields
     *            待更新的字段列表
     * @return 影响的记录条数
     */
    default int update(T t, Condition cnd, String... fields) {
        Arrays.sort(fields);
        NutMap map = Lang.map(Json.toJson(t, JsonFormat.compact().ignoreJsonShape()));
        NutMap data = NutMap.NEW();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            if (Arrays.binarySearch(fields, key) >= 0) {
                data.put(key, map.get(key));
            }
        }
        try {
            return dao().update(t.getClass(), Chain.from(data), cnd);
        }
        catch (Exception e) {
            Logs.get().error(e);
            return 0;
        }
    }

    /**
     * 根据条件更新数据
     * 
     * @param t
     *            更新目标数据样本
     * @param cnd
     *            条件
     * @return 影响的记录条数
     */
    default int update(T t, Condition cnd) {
        try {
            return dao().update(t.getClass(), Chain.from(t), cnd);
        }
        catch (Exception e) {
            Logs.get().error(e);
            return 0;
        }
    }

    /**
     * 清除全部数据
     *
     * @return 记录条数
     */
    default int clear() {
        return clear(null);
    }

    /**
     * 删除关联数据
     *
     * @param obj
     *            对象
     * @param regex
     *            正则
     * @return 是否删除成功
     */
    default T clearLinks(T obj, final String regex) {
        return dao().clearLinks(obj, regex);
    }

    /**
     * 删除对象
     * 
     * @param obj
     *            待删除对象
     * @return 影响的记录条数
     */
    default int delete(T obj) {
        return dao().delete(obj);
    }

    default int deleteWith(T obj, final String regex) {
        return dao().deleteWith(obj, regex);
    }

    default int deleteLinks(T obj, final String regex) {
        return dao().deleteLinks(obj, regex);
    }

    /**
     * 创建sql对象
     *
     * @param key
     *            sqlManager中的sql key
     * @return Sql 对象
     */
    default Sql createSql(String key) {
        return dao().sqls().create(key);
    }

    /**
     * 创建sql对象
     *
     * @param key
     *            sqlManager中的sql key
     * @return Sql 对象
     */
    default Sql sql(String key) {
        return dao().sqls().create(key);
    }

    /**
     * 获取entity
     * 
     * @param <E>
     *            类型
     * @param clazz
     *            entity字节码
     * @return entity实例
     */
    default <E> org.nutz.dao.entity.Entity<E> entity(Class<E> clazz) {
        return dao().getEntity(clazz);
    }

    /**
     * 执行sql
     * 
     * @param sql
     *            待执行sql
     * @return 执行后的sql对象
     */
    default Sql excute(Sql sql) {
        return dao().execute(sql);
    }

    /**
     * 最大值
     * 
     * @param field
     *            字段
     * @param condition
     *            条件
     * @return 满足条件的字段最大值
     */
    default int max(String field, Cnd condition) {
        return dao().func(getEntityType(), "max", field, condition);
    }

    /**
     * 最小值
     * 
     * @param field
     *            字段
     * @param condition
     *            条件
     * @return 满足条件的字段最小值
     */
    default int min(String field, Cnd condition) {
        return dao().func(getEntityType(), "min", field, condition);
    }

    /**
     * 平均值
     * 
     * @param field
     *            字段
     * @param condition
     *            条件
     * @return 满足条件的字段平均值
     */
    default int avg(String field, Cnd condition) {
        return dao().func(getEntityType(), "avg", field, condition);
    }

    /**
     * 求和
     * 
     * @param field
     *            字段
     * @param condition
     *            条件
     * @return 满足条件的字段求和值
     */
    default int sum(String field, Cnd condition) {
        return dao().func(getEntityType(), "sum", field, condition);
    }

    /**
     * 最大值
     * 
     * @param field
     *            字段
     * @return 字段最大值
     */
    default int max(String field) {
        return max(field, null);
    }

    /**
     * 最小值
     * 
     * @param field
     *            字段
     * @return 字段最小值
     */
    default int min(String field) {
        return min(field, null);
    }

    /**
     * 平均值
     * 
     * @param field
     *            字段
     * @return 字段平均值
     */
    default int avg(String field) {
        return avg(field, null);
    }

    /**
     * 求和
     * 
     * @param field
     *            字段
     * @return 字段求和值
     */
    default int sum(String field) {
        return sum(field, null);
    }

}
