package com.daddy.dataobjs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @ClassName DickObject2
 * @Description TODO
 * @Author niuyp
 * @Date 2021/7/2 15:23
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection="dick_info")
public class Dick2DO implements Serializable {
    private static final long serialVersionUID = -4463111487710987748L;
    String dickLength;
    String name;
    Integer age;

}
