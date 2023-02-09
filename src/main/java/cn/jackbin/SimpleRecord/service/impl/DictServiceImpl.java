package cn.jackbin.SimpleRecord.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.jackbin.SimpleRecord.bo.PageBO;
import cn.jackbin.SimpleRecord.constant.CommonConstants;
import cn.jackbin.SimpleRecord.entity.DictDO;
import cn.jackbin.SimpleRecord.mapper.DictMapper;
import cn.jackbin.SimpleRecord.service.DictService;
import cn.jackbin.SimpleRecord.utils.HttpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.service.impl
 * @date: 2021/7/20 20:07
 **/
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, DictDO> implements DictService {
    @Autowired
    private DictMapper dictMapper;

    @Override
    public void getByPage(String name, String code, Integer status, PageBO<DictDO> pageBO) {
        IPage<DictDO> page = new Page<>(pageBO.getPageNo(), pageBO.getPageSize());
        QueryWrapper<DictDO> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNoneBlank(name)) {
            queryWrapper.like("name", name);
        }
        if (StringUtils.isNoneBlank(code)) {
            queryWrapper.like("code", code);
        }
        if (status != null) {
            queryWrapper.eq("status", status);
        }
        queryWrapper.orderByAsc("order_no");
        page = dictMapper.selectPageWithoutLogicDel(page, queryWrapper);
        pageBO.setTotal((int) page.getTotal());
        pageBO.setList(page.getRecords());
    }

    @Override
    public void add(String name, String code, Integer orderNo, String remark) {
        DictDO dictDO = new DictDO();
        dictDO.setName(name);
        dictDO.setCode(code);
        dictDO.setRemark(remark);
        dictDO.setStatus(CommonConstants.STATUS_NORMAL);
        dictDO.setOrderNo(orderNo);
        dictDO.setIsSysDefault(CommonConstants.NOT_SYS_DEFAULT);
        dictMapper.insert(dictDO);
    }

    @Override
    public void edit(Integer id, String name, String code, Integer orderNo, String remark) {
        DictDO dictDO = new DictDO();
        dictDO.setId(Long.valueOf(id));
        dictDO.setName(name);
        dictDO.setCode(code);
        dictDO.setRemark(remark);
        dictDO.setOrderNo(orderNo);
        dictMapper.updateById(dictDO);
    }

    @Override
    public DictDO getByCode(String code) {
        QueryWrapper<DictDO> queryWrapper = new QueryWrapper<>();
        DictDO dictDO = new DictDO();
        dictDO.setCode(code);
        queryWrapper.eq("code", code);
        return dictMapper.selectOneWithoutLogicDel(queryWrapper);
    }

    @Transactional
    @Override
    public boolean removeById(Long id) {
        return dictMapper.delByIdFillStatus(id) > 0;
    }

    @Override
    public void reset(Integer id) {
        DictDO dictDO = new DictDO();
        dictDO.setId(Long.valueOf(id));
        dictDO.setStatus(CommonConstants.STATUS_NORMAL);
        dictDO.setDeleteTime(null);
        dictMapper.updateByIdWithoutLogicDel(dictDO);
    }

    public String demo(String question) {
        if (StrUtil.isBlank(question)) {
            question = "先进数通是外包吗";
        }
        if (!question.contains("外包吗")) {
            question = question + "是外包吗";
        }
        String wd = URLEncoder.encode(question, StandardCharsets.UTF_8);
        String url = "http://www.baidu.com/s?wd=" + wd + "&rsv_spt=1&rsv_iqid=0xc0f6975f0001ed9e&issp=1&f=8&rsv_bp=1&rsv_idx=2&ie=utf-8&tn=baiduhome_pg&rsv_dl=ib&rsv_enter=1&rsv_sug3=2&rsv_n=2";
//        String url = "http://www.baidu.com/s?wd=" + wd;
//        String url = "https://www.baidu.com/s?ie=utf-8&mod=1&isbd=1&isid=0774837B41527295&ie=utf-8&f=8&rsv_bp=1&tn=baidu&wd=" + wd + "&oq=%25E5%258C%2597%25E4%25BA%25AC%25E5%25AE%2587%25E4%25BF%25A1%25E6%2598%25AF%25E5%25A4%2596%25E5%258C%2585%25E5%2590%2597&rsv_pq=976904700004d513&rsv_t=ab65eZcfvZhjY1hiOYtF4xKG4xUfhAiaznYLrWbttVr0LTs%2Faq5PzpiIHgI&rqlang=cn&rsv_enter=0&rsv_dl=tb&rsv_btype=t&bs=%E5%8C%97%E4%BA%AC%E5%AE%87%E4%BF%A1%E6%98%AF%E5%A4%96%E5%8C%85%E5%90%97&rsv_sid=undefined&_ss=1&clist=&hsug=&f4s=1&csor=0&_cr1=37236";
        System.out.println(url);
        HashMap<String, String> map = new HashMap<>();
        map.put("cookie", "BIDUPSID=552EB905B7458162003C8F6884931E61; PSTM=1636416819; __yjs_duid=1_c367f358e978352b2c1f33fc7f838c931636420978573; BDUSS=jRmUHVpMDNLUnJQZGtYNzJOYXQ5TX5yWmdDdm9uNC0wNHBuaXRnREJRQnFNUHRoSVFBQUFBJCQAAAAAAAAAAAEAAABFI-0GZDZkYXk2NgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAGqj02Fqo9NhS; BDUSS_BFESS=jRmUHVpMDNLUnJQZGtYNzJOYXQ5TX5yWmdDdm9uNC0wNHBuaXRnREJRQnFNUHRoSVFBQUFBJCQAAAAAAAAAAAEAAABFI-0GZDZkYXk2NgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAGqj02Fqo9NhS; MCITY=-194%3A; BD_UPN=12314753; BAIDUID=077483933C4D4E86188826275127B415:FG=1; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; ab_sr=1.0.1_NzE4ODI5N2UxZjQ2OWY3NGEzMzE2N2I0MDAzOWM2YmFiYTI0MzQ0NTRmYmFiMDk1NWQ2NmFiYjdiZjgxZTJhNDcwY2MzNTY4YThmZjMyZDYwYjQyYWU5YzljOGIwYjFkYTljNTE3YWI3ZjJmMTc2NDc1MzRlNTJjODY0NjIwZTBjMjMwYWZjZDk4YmM4YjJkMWVhZDdkYmY4MjQ0ZWZiZGIzZDBlNTJiZGQ3OTBhNWFlYzMwMWE1MDAwMTYxOTM4; delPer=0; BD_CK_SAM=1; PSINO=7; SL_G_WPT_TO=zh; H_PS_PSSID=36552_37975_37646_37689_37908_37625_36920_37795_36805_37934_37900_26350_37791_37881; H_PS_645EC=7667s91TgQ0351tfSMVBjHMELvifDD0zoXLVVT60QhJi7FDEkNce3ZIzFQc; BAIDUID_BFESS=077483933C4D4E86188826275127B415:FG=1; BA_HECTOR=a58g00a0000ha0a52k0107941hqa7br1j; ZFY=J4xPnjips:AeqstO5hiUah2T7rXliIcmEk:AEgQ:BnMLAA:C; COOKIE_SESSION=350_0_7_6_8_15_1_1_6_4_0_4_1027_0_94_0_1671763143_0_1671763049%7C9%2321464_18_1671176215%7C9; ZD_ENTRY=empty");
        String result = HttpUtil.doGet(url, null, map);

        String substring = result.substring(result.indexOf("shortAnswer") + 14, result.indexOf("shortAnswer") + 18);
        // 字符串正则
        String regEx = "[^a-zA-Z_\u4e00-\u9fa5]";
        String s = substring.replaceAll(regEx, "");
        if (!s.contains("是")) {
            s = "未查询到有效信息";
        }
        return s;
    }

}
