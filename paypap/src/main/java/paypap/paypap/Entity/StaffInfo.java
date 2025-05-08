package paypap.paypap.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "staff_info")
@Data
public class StaffInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "companyName")
	private String companyName;
	
	@Column(name="staffNumber")
	private String staffNumber;
	
	@Column(name="staffName")
	private String staffName;
	
	@Column(name="year")
	private String year;
	
	@Column(name="month")
	private String month;
	
}
