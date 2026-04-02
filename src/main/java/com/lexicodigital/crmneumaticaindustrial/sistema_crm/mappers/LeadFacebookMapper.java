package com.lexicodigital.crmneumaticaindustrial.sistema_crm.mappers;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.lexicodigital.crmneumaticaindustrial.sistema_crm.dto.formsfacebook.FormDTO;
import com.lexicodigital.crmneumaticaindustrial.sistema_crm.dto.formsfacebook.LeadDTO;
import com.lexicodigital.crmneumaticaindustrial.sistema_crm.entity.LeadEntity;

@Mapper(componentModel = "spring")
public interface LeadFacebookMapper {

	// 🔥 FORM + LEADS → ENTITY
    @Mapping(target = "id", source = "lead.id")
    @Mapping(target = "createdTime", source = "lead.created_time")
    @Mapping(target = "formId", source = "form.id")
    @Mapping(target = "formName", source = "form.name")

    // Campos personalizados (los sacamos del field_data)
    @Mapping(target = "fullName", expression = "java(extractField(lead, \"full_name\"))")
    @Mapping(target = "email", expression = "java(extractField(lead, \"email\"))")
    @Mapping(target = "phoneNumber", expression = "java(extractField(lead, \"phone_number\"))")
    @Mapping(target = "phoneVerified", expression = "java(parseBoolean(extractField(lead, \"phone_number_verified\")))")

    LeadEntity toEntity(LeadDTO lead, FormDTO form);


    // 🔥 LISTA
    List<LeadEntity> toEntityList(List<LeadDTO> leads, @Context FormDTO form);


    // 🔥 MÉTODO AUXILIAR PARA EXTRAER CAMPOS DINÁMICOS
    default String extractField(LeadDTO lead, String fieldName) {
        if (lead.getField_data() == null) return null;

        return lead.getField_data().stream()
                .filter(f -> fieldName.equals(f.getName()))
                .map(f -> f.getValues() != null && !f.getValues().isEmpty() ? f.getValues().get(0) : null)
                .findFirst()
                .orElse(null);
    }

    default Boolean parseBoolean(String value) {
        return value != null && Boolean.parseBoolean(value);
    }
    
    @AfterMapping
    default void normalize(@MappingTarget LeadEntity lead) {

        if (lead.getEmail() != null) {
            lead.setEmail(lead.getEmail().toLowerCase());
        }

        if (lead.getFullName() != null) {
            lead.setFullName(lead.getFullName().trim());
        }
    }
}
