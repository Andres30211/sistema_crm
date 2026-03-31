package com.lexicodigital.crmneumaticaindustrial.sistema_crm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lexicodigital.crmneumaticaindustrial.sistema_crm.dto.PostFacebookDto;
import com.lexicodigital.crmneumaticaindustrial.sistema_crm.entity.PostFacebookEntity;

@Repository
public interface PostfacebookRepository extends JpaRepository<PostFacebookEntity, Long>{
	
	Optional<PostFacebookEntity> findByFbPostId(String fbPostId);
	
	boolean existsByFbComentarioId(String fbComentarioId);

}
