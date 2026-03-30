package com.lexicodigital.crmneumaticaindustrial.sistema_crm.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.lexicodigital.crmneumaticaindustrial.sistema_crm.dto.PostFacebookDto;

@Repository
public interface PostfacebookRepository extends R2dbcRepository<PostFacebookDto, String>{

}
