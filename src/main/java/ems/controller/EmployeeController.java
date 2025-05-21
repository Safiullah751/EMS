





package ems.controller;
import ems.models.Employee;
import ems.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class EmployeeController {
    @GetMapping("index")
    public String index(){
        return "index";
    }

    @Autowired
    private EmployeeRepository employeeRepository;

    // List all employees
    @GetMapping("tables-general")
    public String listEmployees(Model model) {
        model.addAttribute("employees", employeeRepository.findAll());
        return "tables-general";
    }

    // Show form to create a new employee
    @GetMapping("/forms-elements")
    public String showCreateForm(Employee employee) {
        return "forms-elements";
    }

    // Save new employee
    @PostMapping("/employees")
    public String createEmployee(@ModelAttribute Employee employee) {
        employeeRepository.save(employee);
        return "redirect:/tables-general";
    }

    // Show form to edit an existing employee
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee Id:" + id));
        model.addAttribute("employee", employee);
        return "update"; // Make sure update.html exists
    }

    @PostMapping("/edit/{id}")
    public String updateEmployee(@ModelAttribute Employee employee) {
        employeeRepository.save(employee);
        return "redirect:/tables-general";
    }


    // Delete an employee
    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable("id") Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid employee Id:" + id));
        employeeRepository.delete(employee);
        return "redirect:/tables-general";
    }
}

//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//
//@Controller
//public class EmployeeController {
//
//    @GetMapping("/tables-general")
//    public String tablesGeneral() {
//
//        return "tables-general";
//    }
//
//    @GetMapping("/forms-elements")
//    public String formsElements() {
//        return "forms-elements";
//    }
//
//    @GetMapping("/index")
//    public String index() {
//        return "index";
//    }
//
//    @GetMapping("/pages-login")
//    public String pagesLogin() {
//        return "pages-login";
//    }
//
//    @GetMapping("/users-profile")
//    public String usersProfile() {
//        return "users-profile";
//    }
//
//}
