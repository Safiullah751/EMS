package employee.employee;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmployeeController {

    @GetMapping("/tables-general")
    public String tablesGeneral() {
        return "tables-general";
    }

    @GetMapping("/forms-elements")
    public String formsElements() {
        return "forms-elements";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/pages-login")
    public String pagesLogin() {
        return "pages-login";
    }

    @GetMapping("/users-profile")
    public String usersProfile() {
        return "users-profile";
    }

}
