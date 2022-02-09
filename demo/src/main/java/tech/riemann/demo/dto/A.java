package tech.riemann.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wkipy
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class A<T, S> {

    T t;

    S s;
}
