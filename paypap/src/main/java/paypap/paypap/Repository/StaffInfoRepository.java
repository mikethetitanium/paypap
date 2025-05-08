package paypap.paypap.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import paypap.paypap.Entity.StaffInfo;

public interface StaffInfoRepository extends JpaRepository<StaffInfo, Long> {
	// Custom query methods can be defined here if needed

}
