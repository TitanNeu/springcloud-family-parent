package com.daddy.service.impl;

import com.daddy.dataobjs.DickDO;
import com.daddy.dataobjs.Dick2DO;
import com.daddy.dao.MongoDaoMapper;
import com.daddy.dao.MongoDaoMapper2;
import com.daddy.service.MongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @ClassName MongoServiceIml
 * @Description TODO
 * @Author niuyp
 * @Date 2021/7/2 14:14
 * @Version 1.0
 **/
@Service
public class MongoServiceImpl implements MongoService {

    @Autowired
    MongoDaoMapper mongoDaoMapper;
    @Autowired
    MongoDaoMapper2 mongoDaoMapper2;

    @Autowired
    MongoTemplate mongoTemplate;

    /**
      * @Author niuyp
      * @Description 保存DickObject
      * @Date 19:31 2021/7/6
      * @Param [dickObject]
      * @return com.daddy.dataobjs.DickObject
      **/
    @Override
    public DickDO save1(DickDO dickDO) {
        return mongoDaoMapper.save(dickDO);
    }

    /**
      * @Author niuyp
      * @Description 保存DickObject2到
      * @Date 19:32 2021/7/6
      * @Param [dickObject]
      * @return com.daddy.dataobjs.DickObject2
      **/
    @Override
    public Dick2DO save2(Dick2DO dickObject) {
        return mongoDaoMapper2.save(dickObject);
    }

    /**
      * @Author niuyp
      * @Description mongo template操作
      * @Date 14:04 2021/7/6
      * @Param [id]
      * @return java.util.List<com.daddy.dadtaobjs.DickObject2>
      **/
    @Override
    @Cacheable(value = "dick1s", key = "#id")
    public List<DickDO> query(String id) {

        Query query = new Query(Criteria.where("_id").is(id));
        List<DickDO> dickDOS = mongoTemplate.find(query, DickDO.class,"dick_doc");


        return dickDOS;

    }
    /**
      * @Author niuyp
      * @Description mongo repository操作数据
      * @Date 14:04 2021/7/6
      * @Param [id]
      * @return java.util.Optional<com.daddy.dadtaobjs.DickObject2>
      **/
    @Override
    @Cacheable(value = "dick2s", key = "#id")
    public Dick2DO query2(String id) {
        Optional<Dick2DO> byId = mongoDaoMapper2.findById(id);
        return byId.isPresent() ? byId.get():null;

    }
}
