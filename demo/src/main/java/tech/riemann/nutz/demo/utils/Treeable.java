package tech.riemann.nutz.demo.utils;

/**
 * 
 * @author kerbores(kerbores@riemann.tech)
 *
 * @param <T>
 */
public interface Treeable<T> {

    T getKey();

    T getParentKey();

    String getName();

    String getDescription();

    public default Tree<T> toTree() {
        return Tree.<T> builder()
                   .key(getKey())
                   .parentKey(getParentKey())
                   .name(getName())
                   .description(getDescription())
                   .originData(this)
                   .build();
    }

}
