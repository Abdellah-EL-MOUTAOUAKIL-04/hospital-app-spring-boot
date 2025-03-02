package ma.abdellah.hospitalapp;

import ma.abdellah.hospitalapp.entities.Patient;
import ma.abdellah.hospitalapp.repository.PatientRepository;
import ma.abdellah.hospitalapp.security.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

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

    //cette fonction permettre de créer des utilisateurs et les roles associés en utilisant l'interface JdbcUserDetailsManager
    //@Bean
    CommandLineRunner commandLineRunner(JdbcUserDetailsManager jdbcUserDetailsManager){
        PasswordEncoder passwordEncoder=passwordEncoder();
        return args -> {
            if(!jdbcUserDetailsManager.userExists("admin"))
                jdbcUserDetailsManager.createUser(User.withUsername("admin").password(passwordEncoder.encode("1234")).roles("ADMIN","USER").build());
            if(!jdbcUserDetailsManager.userExists("user1"))
                jdbcUserDetailsManager.createUser(User.withUsername("user1").password(passwordEncoder.encode("1234")).roles("USER").build());
            if(!jdbcUserDetailsManager.userExists("user2"))
                jdbcUserDetailsManager.createUser(User.withUsername("user2").password(passwordEncoder.encode("1234")).roles("USER").build());
        };
    }

   // @Bean
    public CommandLineRunner commandLineRunnerUserdetails(AccountService accountService){
        return args -> {
            accountService.addNewRole("USER");
            accountService.addNewRole("ADMIN");
            accountService.addNewUser("user1","1234","user1@gmail.com", "1234");
            accountService.addNewUser("user2","1234","user2@gmail.com", "1234");
            accountService.addNewUser("admin","1234","admin@gmail.com", "1234");

            accountService.addRoleToUser("user1","USER");
            accountService.addRoleToUser("user2","USER");
            accountService.addRoleToUser("admin","USER");
            accountService.addRoleToUser("admin","ADMIN");
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
