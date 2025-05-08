package paypap.paypap.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import paypap.paypap.Entity.Statutory;

@Repository
public interface StatutoryRepository extends JpaRepository<Statutory, Long> {
	// Custom query methods can be defined here if needed

}
