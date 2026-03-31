package com.lexicodigital.crmneumaticaindustrial.sistema_crm.mappers;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.lexicodigital.crmneumaticaindustrial.sistema_crm.dto.CommentfacebookDto;
import com.lexicodigital.crmneumaticaindustrial.sistema_crm.dto.PostFacebookDto;
import com.lexicodigital.crmneumaticaindustrial.sistema_crm.entity.CommentFacebookEntity;
import com.lexicodigital.crmneumaticaindustrial.sistema_crm.entity.PostFacebookEntity;

@Mapper(componentModel = "spring")
public interface FacebookMapper {

	// 🔥 POST
    @Mapping(target = "idEntity", ignore = true)
    @Mapping(target = "fechaCaptura", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "comentarios", source = "comments.data")
	PostFacebookEntity toEntity(PostFacebookDto postFacebookDto);
    
    List<PostFacebookEntity> toEntityList(List<PostFacebookDto> dtoList);

    // 🔥 COMMENTS
    @Mapping(target = "idEntity", ignore = true)
    @Mapping(target = "userName", source = "from.name")
    @Mapping(target = "userId", source = "from.id")
    @Mapping(target = "post", ignore = true) // se asigna manualmente
    CommentFacebookEntity toEntity(CommentfacebookDto dto);

    List<CommentFacebookEntity> toCommentList(List<CommentfacebookDto> dtoList);

    // 🔥 AFTER MAPPING (clave para relaciones)
    @AfterMapping
    default void linkComments(@MappingTarget PostFacebookEntity post) {
        if (post.getComentarios() != null) {
            post.getComentarios().forEach(c -> c.setPost(post));
        }
    }
}
