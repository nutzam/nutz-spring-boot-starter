package tech.riemann.nutz.demo.config.auth;

import java.util.List;

import org.nutz.lang.Times;
import org.nutz.lang.random.R;
import org.nutz.lang.util.NutMap;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.experimental.UtilityClass;

/**
 * 
 * @author Kerbores(kerbores@gmail.com)
 *
 */
@UtilityClass
public class JwtTokenUtil {
    private static final String SECRET = "nutz";
    private static final int EXPIRATION_DAY = 30; // 30天
    private static final int REFRESH_EXPIRATION_DAY = 60; // 60天

    /**
     * 生成token
     * 
     * @param userName
     *            用户名
     * @param roles
     *            角色
     * @param permissions
     *            权限
     * @return token
     */
    public String generateToken(String userName, List<String> roles, List<String> permissions) {
        return Jwts.builder()
                   .setClaims(new DefaultClaims(NutMap.NEW()
                                                      .addv("roles", roles)
                                                      .addv("userName", userName)
                                                      .addv("permissions", permissions))
                                                                                        .setSubject(userName)
                                                                                        .setAudience(userName)
                                                                                        .setExpiration(Times.nextDay(Times.now(), EXPIRATION_DAY))
                                                                                        .setId(R.UU32())
                                                                                        .setIssuedAt(Times.now())
                                                                                        .setIssuer("nutz")
                                                                                        .setNotBefore(Times.now()))
                   .signWith(SignatureAlgorithm.HS512, SECRET)
                   .compact();
    }

    public String generateRefreshToken(String userName) {
        return Jwts.builder()
                   .setClaims(new DefaultClaims(NutMap.NEW()
                                                      .addv("userName", userName))
                                                                                  .setSubject(userName)
                                                                                  .setAudience(userName)
                                                                                  .setExpiration(Times.nextDay(Times.now(), REFRESH_EXPIRATION_DAY))
                                                                                  .setId(R.UU32())
                                                                                  .setIssuedAt(Times.now())
                                                                                  .setIssuer("nutz")
                                                                                  .setNotBefore(Times.now()))
                   .signWith(SignatureAlgorithm.HS512, SECRET)
                   .compact();
    }

    public String getName(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().getSubject();
    }

}
