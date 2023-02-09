package cn.jackbin.SimpleRecord.demo.dto;

import lombok.Data;

/**
 * @author: d6day
 * @date: 2023/2/09/009 9:53
 * @description:
 */
@Data
public class DemoObject {
    private String name;
    private Integer age;

    public DemoObject(String gh, int i) {
        this.name = gh;
        this.age = i;
    }
}
