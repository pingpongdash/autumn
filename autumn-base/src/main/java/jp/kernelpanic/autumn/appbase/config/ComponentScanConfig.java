package jp.kernelpanic.autumn.appbase.config;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class ComponentScanConfig implements ImportBeanDefinitionRegistrar {

    private final Environment environment;

    public ComponentScanConfig(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        try {
            String[] packages = environment.getProperty("spring.component.scan.base.packages", String[].class);


            if (packages == null || packages.length == 0) {
                System.out.println("⚠️ No component scan packages found in YAML.");
                return;
            }

            List<String> packageList = Arrays.stream(packages)
                    .map(String::trim)
                    .collect(Collectors.toList());

            System.out.println("🧭 Dynamically scanning packages: " + String.join(", ", packageList));

            ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry);
            for (String basePackage : packageList) {
                scanner.scan(basePackage);
            }

        } catch (Exception e) {
            System.err.println("❌ ComponentScanConfig failed: " + e.getMessage());
        }
    }
}

