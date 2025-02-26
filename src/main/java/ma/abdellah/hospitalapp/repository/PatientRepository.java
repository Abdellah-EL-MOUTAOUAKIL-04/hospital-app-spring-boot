package ma.abdellah.hospitalapp.repository;

import ma.abdellah.hospitalapp.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
