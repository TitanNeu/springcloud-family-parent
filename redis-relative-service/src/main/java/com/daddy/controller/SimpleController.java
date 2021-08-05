package com.daddy.controller;

import com.daddy.dataobjs.DickDO;
import com.daddy.dataobjs.Dick2DO;
import com.daddy.service.MongoService;
import com.daddy.vo.ViewObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @ClassName SimpleController
 * @Description TODO
 * @Author niuyp
 * @Date 2021/7/2 14:18
 * @Version 1.0
 **/
@RestController
public class SimpleController {
    @Autowired
    MongoService mongoService;


    @RequestMapping(value = "/mongo/savaDickObject1", method = RequestMethod.POST)
    public DickDO test1(@RequestBody Map<String, String> map) {

        DickDO dickDO = new DickDO();
        dickDO.setId(map.get("id"));
        dickDO.setName(map.get("name"));
        dickDO.setDickName(map.get("dickName"));


        return mongoService.save1(dickDO);


    }

    @RequestMapping(value = "/mongo/savaDickObject2", method = RequestMethod.POST)
    public Dick2DO test2(@RequestBody Map<String, Object> map) {

        Dick2DO dickObject = new Dick2DO();
        dickObject.setDickLength((String) map.get("dickLength"));
        dickObject.setName((String) map.get("name"));
        dickObject.setAge((Integer) map.get("age"));


        return mongoService.save2(dickObject);

    }

    @RequestMapping(value = "/mongo/getDickObjectList", method = RequestMethod.POST)
    public List<DickDO> test3(@RequestBody Map<String, String> map) {
        List<DickDO> objs = mongoService.query(map.get("id"));
        return objs;

    }

    //返回视图层对象
    @RequestMapping(value = "/mongo/getViewDickObject", method = RequestMethod.POST)
    public ViewObject test4(@RequestBody Map<String, String> map) {
        List<DickDO> objs = mongoService.query(map.get("id"));
        if(objs == null || objs.size() == 0) {
            return null;
        }
        DickDO dickDO = objs.get(0);
        ViewObject viewObject = new ViewObject();
        viewObject.setName(dickDO.getName());
        viewObject.setDickName(dickDO.getDickName());
        return viewObject;
    }

    @RequestMapping(value = "/mongo/getDickObject2", method = RequestMethod.POST)
    public Dick2DO test5(@RequestBody Map<String, String> map) {
        return mongoService.query2(map.get("id"));

    }

}
