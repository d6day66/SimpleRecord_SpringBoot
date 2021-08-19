package cn.jackbin.SimpleRecord.controller.basic;

import cn.jackbin.SimpleRecord.service.DictItemService;
import cn.jackbin.SimpleRecord.vo.AddDictItemVO;
import cn.jackbin.SimpleRecord.vo.EditDictItemVO;
import cn.jackbin.SimpleRecord.vo.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.controller.basic
 * @date: 2021/8/12 20:37
 **/
@Api(value = "DictItemController", tags = { "字典项相关接口" })
@RestController
@RequestMapping("/dictItem")
public class DictItemController {
    @Autowired
    private DictItemService dictItemService;

    @PostMapping("/add")
    public Result<?> addDictItem(@RequestBody @Validated AddDictItemVO vo) {
        dictItemService.add(vo.getDictId(), vo.getText(), vo.getValue(), vo.getOrderNo(), vo.getRemark());
        return Result.success();
    }

    @PutMapping("/edit")
    public Result<?> editDictItem(@RequestBody @Validated EditDictItemVO vo) {
        dictItemService.edit(vo.getId(), vo.getText(), vo.getValue(), vo.getOrderNo(), vo.getRemark());
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delDictItem(@PathVariable @Validated @Positive(message = "Id需为正数") Integer id) {
        dictItemService.removeById(id);
        return Result.success();
    }
}
