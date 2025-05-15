package com.gmail.subscriptions.mapper;

import com.gmail.subscriptions.dto.UserCreationDto;
import com.gmail.subscriptions.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User creationToEntity(UserCreationDto userCreationDto);
}
