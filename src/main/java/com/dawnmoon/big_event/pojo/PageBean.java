package com.dawnmoon.big_event.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageBean <T> {
    private Long count;
    private List<T> lists;
}
