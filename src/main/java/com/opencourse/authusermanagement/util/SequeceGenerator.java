package com.opencourse.authusermanagement.util;

import static  org.springframework.data.mongodb.core.query.Query.query;

import java.util.Objects;

import static  org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.opencourse.authusermanagement.entities.Sequence;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class SequeceGenerator {

    private final MongoTemplate mongoTemplate;

    public Long generate(String sequenceName){
        Sequence counter=mongoTemplate.findAndModify(query(where("_id").is(sequenceName)),
        new Update().inc("seq", 1),
        options().returnNew(true).upsert(true), 
        Sequence.class);
        return !Objects.isNull(counter) ? counter.getSeq() : 1L;
    } 
    
}
