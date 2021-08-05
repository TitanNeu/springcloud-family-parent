package com.daddy.dao;

import com.daddy.dataobjs.Dick2DO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @InterfaceName MongoDAO
 * @Description TODO
 * @Author niuyp
 * @Date 2021/7/2 14:10
 * @Version 1.0
 **/
@Repository
public interface MongoDaoMapper2 extends MongoRepository<Dick2DO, String> {
}
