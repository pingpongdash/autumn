package jp.kernelpanic.autumn.autumnviews;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ThemeController {

    private final ResourceLoader resourceLoader;

    public ThemeController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @GetMapping(value = "/css/style.css", produces = "text/css")
    @ResponseBody
    public ResponseEntity<Resource> getDynamicCss(
        @RequestParam(defaultValue = "dark") String theme) {

        String cssFileName = "themes/" + theme + ".css";

        Resource resource = resourceLoader.getResource("classpath:" + cssFileName);

        if (!resource.exists()) {
            resource = resourceLoader.getResource("classpath:themes/light.css");
        }
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType("text/css"))
            .header(HttpHeaders.CACHE_CONTROL, "max-age=600")
            .body(resource);
    }
}
