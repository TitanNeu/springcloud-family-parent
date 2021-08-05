package com.daddy.vo;

/**
 * @ClassName ViewObject
 * @Description TODO
 * @Author niuyp
 * @Date 2021/6/30 14:38
 * @Version 1.0
 **/
public class ViewObject {
    private String id;
    private int idValue;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIdValue() {
        return idValue;
    }

    public void setIdValue(int idValue) {
        this.idValue = idValue;
    }

    @Override
    public String toString() {
        return "ViewObject{" +
                "id='" + id + '\'' +
                ", idValue=" + idValue +
                '}';
    }
}
