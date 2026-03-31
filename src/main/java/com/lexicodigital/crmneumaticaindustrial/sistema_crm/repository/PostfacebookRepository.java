package com.lexicodigital.crmneumaticaindustrial.sistema_crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lexicodigital.crmneumaticaindustrial.sistema_crm.dto.PostFacebookDto;

@Repository
public interface PostfacebookRepository extends JpaRepository<PostFacebookDto, Long>{

}
