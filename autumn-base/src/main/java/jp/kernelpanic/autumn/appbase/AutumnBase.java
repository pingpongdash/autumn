package jp.kernelpanic.autumn.appbase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import jp.kernelpanic.autumn.appbase.config.ComponentScanConfig;
import jp.kernelpanic.autumn.appbase.config.ComponentScanProperties;
import jp.kernelpanic.auth.core.config.SecurityConfig;

@SpringBootApplication
@Import(ComponentScanConfig.class)
public class AutumnBase {
    public static void main(String[] args) {
        SpringApplication.run(AutumnBase.class, args);
    }
}
