package com.daddy.dataobjs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @ClassName DickObject
 * @Description TODO
 * @Author niuyp
 * @Date 2021/7/2 14:04
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Document(collection="dick_doc")
public class DickDO implements Serializable {
    private static final long serialVersionUID = -3586082919840623072L;
    private String id;
    private String name;
    private String dickName;
}
