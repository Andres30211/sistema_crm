package com.lexicodigital.crmneumaticaindustrial.sistema_crm.dto.formsfacebook;

import java.util.List;

import lombok.Data;

@Data
public class LeadDTO {

	private String id;
    private String created_time;
    private List<FieldDataDTO> field_data;
}
