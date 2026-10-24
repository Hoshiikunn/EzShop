package cn.hoshiikunn.ezshop.modules.security.shiro;

import cn.hoshiikunn.ezshop.result.Result;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static cn.hoshiikunn.ezshop.result.ResultCode.UNAUTHORIZED;

/**
 * JWT 认证过滤器
 * <p>
 * 从请求头提取令牌 → 封装 {@code JwtToken} → 交给 Shiro (JwtRealm 调 TokenService 校验) → 失败统一写回 401
 */
@Slf4j
public class JwtFilter extends AuthenticatingFilter {

    /**
     * Jackson 序列化器
     */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    /**
     * 令牌请求头前缀
     */
    private static final String BEARER_PREFIX = "Bearer ";

    /**
     * 判断是否允许访问 (已认证或可放行时触发)
     * <p>
     * 仅放行跨域预检请求 (OPTIONS) 预检不携带自定义请求头
     *
     * @param request     Servlet 请求
     * @param response    Servlet 响应
     * @param mappedValue 过滤器链配置参数
     * @return true 放行 (不走认证) ，false 进入 {@code onAccessDenied()} 执行认证
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        // 跨域预检请求不携带自定义头，直接放行
        return "OPTIONS".equals(((HttpServletRequest) request).getMethod());
    }

    /**
     * 执行认证 (未认证时触发)
     * <p>从请求头提取令牌：</p>
     * <p>无令牌 → 记录日志并写回 401 (未认证)</p>
     * <p>有令牌 → {@code executeLogin()} 交给 Shiro 认证 (内部回调 {@code createToken()} 封装，由 JwtRealm 校验)</p>
     *
     * @param request  Servlet 请求
     * @param response Servlet 响应
     * @return true 认证成功，继续执行后续链路；false 响应已写回，阻断请求
     * @throws Exception 认证过程中的异常 (认证失败由 {@code onLoginFailure()} 处理)
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        // 从请求头提取令牌
        String token = getRequestToken(httpRequest);

        // 无令牌 → 401 (未认证)
        if (token == null) {
            log.warn("请求未携带令牌 | uri = {}", httpRequest.getRequestURI());
            responseUnauthorized(response);
            return false;
        }

        // 有令牌 -> 封装提交给 Shiro
        return executeLogin(request, response);
    }

    /**
     * 构造认证令牌
     * <p>由 {@code executeLogin()} 内部回调，将提取的令牌字符串封装为 {@code JwtToken} 交给 Shiro</p>
     *
     * @param request  Servlet 请求
     * @param response Servlet 响应
     * @return 封装后的 {@code JwtToken}
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        return new JwtToken(getRequestToken((HttpServletRequest) request));
    }

    /**
     * 认证失败回调
     * <p>{@code executeLogin()} 抛出认证异常时触发，写回 401 统一结构并阻断请求</p>
     * <p>JwtRealm 抛出的 {@code ExpiredCredentialsException} 本身即 AuthenticationException</p>
     *
     * @param token    认证时传入的令牌对象
     * @param e        Shiro 抛出的认证异常
     * @param request  Servlet 请求
     * @param response Servlet 响应
     * @return false 阻断请求
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e,
                                     ServletRequest request, ServletResponse response) {
        log.warn("令牌校验失败 | uri = {}", ((HttpServletRequest) request).getRequestURI());
        responseUnauthorized(response);

        return false;
    }

    /**
     * 从 Authorization 头提取令牌 (Bearer xxx)
     *
     * @param request HTTP 请求
     * @return 令牌字符串；未携带或格式不符时返回 null
     */
    private String getRequestToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StrUtil.isNotBlank(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }

        return null;
    }

    /**
     * 写回 401 统一结构
     * <p>
     * 过滤器层不经过 @RestControllerAdvice，必须写响应
     */
    private void responseUnauthorized(ServletResponse response) {
        try {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setContentType("application/json;charset=UTF-8");
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write(OBJECT_MAPPER.writeValueAsString(
                    new Result<>().error(UNAUTHORIZED)));
        } catch (IOException e) {
            throw new RuntimeException("401 响应写入失败", e);
        }
    }
}
