package com.daddy.vo;

import java.util.List;
import java.util.Map;

/**
 * @ClassName JunkViewObject
 * @Description TODO
 * @Author niuyp
 * @Date 2021/6/30 15:39
 * @Version 1.0
 **/
public class JunkViewObject {
    private List<String> li;
    private Map<String, String> map;
    private String id;

    @Override
    public String toString() {
        return "JunkViewObject{" +
                "li=" + li +
                ", map=" + map +
                ", id='" + id + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getLi() {
        return li;
    }

    public void setLi(List<String> li) {
        this.li = li;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

}
