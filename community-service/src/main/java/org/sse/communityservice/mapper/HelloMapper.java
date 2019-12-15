package org.sse.communityservice.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface HelloMapper {

    @Select(value = "SELECT username FROM mlab.user ;")
    public List<String> sayHello();
}
