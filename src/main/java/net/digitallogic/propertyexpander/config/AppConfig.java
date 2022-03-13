package net.digitallogic.propertyexpander.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

@Configuration
@ComponentScan(value = "net.digitallogic.propertyexpander")
@Slf4j
public class AppConfig {

	@Autowired
	public AppConfig() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}
}
