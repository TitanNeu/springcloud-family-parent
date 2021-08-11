package com.daddy.controller;

import com.daddy.vo.ViewObject;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @ClassName ServiceProviderController
 * @Description TODO
 * @Author niuyp
 * @Date 2021/6/30 14:02
 * @Version 1.0
 **/
@RestController
public class ServiceProviderController {

    @GetMapping(value = "/provider/test1")
    public String test1() {
        return "invoke remote method success";
    }

    @RequestMapping(value = "/provider/test2", method = RequestMethod.POST)
    public Map<String, String> test2(@RequestBody Map<String, String> inMap) {
        return inMap;
    }


    @RequestMapping(value = "/provider/test3", method = RequestMethod.POST)
    public ViewObject test2(@RequestBody ViewObject viewObject) {
        return viewObject;
    }

    @GetMapping(value = "/proxy/test1")
    public String testProxy() {
        return "invoke remote method by gateway server success";
    }
}
