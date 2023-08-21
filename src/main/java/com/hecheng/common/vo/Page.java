package com.hecheng.common.vo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class Page {
    Integer page;
    Integer pageSize;
}
