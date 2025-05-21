package ems.controller;

import ems.dto.LoginForm;
import ems.models.Employee;
import ems.repository.EmployeeRepository;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    // Show login form
    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "pages-login";
    }

    // Handle login submission
    @PostMapping("/login")
    public String processLogin(@ModelAttribute LoginForm loginForm, Model model, HttpSession session) {
        if ("admin".equals(loginForm.getUsername()) && "admin123".equals(loginForm.getPassword())) {
            session.setAttribute("loggedInUser", loginForm.getUsername());
            return "redirect:/index";
        } else {
            model.addAttribute("loginError", "Invalid username or password");
            return "pages-login";
        }
    }

    // Logout and invalidate session
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    // Home page (requires login)
    @GetMapping("/index")
    public String index(HttpSession session, Model model) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }

        long count = employeeRepository.count(); // count employees
        model.addAttribute("employeesCount", count);

        return "index";
    }


    // List all employees (requires login)
    @GetMapping("/tables-general")
    public String listEmployees(Model model, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }
        model.addAttribute("employees", employeeRepository.findAll());
        return "tables-general";
    }

    // Show create employee form (requires login)
    @GetMapping("/forms-elements")
    public String showCreateForm(Employee employee, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }
        return "forms-elements";
    }

    // Save new employee (requires login)
    @PostMapping("/employees")
    public String createEmployee(@ModelAttribute Employee employee, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }
        employeeRepository.save(employee);
        return "redirect:/tables-general";
    }

    // Edit form (requires login)
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee Id:" + id));
        model.addAttribute("employee", employee);
        return "update";
    }

    // Submit edit (requires login)
    @PostMapping("/edit/{id}")
    public String updateEmployee(@ModelAttribute Employee employee, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }
        employeeRepository.save(employee);
        return "redirect:/tables-general";
    }

    // Delete employee (requires login)
    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable("id") Long id, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee Id:" + id));
        employeeRepository.delete(employee);
        return "redirect:/tables-general";
    }
}
