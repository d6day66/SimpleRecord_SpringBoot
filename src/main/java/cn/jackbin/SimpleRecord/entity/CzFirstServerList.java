package cn.jackbin.SimpleRecord.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * @author: d6day
 * @date: 2023/4/02/002 12:46
 * @description:
 */
@Data
public class CzFirstServerList {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private Integer code;
    private Integer state;
    private Integer currentServer;
    private Integer version;
    private Integer delLog;
    private Date createTime;
    private Date updateTime;
}
