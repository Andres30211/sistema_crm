package com.lexicodigital.crmneumaticaindustrial.sistema_crm.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lexicodigital.crmneumaticaindustrial.sistema_crm.dto.formsfacebook.FormDTO;
import com.lexicodigital.crmneumaticaindustrial.sistema_crm.dto.formsfacebook.LeadDTO;
import com.lexicodigital.crmneumaticaindustrial.sistema_crm.dto.formsfacebook.LeadGenResponseDTO;
import com.lexicodigital.crmneumaticaindustrial.sistema_crm.entity.LeadEntity;
import com.lexicodigital.crmneumaticaindustrial.sistema_crm.mappers.LeadFacebookMapper;
import com.lexicodigital.crmneumaticaindustrial.sistema_crm.repository.LeadRepository;

@Service
public class LeadService {

	@Autowired
	private LeadRepository repository;
	
	@Autowired
	private LeadFacebookMapper mapper;
	
	public void saveLeads(LeadGenResponseDTO response) {
	
	    List<LeadEntity> entities = new ArrayList();
	
	    for (FormDTO form : response.getData()) {
	
	        if (form.getLeads() == null) continue;
	
	        for (LeadDTO lead : form.getLeads().getData()) {
	
	            // 🔥 MapStruct trabajando fino
	            LeadEntity entity = mapper.toEntity(lead, form);
	
	            entities.add(entity);
	        }
	    }
	
	    repository.saveAll(entities);
	}
}
