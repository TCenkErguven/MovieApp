package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.UserRegisterRequestDto;
import com.bilgeadam.dto.request.UserUpdateRequestDto;
import com.bilgeadam.dto.response.UserLoginResponseDto;
import com.bilgeadam.repository.entity.User;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Optional;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserMapper {
    IUserMapper INSTANCE = Mappers.getMapper(IUserMapper.class);
    User registerMapper(final UserRegisterRequestDto dto);
    UserLoginResponseDto loginMapper(final User user);

    //@MappedTarget --> sizin dto'nuz ile entity'niz arasında bir mappleme
    //yaparak veri güvenliği sağlar ve veri kaybını önler
    /**
     * Dönüş tipi void olmalıdır.
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserfromDto(UserUpdateRequestDto dto, @MappingTarget User user);

}
