package ma.abdellah.hospitalapp;

import ma.abdellah.hospitalapp.entities.Patient;
import ma.abdellah.hospitalapp.repository.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class HospitalAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(HospitalAppApplication.class, args);
    }

    //Lorsque en utilise l'annotation @Bean, Spring va exécuter la méthode annotée au démarrage de l'application
    //@Bean
    public CommandLineRunner start(PatientRepository patientRepository) {
        return args -> {
            Patient p1 =new Patient();
            p1.setNom("EL MOUTAOUAKIL");
            p1.setPrenom("Abdellah");
            p1.setScore(10);
            p1.setMalade(false);
            p1.setDateNaissance(new Date());

            Patient p2=new Patient(null,"EL MOUTAOUAKIL","ahmed",new Date(),20,false);

            //en utilisant le design pattern Builder en peut creer un objet de type Patient sont avoir besoin de l'initialiser avec toutes les attributs
            Patient p3=Patient.builder()
                    .nom("EL MOUTAOUAKIL")
                    .prenom("Zahra")
                    .score(30)
                    .dateNaissance(new Date())
                    .build();

            patientRepository.save(p1);
            patientRepository.save(p2);
            patientRepository.save(p3);

            patientRepository.findAll().forEach(System.out::println);
        };
    }
}
