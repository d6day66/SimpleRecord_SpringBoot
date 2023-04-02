package cn.jackbin.SimpleRecord.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.jackbin.SimpleRecord.dto.UrlObjectDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: d6day
 * @date: 2023/4/02/002 10:50
 * @description:
 */
public class HttpDemo {
    public static void main(String[] args) {
        String url = "http://jljx-3sm-hz.sthaozhe.com/ProjectPok/group/server_list.json?v=20232902102917 ";
        Map<String, String> headers = new HashMap<>();
        headers.put("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1");
        HttpRequest get = HttpUtil.createGet(url);
        get.addHeaders(headers);
        HttpResponse execute = get.execute();
        String body = execute.body();
        JSONObject object = JSONUtil.parseObj(body);
        List<UrlObjectDto> games = (List<UrlObjectDto>) object.get("games");
        List<UrlObjectDto> urlObjects = BeanUtil.copyToList(games, UrlObjectDto.class);
        UrlObjectDto urlObject = urlObjects.stream().filter(item -> item.getState().equals(1)).findFirst().get();
        System.out.println(urlObject);
    }
}


