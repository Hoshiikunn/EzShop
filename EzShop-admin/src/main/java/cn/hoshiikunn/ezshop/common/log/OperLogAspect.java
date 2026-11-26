package cn.hoshiikunn.ezshop.common.log;

import cn.hoshiikunn.ezshop.common.annotation.OperLog;
import cn.hoshiikunn.ezshop.exception.BusinessException;
import cn.hoshiikunn.ezshop.modules.security.user.SecurityUser;
import cn.hoshiikunn.ezshop.modules.sys.entity.SysOperLogEntity;
import cn.hoshiikunn.ezshop.modules.sys.entity.SysUserEntity;
import cn.hoshiikunn.ezshop.modules.sys.enums.OperStatusEnum;
import cn.hoshiikunn.ezshop.modules.sys.service.SysOperLogService;
import cn.hoshiikunn.ezshop.modules.sys.service.SysUserService;
import cn.hoshiikunn.ezshop.utils.AddressUtils;
import cn.hoshiikunn.ezshop.utils.IpUtils;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static cn.hoshiikunn.ezshop.modules.sys.enums.OperStatusEnum.FAIL;
import static cn.hoshiikunn.ezshop.modules.sys.enums.OperStatusEnum.SUCCESS;

/**
 * 操作日志切面类
 */
@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class OperLogAspect {

    private final SysOperLogService operLogService;
    private final SysUserService userService;

    /**
     * 全局敏感字段，序列化后值替换为 ***
     */
    private static final List<String> SENSITIVE_KEYS =
            Arrays.asList("password", "oldPassword", "newPassword");
    /**
     * 请求参数最大保留长度
     */
    private static final int MAX_PARAMS_LENGTH = 2000;
    /**
     * 失败原因最大保留长度
     */
    private static final int MAX_ERROR_MSG_LENGTH = 500;
    /**
     * 解析失败兜底文案
     */
    private static final String UNKNOWN = "未知";

    /**
     * 记录操作日志
     * <p>
     * 成功与失败都会留痕，业务异常原样抛出
     *
     * @param joinPoint 切点
     * @param operLog 操作日志注解
     * @return 业务方法返回值
     * @throws Throwable 业务方法异常原样抛出
     */
    @Around("@annotation(operLog)")
    public Object around(ProceedingJoinPoint joinPoint, OperLog operLog) throws Throwable {
        long beginTime = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            // 成功后写日志
            saveOperLog(joinPoint, operLog, SUCCESS, null,
                    System.currentTimeMillis() - beginTime);
            return result;
        } catch (Throwable e) {
            // 失败也要留痕，记录后原样抛出
            saveOperLog(joinPoint, operLog, FAIL, resolveErrorMsg(e),
                    System.currentTimeMillis() - beginTime);
            throw e;
        }
    }

    /**
     * 组装并保存操作日志
     *
     * @param joinPoint 切点
     * @param operLog 操作日志注解
     * @param status 操作状态
     * @param errorMsg 失败原因，成功时为 null
     * @param elapsed 耗时 (毫秒)
     */
    private void saveOperLog(ProceedingJoinPoint joinPoint, OperLog operLog,
                             OperStatusEnum status, String errorMsg, long elapsed) {
        try {
            HttpServletRequest request = getRequest();
            String ip = request == null ? null : IpUtils.getClientIp(request);

            SysOperLogEntity entity = new SysOperLogEntity();
            entity.setModule(operLog.module());
            entity.setOperation(operLog.operation());
            Long userId = SecurityUser.getUserId();
            entity.setUserId(userId);
            entity.setUsername(resolveUsername(userId));
            entity.setRequestMethod(request == null ? null : request.getMethod());
            entity.setRequestUri(request == null ? null : request.getRequestURI());
            entity.setIp(ip);
            entity.setOperLocation(ip == null ? null : AddressUtils.getRealAddress(ip));
            entity.setRequestParams(resolveParams(joinPoint, operLog.excludeParamNames()));
            entity.setStatus(status.getValue());
            entity.setErrorMsg(errorMsg);
            entity.setElapsedMs((int) elapsed);
            operLogService.save(entity);
        } catch (Exception e) {
            log.warn("操作日志记录失败: {}", e.getMessage());
        }
    }

    /**
     * 解析失败信息
     * <p>
     * 业务异常只记业务原因，其余异常记类名与消息，不记堆栈
     *
     * @param e 业务执行抛出的异常
     * @return 失败原因，超长时截断
     */
    private String resolveErrorMsg(Throwable e) {
        // 业务异常只记业务原因
        if (e instanceof BusinessException) {
            return StrUtil.subPre(e.getMessage(), MAX_ERROR_MSG_LENGTH);
        }
        // NPE 等无消息异常至少保留类名
        String msg = StrUtil.isBlank(e.getMessage())
                ? e.getClass().getSimpleName()
                : e.getClass().getSimpleName() + "：" + e.getMessage();
        return StrUtil.subPre(msg, MAX_ERROR_MSG_LENGTH);
    }

    /**
     * 解析用户名
     * <p>
     * 用户名不存在时返回 "未知"
     *
     * @param userId 用户ID
     * @return 操作人用户名
     */
    private String resolveUsername(Long userId) {
        if (userId == null) {
            return UNKNOWN;
        }
        SysUserEntity user = userService.getById(userId);
        return user == null ? UNKNOWN : user.getUsername();
    }

    /**
     * 获取当前请求
     *
     * @return 当前请求 <p>
     * 非 Web 上下文时返回 {@code null}
     */
    private HttpServletRequest getRequest() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return attributes instanceof ServletRequestAttributes
                ? ((ServletRequestAttributes) attributes).getRequest()
                : null;
    }

    /**
     * 解析请求参数
     * <p>
     * 参数逐个序列化拼接，敏感字段值掩码，超长时截断
     *
     * @param joinPoint 切点
     * @param excludeParamNames 排除的敏感参数字段名
     * @return 请求参数 <p>
     * 序列化失败时返回提示文案
     */
    private String resolveParams(ProceedingJoinPoint joinPoint, String[] excludeParamNames) {
        Object[] args = joinPoint.getArgs();
        if (ArrayUtil.isEmpty(args)) {
            return null;
        }
        try {
            StringBuilder params = new StringBuilder();
            for (Object arg : args) {
                // 不可序列化对象跳过
                if (arg == null || isFilterObject(arg)) {
                    continue;
                }
                params.append(maskSensitive(JSONUtil.toJsonStr(arg), excludeParamNames))
                        .append(' ');
            }
            return StrUtil.subPre(params.toString(), MAX_PARAMS_LENGTH);
        } catch (Exception e) {
            return "参数序列化失败";
        }
    }

    /**
     * 敏感字段值替换为 ***
     *
     * @param json 序列化后的参数 JSON
     * @param excludeParamNames 排除的敏感参数字段名
     * @return 掩码后的参数 JSON
     */
    private String maskSensitive(String json, String[] excludeParamNames) {
        List<String> keys = new ArrayList<>(SENSITIVE_KEYS);
        keys.addAll(Arrays.asList(excludeParamNames));
        for (String key : keys) {
            json = json.replaceAll("(\"" + key + "\"\\s*:\\s*\")[^\"]*(\")", "$1***$2");
        }
        return json;
    }

    /**
     * 判断是否为不可序列化的过滤对象
     *
     * @param o 参数对象
     * @return {@code true} - 跳过序列化 <p>
     * {@code false} - 正常序列化
     */
    private boolean isFilterObject(Object o) {
        return o instanceof MultipartFile || o instanceof HttpServletRequest
                || o instanceof HttpServletResponse || o instanceof BindingResult;
    }
}
