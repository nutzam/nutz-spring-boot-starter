package org.nutz.spring.boot.filepool;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @author Kerbores(kerbores@gmail.com)
 *
 */
@Data
@ConfigurationProperties("nutz.filepool")
public class FilePoolAutoConfigurationProperties {

    boolean enabled = true;

    String path = "~/.temp";

    int size = 0;
}
