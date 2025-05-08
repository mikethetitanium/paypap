package paypap.paypap.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "statutory")
@Data
public class Statutory {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long calculation_id;
	
	@Column(name = "basic_salary")
	private double basicSalary;
	
	@Column(name = "other_allowance")
	private double otherAllowance;
	
	@Column(name = "other_allowance1")
	private double otherAllowance1;
	
	@Column(name = "pension_contribution")
	private double pensionContribution;
	
	@Column(name = "nssf_contribution")
	private double nssfContribution;
	
	
	@Column(name = "housing_levy")
	private double housingLevy;
	
	@Column(name = "shif")
	private double shif;
	
	@Column(name = "mortgage_interest")
	private double mortgageInterest;
	
	@Column(name = "gross_salary")
	private double grossSalary;
	
	
	@Column(name = "taxable_income")
	private double taxableIncome;
	
	@Column(name = "tax")
	private double tax;
	
	@Column(name = "net_salary")
	private double netSalary;
	
	
	
	
}
