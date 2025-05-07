package paypap.paypap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class PayrollController {
@GetMapping("/")
public String showForm() {
	return "payslip-form";
}
@PostMapping("/calculate")
public String calculatePayslip(@RequestParam("basicSalary") double basicSalary, Model model) {
	// Perform calculations and add attributes to the model
	double tax= calculateTax(basicSalary);
	double netSalary = basicSalary - tax;
	model.addAttribute("basicSalary", basicSalary);
	model.addAttribute("tax", tax);
	model.addAttribute("netSalary", netSalary);
	return "payslip-result";

}
private double calculateTax(double basicSalary) {
	// Simple tax calculation logic (for demonstration purposes)
	double tax= 0;
	if (basicSalary <= 24000) {
		 tax = basicSalary * 0.1;// 10% tax
	} else if (basicSalary <= 32333) {
		tax = ((basicSalary - 24000) * 0.25) +(0.1 * 24000); // 20% tax
	} else if (basicSalary <= 500000) {
		   tax = ((32333 - 24000) * 0.25) + (0.1 * 24000) + ((basicSalary - 32333) * 0.3); // 30% tax		
	}else if (basicSalary <= 800000) {
		tax = ((32333 - 24000) * 0.25) + (0.1 * 24000) + ((500000 - 32333) * 0.3) + ((basicSalary - 500000) * 0.325); // 32.5% tax
	}
	else {
		tax = ((32333 - 24000) * 0.25) + (0.1 * 24000) + ((500000 - 32333) * 0.3) + ((800000 - 500000) * 0.325) + ((basicSalary - 800000) * 0.35); // 35% tax
	}
	return tax;
}
}
