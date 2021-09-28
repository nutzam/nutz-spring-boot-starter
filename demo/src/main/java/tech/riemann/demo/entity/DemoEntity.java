package tech.riemann.demo.entity;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.lang.Times;
import org.nutz.spring.boot.service.entity.IdEntity;

import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author mdp 代码生成器
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DemoEntity extends IdEntity {

    private static final long serialVersionUID = 1L;

    @Column("created_by")
    @Comment("创建人")
    private String creater;

    @Column("created_time")
    @Comment("创建时间")
    @Default
    private Date createTime = Times.now();

    @Column("updated_by")
    @Comment("更新人")
    private String updater;

    @Column("updated_time")
    @Comment("更新时间")
    @Default
    private Date updateTime = Times.now();

}
