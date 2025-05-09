package paypap.paypap.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import paypap.paypap.Entity.StaffInfo;
import paypap.paypap.Entity.Statutory;
import paypap.paypap.Repository.StaffInfoRepository;
import paypap.paypap.Repository.StatutoryRepository;

@Service
public class PayrollService {

    @Autowired
    private StatutoryRepository statutoryRepository;
    @Autowired
    private StaffInfoRepository staffInfoRepository;

    public Statutory calculatePayroll(Statutory statutory, StaffInfo staffInfo) {
        // Step 1: Gross Salary Calculation
        double grossSalary = statutory.getBasicSalary()
                                     + statutory.getOtherAllowance()
                                     + statutory.getOtherAllowance1();

        // Step 2: Cap deductions
        double contributionLimit = 30000;
        double mortgageLimit = 30000;

        double totalContribution = statutory.getPensionContribution() + statutory.getNssfContribution();
        double consideredPensionContribution = Math.min(totalContribution, contributionLimit);
        double consideredMortgageInterest = Math.min(statutory.getMortgageInterest(), mortgageLimit);

        // Step 3: SHIF and Housing Levy
        double shif = calculateShif(statutory.getBasicSalary(), statutory.getOtherAllowance1());
        double housingLevy = calculateHousingLevy(statutory.getBasicSalary(), statutory.getOtherAllowance1());

        // Step 4: Taxable Income
        double taxableIncome = grossSalary - consideredPensionContribution - consideredMortgageInterest - shif - housingLevy;

        // Step 5: Tax and Net Salary
        double tax = calculateTax(taxableIncome);
        double netSalary = grossSalary - tax - shif - housingLevy - statutory.getPensionContribution() - statutory.getNssfContribution() - statutory.getMortgageInterest();

        // Step 6: Populate Entity
        statutory.setGrossSalary(grossSalary);
        statutory.setConsideredPensionContribution(consideredPensionContribution);
        statutory.setConsideredMortgageInterest(consideredMortgageInterest);
        statutory.setShif(shif);
        statutory.setHousingLevy(housingLevy);
        statutory.setTaxableIncome(taxableIncome);
        statutory.setTax(tax);
        statutory.setNetSalary(netSalary);

        // If StaffInfo is required, save it first and link it to Statutory
        if (staffInfo != null) {
            staffInfoRepository.save(staffInfo); // Save StaffInfo to the DB first
            statutory.setStaffInfo(staffInfo); // Link StaffInfo to Statutory
        }

        // Step 7: Save the Statutory object to the database
        return statutoryRepository.save(statutory);
    }

    public double calculateTax(double taxableIncome) {
        double tax = 0;
        if (taxableIncome <= 24000) {
            tax = taxableIncome * 0.1;
        } else if (taxableIncome <= 32333) {
            tax = ((taxableIncome - 24000) * 0.25) + (0.1 * 24000);
        } else if (taxableIncome <= 500000) {
            tax = ((32333 - 24000) * 0.25)
                + (0.1 * 24000)
                + ((taxableIncome - 32333) * 0.3);
        } else if (taxableIncome <= 800000) {
            tax = ((32333 - 24000) * 0.25)
                + (0.1 * 24000)
                + ((500000 - 32333) * 0.3)
                + ((taxableIncome - 500000) * 0.325);
        } else {
            tax = ((32333 - 24000) * 0.25)
                + (0.1 * 24000)
                + ((500000 - 32333) * 0.3)
                + ((800000 - 500000) * 0.325)
                + ((taxableIncome - 800000) * 0.35);
        }
        return tax;
    }

    public double calculateShif(double basicSalary, double otherAllowance1) {
        double shifRate = 2.75;
        return (basicSalary + otherAllowance1) * (shifRate / 100);
    }

    public double calculateHousingLevy(double basicSalary, double otherAllowance1) {
        double housingLevyRate = 0.015;
        return (basicSalary + otherAllowance1) * housingLevyRate;
    }
}
