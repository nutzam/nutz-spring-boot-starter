package tech.riemann.demo.entity.acl;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Table;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;
import tech.riemann.demo.entity.DemoEntity;

/**
 * @author wkipy
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@Accessors(chain = true)
@Table("t_login_channel")
@Comment("角色")
@Schema(name = "LoginChannel", description = "社会化登录渠道")
public class LoginChannel extends DemoEntity {
    /**
    *
    */
    private static final long serialVersionUID = 1L;

    @Schema(description = "openid", required = true)
    @Column("lc_openid")
    @Comment("openid")
    String openid;

    @Schema(description = "用户id", required = true)
    @Column("lc_user_id")
    @Comment("用户id")
    long userId;

    @Schema(description = "渠道", required = true)
    @Column("lc_channel")
    @Comment("渠道")
    Channel channel;

    public enum Channel {
        /**
         * 公众号网页授权
         */
        MP,
        /**
         * 微信小程序
         */
        MINIAPP,
        /**
         * 微信扫码登录
         */
        WECHAT_SCAN,
        /**
         * 微信社会化登录(APP登录)
         */
        WECHAT
    }

}
