package cn.jackbin.SimpleRecord.common.aspect;

import cn.jackbin.SimpleRecord.common.LocalUserId;
import cn.jackbin.SimpleRecord.common.anotations.CommonLog;
import cn.jackbin.SimpleRecord.common.enums.BusinessStatus;
import cn.jackbin.SimpleRecord.common.manager.AsyncManager;
import cn.jackbin.SimpleRecord.common.manager.factory.AsyncFactory;
import cn.jackbin.SimpleRecord.entity.CommonLogDO;
import cn.jackbin.SimpleRecord.utils.IpUtil;
import cn.jackbin.SimpleRecord.utils.ServletUtil;
import cn.jackbin.SimpleRecord.utils.StringUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.PropertyPreFilters;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.protocol.HTTP;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.common.aspect
 * @date: 2021/4/7 21:20
 **/
@Component
@Aspect
@Slf4j
public class LogAspect implements Ordered {
    // 执行顺序，越小越先执行（遵从同心圆的概念）
    private final int order = 100;

    ThreadLocal<String> params = new ThreadLocal<>();

    /** 排除敏感属性字段 */
    public static final String[] EXCLUDE_PROPERTIES = { "password", "oldPassword", "newPassword", "confirmPassword" };

    @Pointcut("@annotation(cn.jackbin.SimpleRecord.common.anotations.CommonLog)")
    public void doHandler(){

    }

    @Before("doHandler()")
    public void before(JoinPoint joinPoint) {
        params.set(getRequestParams());
    }

    /**
     * 处理请求后执行
     * @param joinPoint
     * @return
     */
    @AfterReturning(pointcut = "doHandler()", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Object jsonResult) {
        handleLog(joinPoint, null, jsonResult);
    }

    @AfterThrowing(pointcut = "doHandler()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e, null);
    }

    /**
     * 处理日志
     * @param joinPoint
     * @param e
     * @param jsonResult
     */
    private void handleLog(final JoinPoint joinPoint, final Exception e, final Object jsonResult) {
        try {
            // 获得自定义注解
            CommonLog annotationLog = getAnnotationLog(joinPoint);
            if (annotationLog == null)
            {
                return;
            }

            // 获取当前用户Id
            Long userId = LocalUserId.get();
            String ip = IpUtil.getIpAddr(ServletUtil.getRequest());
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            // 添加数据
            CommonLogDO logDO = new CommonLogDO();
            logDO.setTitle(annotationLog.title());
            logDO.setStatus(BusinessStatus.SUCCESS.getCode());
            logDO.setBusinessTypeCode(annotationLog.businessType().getCode());
            logDO.setBusinessTypeName(annotationLog.businessType().getName());
            logDO.setMethod(className + "." + methodName + "()");
            // 设置请求方式
            logDO.setRequestMethod(ServletUtil.getRequest().getMethod());
            // 是否需要保存request，参数和值
            if (annotationLog.isSaveRequestData()) {
                // 获取参数的信息，传入到数据库中。
//                setRequestValue(logDO);
                logDO.setRequestParam(params.get());
            }
            // 设置返回结果
            logDO.setJsonResult(JSON.toJSONString(jsonResult));
            logDO.setRequestUrl(ServletUtil.getRequest().getRequestURL().toString());
            logDO.setOperId(userId.intValue());
            logDO.setOperIp(ip);
            if (e != null)
            {
                logDO.setStatus(BusinessStatus.FAIL.getCode());
                logDO.setErrorMsg(StringUtil.substring(e.getMessage(), 0, 2000));
            }
            // 异步保存日志
            AsyncManager.me().schedule(AsyncFactory.recordCommonLog(logDO));
        } catch (Exception exception) {
            log.error("日志记录异常，msg{}", exception.getMessage());
        }
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private CommonLog getAnnotationLog(JoinPoint joinPoint) throws Exception
    {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null)
        {
            return method.getAnnotation(CommonLog.class);
        }
        return null;
    }

    /**
     * 获取请求的参数
     *
     */
    private String getRequestParams()
    {
        HttpServletRequest req = ServletUtil.getRequest();
        String contentType = req.getContentType();
        String params ="";
        // 判断contentType类型
        if (contentType == null) {

            Map<String, String[]> map = req.getParameterMap();
            if (StringUtil.isNotEmpty(map))
            {
                PropertyPreFilters.MySimplePropertyPreFilter excludefilter = new PropertyPreFilters().addFilter();
                excludefilter.addExcludes(EXCLUDE_PROPERTIES);
                params = JSONObject.toJSONString(map, excludefilter);

            }
        } else if (contentType.contains("application/json")) {
            // 从流中读取请求数据
            ServletInputStream is;
            try {
                is = req.getInputStream();
                int nRead = 1;
                int nTotalRead = 0;
                byte[] bytes = new byte[10240];
                while (nRead > 0) {
                    nRead = is.read(bytes, nTotalRead, bytes.length - nTotalRead);
                    if (nRead > 0)
                        nTotalRead = nTotalRead + nRead;
                }
                params = new String(bytes, 0, nTotalRead, StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return params;
    }

    @Override
    public int getOrder() {
        return order;
    }
}
