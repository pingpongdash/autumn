package jp.kernelpanic.autumn.appbase.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "spring.component")
public class ComponentScanProperties {

    private Scan scan = new Scan();

    public static class Scan {
        private List<String> basePackages;

        public List<String> getBasePackages() {
            return basePackages;
        }

        public void setBasePackages(List<String> basePackages) {
            this.basePackages = basePackages;
        }
    }

    public Scan getScan() {
        return scan;
    }

    public void setScan(Scan scan) {
        this.scan = scan;
    }
}
