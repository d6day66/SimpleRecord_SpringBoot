package cn.jackbin.SimpleRecord.exception;

import cn.jackbin.SimpleRecord.dto.CodeMsg;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.exception
 * @date: 2020/8/31 19:41
 **/
public class BusinessException extends BaseException {
    public BusinessException(String message) {
        super(message);
        super.codeMsg = CodeMsg.NOT_FIND_DATA;
    }

    public BusinessException(CodeMsg codeMsg,String message) {
        super(message);
        super.codeMsg = codeMsg;
    }
}