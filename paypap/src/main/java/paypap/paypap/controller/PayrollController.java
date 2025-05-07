package paypap.paypap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PayrollController {

    @GetMapping("/")
    public String showForm(Model model) {
        // Initialize the model attributes with default values
        model.addAttribute("taxableIncome", 0.0);
        model.addAttribute("netSalary", 0.0);
        model.addAttribute("tax", 0.0);
        model.addAttribute("grossSalary", 0.0);
        model.addAttribute("basicSalary", 0.0);
        model.addAttribute("otherAllowance1", 0.0);
        model.addAttribute("otherAllowance", 0.0);
        model.addAttribute("pensionContribution", 0.0);
        model.addAttribute("nssfContribution", 0.0);
        model.addAttribute("housingLevy", 0.0);
        model.addAttribute("shif", 0.0);
        model.addAttribute("mortgageInterest", 0.0); // Example value
        return "payslip-form"; // Ensure the correct template is returned
    }

    @PostMapping("/calculate")
    public String calculatePayslip(@RequestParam("basicSalary") double basicSalary,
                                    @RequestParam("otherAllowance") double otherAllowance,
                                    @RequestParam("otherAllowance1") double otherAllowance1,
                                    @RequestParam("pensionContribution") double pensionContribution,
                                    @RequestParam("nssfContribution") double nssfContribution,
                                    @RequestParam("housingLevy") double housingLevy,
                                    @RequestParam("shif") double shif,
                                    @RequestParam("mortgageInterest") double mortgageInterest,
                                    Model model) {
        // Perform calculations and add attributes to the model
    	 double contributionLimit=30000;
    	 double mortgageLimit=30000;
    	 double consideredContribution=0;
    	 double consideredMortgage=0;
    	 if((pensionContribution + nssfContribution)>contributionLimit) {
    		 consideredContribution=contributionLimit;
    	 }
    	 else {
    		 consideredContribution=pensionContribution + nssfContribution;
    	 }
    	 if(mortgageInterest>mortgageLimit) {
    		 consideredMortgage=mortgageLimit;
    	 }
    	 else {
    		 consideredMortgage=mortgageInterest;
    	 }
        double grossSalary = basicSalary + otherAllowance + otherAllowance1;
        double taxableIncome = grossSalary - consideredContribution-consideredMortgage;
        double tax = calculateTax(taxableIncome);
        double netSalary = grossSalary - tax;
        double shifcal = calculateShif(basicSalary, otherAllowance); // Calculate SHIF based on basic salary and other allowance

        // Add calculated values to the model for display in the result page
        model.addAttribute("grossSalary", grossSalary);
        model.addAttribute("consideredContribution", consideredContribution);
        model.addAttribute("consideredMortgage", consideredMortgage);
        model.addAttribute("taxableIncome", taxableIncome);
        model.addAttribute("tax", tax);
        model.addAttribute("netSalary", netSalary);
        model.addAttribute("shifcal", shifcal); // Add SHIF calculation result to the model

        // Add form field values to the model to retain them after form submission
        model.addAttribute("basicSalary", basicSalary);
        model.addAttribute("otherAllowance1", otherAllowance1);
        model.addAttribute("otherAllowance", otherAllowance);
        model.addAttribute("pensionContribution", pensionContribution);
        model.addAttribute("nssfContribution", nssfContribution);
        model.addAttribute("housingLevy", housingLevy);
        model.addAttribute("shif", shif);
        model.addAttribute("shifcal", shifcal);

        return "payslip-result"; // Redirect to result page
    }
    
    @PostMapping("/calculate-shif")
    @ResponseBody
    public String calculateShifAjax(@RequestParam("basicSalary") double basicSalary,
                                    @RequestParam("otherAllowance") double otherAllowance) {
        double shifcal = calculateShif(basicSalary, otherAllowance);
        return String.valueOf(shifcal);
    }


    private double calculateTax(double taxableIncome) {
        // Simple tax calculation logic (for demonstration purposes)
        double tax = 0;
        if (taxableIncome <= 24000) {
            tax = taxableIncome * 0.1; // 10% tax
        } else if (taxableIncome <= 32333) {
            tax = ((taxableIncome - 24000) * 0.25) + (0.1 * 24000); // 20% tax
        } else if (taxableIncome <= 500000) {
            tax = ((32333 - 24000) * 0.25) + (0.1 * 24000) + ((taxableIncome - 32333) * 0.3); // 30% tax
        } else if (taxableIncome <= 800000) {
            tax = ((32333 - 24000) * 0.25) + (0.1 * 24000) + ((500000 - 32333) * 0.3) + ((taxableIncome - 500000) * 0.325); // 32.5% tax
        } else {
            tax = ((32333 - 24000) * 0.25) + (0.1 * 24000) + ((500000 - 32333) * 0.3) + ((800000 - 500000) * 0.325) + ((taxableIncome - 800000) * 0.35); // 35% tax
        }
        return tax;
    }
    
    private double calculateShif(double basicSalary, double otherAllowance) {
		// Simple SHIF calculation logic (for demonstration purposes)
		double shifcal = 0;
		// Add your SHIF calculation logic here
		double shifRate = 2.75; // Example: 2.75% of gross salary
		shifcal = (basicSalary + otherAllowance) * (shifRate/100); // Example: 5% of gross salary
		return shifcal;
	}
}
