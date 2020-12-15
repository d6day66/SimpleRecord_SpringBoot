package cn.jackbin.SimpleRecord.controller.auth;

import cn.hutool.http.HttpUtil;
import cn.jackbin.SimpleRecord.constant.UrlConstant;
import cn.jackbin.SimpleRecord.service.WechatUserService;
import cn.jackbin.SimpleRecord.vo.LoginSuccessVO;
import cn.jackbin.SimpleRecord.vo.Result;
import cn.jackbin.SimpleRecord.vo.WechatUserVO;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.controller.auth
 * @date: 2020/11/2 20:45
 **/
@Api(value = "WechatAuthController", tags = { "微信授权接口" })
@RestController
@RequestMapping("/wx")
public class WechatAuthController {
    @Value("${wechat.appId}")
    private String appId;
    @Value("${wechat.appSecret}")
    private String appSecret;

    @Autowired
    private WechatUserService wechatUserService;

    @ApiOperation(value = "获取微信openId")
    @GetMapping("/openId/{code}")
    public Result<?> getOpenId(@Validated @NotNull(message = "code不能为空") @PathVariable("code") String code) {
        Map<String, Object> map = new HashMap<>();
        map.put("appid", appId);
        map.put("secret", appSecret);
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");
        String result = HttpUtil.get(UrlConstant.wechatOpenIdUrl, map);
        return Result.success(JSON.parse(result));
    }

    @ApiOperation(value = "微信登录授权")
    @PostMapping("/login")
    public Result<?> wechatLogin(@RequestBody @Validated  WechatUserVO vo) {
        LoginSuccessVO successVO = wechatUserService.wechatLogin(vo);
        return Result.success(successVO);
    }
}
