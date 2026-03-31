package com.lexicodigital.crmneumaticaindustrial.sistema_crm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lexicodigital.crmneumaticaindustrial.sistema_crm.dto.CommentfacebookDto;
import com.lexicodigital.crmneumaticaindustrial.sistema_crm.dto.PostFacebookDto;

@Repository
public interface PostfacebookRepository extends JpaRepository<PostFacebookDto, Long>{
	
	Optional<PostFacebookDto> findByfbPostId(String fbPostId);

}
