package com.daddy.service;

import com.daddy.dataobjs.DickDO;
import com.daddy.dataobjs.Dick2DO;

import java.util.List;

/**
 * @InterfaceName MongoService
 * @Description TODO
 * @Author niuyp
 * @Date 2021/7/2 14:13
 * @Version 1.0
 **/
public interface MongoService {


    DickDO save1(DickDO dickDO);
    Dick2DO save2(Dick2DO dickObject);


    List<DickDO> query(String id);
    Dick2DO query2(String id);


}
