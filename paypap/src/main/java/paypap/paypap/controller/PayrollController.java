package paypap.paypap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import paypap.paypap.Entity.Statutory;
import paypap.paypap.Repository.StatutoryRepository;
import paypap.paypap.Service.PayrollService;

@Controller
public class PayrollController {

    @Autowired
    private PayrollService payrollService;
    @Autowired
    private StatutoryRepository statutoryRepository;

    @GetMapping("/")
    public String showForm(Model model) {
        model.addAttribute("statutory", new Statutory());
        return "payslip-form";
    }

    @PostMapping("/calculate")
    public String calculatePayslip(Statutory statutory, Model model) {
        // Perform payroll calculations
        Statutory result = payrollService.calculatePayroll(statutory);

        // Add fields to the model for Thymeleaf
        model.addAttribute("grossSalary", result.getGrossSalary());
        model.addAttribute("consideredContribution", result.getConsideredPensionContribution());
        model.addAttribute("consideredMortgage", result.getConsideredMortgageInterest());
        model.addAttribute("shif", result.getShif());
        model.addAttribute("housingLevy", result.getHousingLevy());
        model.addAttribute("taxableIncome", result.getTaxableIncome());
        model.addAttribute("tax", result.getTax());
        model.addAttribute("netSalary", result.getNetSalary());
        
        statutoryRepository.save(statutory);
        model.addAttribute("message", "Payslip saved successfully.");

        return "payslip-result";
    }

  
}
