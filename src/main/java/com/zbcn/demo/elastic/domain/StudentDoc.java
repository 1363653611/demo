package com.zbcn.demo.elastic.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.zbcn.demo.elastic.query.annotation.QueryMapping;
import com.zbcn.demo.elastic.query.annotation.QueryPolicy;
import com.zbcn.demo.serializer.JodaDateTimeDeserializer;
import lombok.Data;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * Created by nealmi on 8/31/16.
 */
@Document(indexName = "zbcn", type = "test")
@QueryMapping(mappedQueryString = "page", queryPolicy = QueryPolicy.IGNORE)
@QueryMapping(mappedQueryString = "size", queryPolicy = QueryPolicy.IGNORE)
@QueryMapping(mappedQueryString = "q", queryPolicy = QueryPolicy.FULLTEXT)
@Data
public class StudentDoc {

    @Id
    @QueryMapping(queryPolicy = QueryPolicy.IN)
    @QueryMapping(mappedQueryString = "_id", queryPolicy = QueryPolicy.IN)
    private String id;

    private String name;

    private Study study;

    //@Field(type = FieldType.Date, index = FieldIndex.not_analyzed, format = DateFormat.date_time)
    @QueryMapping(queryPolicy = QueryPolicy.RANGE)
    @JsonDeserialize(using = JodaDateTimeDeserializer.class)
    private DateTime createdAt;


}
