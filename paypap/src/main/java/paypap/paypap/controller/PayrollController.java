package paypap.paypap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import paypap.paypap.Entity.StaffInfo;
import paypap.paypap.Entity.Statutory;
import paypap.paypap.Repository.StaffInfoRepository;
import paypap.paypap.Repository.StatutoryRepository;
import paypap.paypap.Service.PayrollService;

@Controller
public class PayrollController {

    @Autowired
    private PayrollService payrollService;
    @Autowired
    private StatutoryRepository statutoryRepository;
    @Autowired
    private StaffInfoRepository staffInfoRepository;

    @GetMapping("/")
    public String showForm(Model model) {
        model.addAttribute("statutory", new Statutory());
        return "payslip-form";
    }

    @PostMapping("/calculate")
    public String calculatePayslip(Statutory statutory, StaffInfo staffInfo, Model model) {
        // Perform payroll calculations
        Statutory result = payrollService.calculatePayroll(statutory, staffInfo);

        // Add fields to the model for Thymeleaf
        model.addAttribute("grossSalary", result.getGrossSalary());
        model.addAttribute("consideredContribution", result.getConsideredPensionContribution());
        model.addAttribute("consideredMortgage", result.getConsideredMortgageInterest());
        model.addAttribute("shif", result.getShif());
        model.addAttribute("housingLevy", result.getHousingLevy());
        model.addAttribute("taxableIncome", result.getTaxableIncome());
        model.addAttribute("tax", result.getTax());
        model.addAttribute("netSalary", result.getNetSalary());
        
        
        
        return "payslip-result";
    }

    @PostMapping("/savePayrollDetails")
    public String savePayrollDetails(@ModelAttribute Statutory statutory, @ModelAttribute StaffInfo staffInfo, Model model) {
        // Save staff info first
        //StaffInfo savedStaff = staffInfoRepository.save(staffInfo);

        // Associate saved staff with statutory object
        //statutory.setStaffInfo(savedStaff);

        // ⚠️ Perform calculations BEFORE saving statutory
        Statutory calculatedStatutory = payrollService.calculatePayroll(statutory, staffInfo);

        // Save calculated statutory data
        //statutoryRepository.save(calculatedStatutory);
        //staffInfoRepository.save(staffInfo);
       // statutoryRepository.save(calculatedStatutory);
        
        // statutoryRepository.save(statutory);

        model.addAttribute("message", "Payroll details saved successfully.");

        // Add all calculated fields to the model for display
        model.addAttribute("grossSalary", calculatedStatutory.getGrossSalary());
        model.addAttribute("consideredContribution", calculatedStatutory.getConsideredPensionContribution());
        model.addAttribute("consideredMortgage", calculatedStatutory.getConsideredMortgageInterest());
        model.addAttribute("shif", calculatedStatutory.getShif());
        model.addAttribute("housingLevy", calculatedStatutory.getHousingLevy());
        model.addAttribute("taxableIncome", calculatedStatutory.getTaxableIncome());
        model.addAttribute("tax", calculatedStatutory.getTax());
        model.addAttribute("netSalary", calculatedStatutory.getNetSalary());

        return "payslip-result";
    }

    
}
