package cn.jackbin.SimpleRecord.dto;

import lombok.Data;

import java.util.List;

@Data
public class UrlObjectDto {
    private Integer state;
    private Integer id;
    private List<String> urls;
    private String name;
}