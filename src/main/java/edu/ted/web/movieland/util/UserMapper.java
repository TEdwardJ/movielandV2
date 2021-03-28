package edu.ted.web.movieland.util;

import edu.ted.web.movieland.entity.User;
import edu.ted.web.movieland.web.dto.UserDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, implementationName = "DefaultUserMapper")
public interface UserMapper {
    UserDto mapToDTO(User user);
}
