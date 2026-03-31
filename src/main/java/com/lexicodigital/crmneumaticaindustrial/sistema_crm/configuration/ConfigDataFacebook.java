package com.lexicodigital.crmneumaticaindustrial.sistema_crm.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
	@PropertySource("classpath:datafacebook.properties")
})
public class ConfigDataFacebook {

}
