package tech.riemann.demo.dto;

import java.util.List;

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
public class B {

    A<String, List<Long>> kk;

}
