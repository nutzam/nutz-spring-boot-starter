package tech.riemann.nutz.demo.entity.acl;

import java.util.Optional;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.json.JsonField;

import club.zhcs.enums.Codebook;
import club.zhcs.enums.ICodeBook;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;
import tech.riemann.nutz.demo.entity.IdBaseEntity;

/**
 * 用户
 *
 * @author Kerbores(kerbores@gmail.com)
 *
 * @since 2022-09-13 12:25:23
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Table("t_acl_user")
@Comment("用户")
@Schema(name = "User", description = "用户")
public class User extends IdBaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "用户名", required = true)
    @Name
    @Column("u_name")
    @Comment("用户名")
    private String name;

    @Schema(description = "密码", required = true)
    @Column("u_password")
    @Comment("密码")
    private String password;

    @Schema(description = "手机号", required = true)
    @Column("u_mobile")
    @Comment("手机号")
    private String mobile;

    @Schema(description = "性别", required = true)
    @Column("u_sex")
    @Comment("性别")
    @Default
    private Sex sex = Sex.FEMALE;

    @Schema(description = "邮箱", required = true)
    @Column("u_email")
    @Comment("邮箱")
    private String email;

    @Schema(description = "真实姓名", required = false)
    @Column("u_full_name")
    @Comment("真实姓名")
    private String fullName;

    @Getter
    @AllArgsConstructor
    public enum Sex implements ICodeBook {
        /**
         * 
         */
        MALE("male", "男"),
        /**
         * 
         */
        FEMALE("female", "女");

        String code;
        String description;

    }

    @JsonField
    public Codebook getSexInfo() {
        return Optional.of(getSex()).orElse(Sex.FEMALE).build();
    }

    public void setSexInfo(Codebook sexInfo) {
        setSex(Sex.valueOf(sexInfo.getName()));
    }
}
