package paypap.paypap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller

public class PayrollController {
@GetMapping("/")
public String showForm() {
	return"payslip-form";
}
@PostMapping("/calculate")
public String calculatePayslip(@RequestParam("grossSalary") double grossSalary, Model model) {
	// Perform calculations and add attributes to the model
	double tax= calculateTax(grossSalary);
	double netSalary = grossSalary - tax;
	model.addAttribute("grossSalary", grossSalary);
	model.addAttribute("tax", tax);
	model.addAttribute("netSalary", netSalary);
	return "payslip-result";

}
