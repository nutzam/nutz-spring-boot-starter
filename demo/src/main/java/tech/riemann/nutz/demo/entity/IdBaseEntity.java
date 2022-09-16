package tech.riemann.nutz.demo.entity;

import java.time.LocalDateTime;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.spring.boot.service.entity.IdEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * 
 * @author Kerbores(kerbores@gmail.com)
 *
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class IdBaseEntity extends IdEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Column("created_time")
    @Comment("创建时间")
    @Default
    protected LocalDateTime createdTime = LocalDateTime.now();

    @Column("updated_time")
    @Comment("最后更新时间")
    @Default
    protected LocalDateTime updatedTime = LocalDateTime.now();

    @Schema(description = "创建人")
    @Column("created_by")
    @Comment("创建人")
    @JsonIgnore
    protected String createdBy;

    @Schema(description = "更新人")
    @Column("updated_by")
    @Comment("更新人")
    @JsonIgnore
    protected String updatedBy;

}
