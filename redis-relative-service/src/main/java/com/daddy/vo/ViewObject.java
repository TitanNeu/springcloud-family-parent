package com.daddy.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName ViewObject
 * @Description TODO
 * @Author niuyp
 * @Date 2021/7/2 14:59
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ViewObject {
    private String name;
    private String dickName;
}
