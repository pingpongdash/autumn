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

    // ✅ Spring が Environment を自動で注入できるコンストラクタ
    public ComponentScanConfig(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        try {
            //  String[] packages = environment.getProperty("spring.component.scan.base-packages", String[].class);
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




// package jp.kernelpanic.autumn.appbase.config;

// import org.springframework.beans.factory.support.BeanDefinitionRegistry;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
// import org.springframework.core.env.Environment;
// import org.springframework.core.type.AnnotationMetadata;
// import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

// import java.util.Arrays;
// import java.util.List;
// import java.util.stream.Collectors;

// @Configuration
// public class ComponentScanConfig implements ImportBeanDefinitionRegistrar {

//     @Override
//     public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
//         try {
//             // ApplicationContext はまだ初期化されていないので Environment から読む
//             Environment env = (Environment) registry.getBeanFactory().getBean(Environment.class);

//             String[] packages = env.getProperty("spring.component.scan.base-packages", String[].class);

//             if (packages == null || packages.length == 0) {
//                 System.out.println("⚠️ No component scan packages found in YAML.");
//                 return;
//             }

//             List<String> packageList = Arrays.stream(packages)
//                     .map(String::trim)
//                     .collect(Collectors.toList());

//             System.out.println("🧭 Dynamically scanning packages: " + String.join(", ", packageList));

//             ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry);
//             for (String basePackage : packageList) {
//                 scanner.scan(basePackage);
//             }

//         } catch (Exception e) {
//             System.err.println("❌ ComponentScanConfig failed: " + e.getMessage());
//         }
//     }
// }



// // package jp.kernelpanic.autumn.appbase.config;

// // import org.springframework.beans.factory.annotation.Autowired;
// // import org.springframework.beans.factory.support.BeanDefinitionRegistry;
// // import org.springframework.context.ApplicationContext;
// // import org.springframework.context.annotation.Configuration;
// // import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
// // import org.springframework.core.type.AnnotationMetadata;
// // import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

// // import java.util.List;

// // @Configuration
// // public class ComponentScanConfig implements ImportBeanDefinitionRegistrar {

// //     @Autowired
// //     private ApplicationContext context;

// //     @Override
// //     public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
// //         try {
// //             ComponentScanProperties properties = context.getBean(ComponentScanProperties.class);
// //             List<String> packages = properties.getScan().getBasePackages();

// //             if (packages == null || packages.isEmpty()) {
// //                 System.out.println("⚠️ No component scan packages found in YAML.");
// //                 return;
// //             }

// //             System.out.println("🧭 Dynamically scanning packages: " + String.join(", ", packages));

// //             ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry);
// //             for (String basePackage : packages) {
// //                 scanner.scan(basePackage);
// //             }
// //         } catch (Exception e) {
// //             System.err.println("❌ ComponentScanConfig failed: " + e.getMessage());
// //         }
// //     }
// // }




// // // package jp.kernelpanic.autumn.appbase.config;

// // // import org.springframework.beans.factory.support.BeanDefinitionRegistry;
// // // import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
// // // import org.springframework.context.annotation.Configuration;
// // // import org.springframework.core.type.AnnotationMetadata;
// // // import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

// // // import java.util.List;

// // // @Configuration
// // // public class ComponentScanConfig implements ImportBeanDefinitionRegistrar {

// // //     private final ComponentScanProperties properties;

// // //     public ComponentScanConfig(ComponentScanProperties properties) {
// // //         this.properties = properties;
// // //     }

// // //     @Override
// // //     public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
// // //         List<String> packages = properties.getScan().getBasePackages();

// // //         if (packages == null || packages.isEmpty()) {
// // //             System.out.println("⚠️ No component scan packages found in YAML.");
// // //             return;
// // //         }

// // //         System.out.println("🧭 Dynamically scanning packages: " + String.join(", ", packages));

// // //         ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry);
// // //         for (String basePackage : packages) {
// // //             scanner.scan(basePackage);
// // //         }
// // //     }
// // // }



// // // // package jp.kernelpanic.autumn.appbase.config;

// // // // import org.springframework.context.annotation.Configuration;
// // // // import org.springframework.context.annotation.ComponentScan;

// // // // import java.util.List;

// // // // @Configuration
// // // // @ComponentScan
// // // // public class ComponentScanConfig {

// // // //     public ComponentScanConfig(ComponentScanProperties properties) {
// // // //         List<String> packages = properties.getScan().getBasePackages();
// // // //         if (packages == null || packages.isEmpty()) {
// // // //             System.out.println("⚠️ No component scan packages found in YAML.");
// // // //         } else {
// // // //             System.out.println("🧭 Dynamically scanning packages: " + String.join(", ", packages));
// // // //         }
// // // //     }
// // // // }
