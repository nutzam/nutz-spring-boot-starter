package tech.riemann.nutz.demo.utils;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.nutz.lang.Lang;
import org.nutz.lang.Strings;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

/**
 * 
 * @author kerbores(kerbores@riemann.tech)
 *
 * @param <T>
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@Accessors(chain = true)
@Schema(name = "Tree", description = "树")
public class Tree<T> {

    @Schema(description = "ID", required = true)
    T key;

    @Schema(description = "父级 ID")
    T parentKey;

    @Schema(description = "名称", required = true)
    String name;

    @Schema(description = "描述")
    String description;

    @Schema(description = "原始数据", required = true)
    Treeable<T> originData;

    @Schema(description = "下级列表")
    @JsonInclude(value = Include.NON_EMPTY)
    List<Tree<T>> children;

    /**
     * 转换成树
     * 
     * @param <T>
     *            树的类型
     * @param data
     *            原始数据列表
     * @param rootId
     *            根节点Id
     * @return 从根开始的树
     */
    public static <T> List<Tree<T>> from(@NotNull List<Treeable<T>> data, T rootKey) {
        return data.stream()
                   .map(Treeable::toTree)
                   .filter(t -> Lang.equals(t.getKey(), rootKey))
                   .map(t -> t.setChildren(children(data, t.getKey())))
                   .collect(Collectors.toList());

    }

    public static <T> List<Tree<T>> children(@NotNull List<Treeable<T>> data, T rootKey) {
        return data.stream()
                   .map(Treeable::toTree)
                   .filter(t -> Lang.equals(t.getParentKey(), rootKey))
                   .map(t -> t.setChildren(children(data, t.getKey())))
                   .collect(Collectors.toList());
    }

    /**
     * 
     * @param <T>
     * @param data
     * @param rootKeys
     * @return
     */
    public static <T> List<Tree<T>> from(@NotNull List<Treeable<T>> data, List<T> rootKeys) {
        return data.stream()
                   .map(Treeable::toTree)
                   .filter(t -> rootKeys.stream()
                                        .anyMatch(
                                                  key -> Strings.equals(Strings.safeToString(key, ""), Strings.safeToString(t.getKey(), ""))))
                   .map(t -> t.setChildren(children(data, t.getKey())))
                   .collect(Collectors.toList());

    }

}
