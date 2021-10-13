package soft.secure_disk.configService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AntMatcherRedirection {

    @GetMapping("/")
    public String redirectToDisk() {
        return "redirect:/disk";
    }
}
