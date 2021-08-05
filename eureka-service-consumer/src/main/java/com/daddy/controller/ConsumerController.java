package com.daddy.controller;

import com.daddy.constants.Dict;
import com.daddy.vo.JunkViewObject;
import com.daddy.vo.ViewObject;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.swing.text.View;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ConsumerController
 * @Description TODO
 * @Author niuyp
 * @Date 2021/6/30 14:05
 * @Version 1.0
 **/
@RestController
public class ConsumerController {
    @Autowired
    RestTemplate restTemplate;

    @GetMapping(value = "/consumer/test1")
    public String invokeRemoteMethod() {
        final String url = "http://eureka-client-user-service/provider/test1";

        return restTemplate.getForObject(url, String.class);
    }

    @RequestMapping(value = "/consumer/test2", method = RequestMethod.POST)
    public Map invokeRemoteMethod2() {
        final String url = "http://eureka-client-user-service/provider/test2";
        Map map = new HashMap();
        for (int i = 1; i < 10; i++) {
            String str = String.valueOf(i).intern();
            map.put(str, str);
        }

        HttpEntity<Map> httpEntity = new HttpEntity<>(map, getHttpHeaders());

        Map resp = restTemplate.postForObject(url, httpEntity, Map.class);
        System.out.println(resp.toString());

        return resp;
    }

    @RequestMapping(value = "/consumer/test3", method = RequestMethod.POST)
    public JunkViewObject invokeRemoteMethod3() {
        final String url = "http://eureka-client-user-service/provider/test2";
        ViewObject viewObject = new ViewObject();

        viewObject.setId("1");
        viewObject.setIdValue(1);

        HttpEntity<ViewObject> httpEntity = new HttpEntity<>(viewObject, getHttpHeaders());

        JunkViewObject viewObject1 = restTemplate.postForObject(url, httpEntity, JunkViewObject.class);
        System.out.println(viewObject1.toString());
        return viewObject1;

    }


    private HttpHeaders getHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(Dict.POST_REQ_HEADER_NAME, Dict.POST_REQ_HEADER_VALUE);

        return httpHeaders;

    }


}


