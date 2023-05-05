package com.restapi.jobboard.model.mapper;

import com.restapi.jobboard.model.dto.TagDto;
import com.restapi.jobboard.model.entity.Tag;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public abstract class TagMapper implements EntityMapper<TagDto, Tag> {

}
