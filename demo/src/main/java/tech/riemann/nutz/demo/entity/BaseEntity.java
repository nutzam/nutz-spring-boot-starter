package tech.riemann.nutz.demo.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
@EqualsAndHashCode(of = "id")
public class BaseEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Id
    protected Long id;

    @TableField(value = "created_time", fill = FieldFill.INSERT)
    @Column("created_time")
    @Comment("创建时间")
    @Default
    protected LocalDateTime createdTime = LocalDateTime.now();

    @TableField(value = "updated_time", fill = FieldFill.UPDATE)
    @Column("updated_time")
    @Comment("最后更新时间")
    @Default
    protected LocalDateTime updatedTime = LocalDateTime.now();

    @Schema(description = "创建人")
    @TableField("created_user")
    @Column("created_user")
    @Comment("创建人")
    @JsonIgnore
    protected String createdBy;

    @Schema(description = "更新人")
    @TableField("updated_user")
    @Column("updated_user")
    @Comment("更新人")
    @JsonIgnore
    protected String updatedBy;

}
