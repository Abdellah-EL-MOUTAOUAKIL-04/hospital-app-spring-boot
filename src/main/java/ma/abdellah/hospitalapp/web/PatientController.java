package ma.abdellah.hospitalapp.web;

import ma.abdellah.hospitalapp.entities.Patient;
import ma.abdellah.hospitalapp.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;


@Controller
public class PatientController {
    @Autowired
    private PatientRepository patientRepository;

    @GetMapping("/index")
    public String index(Model model,@RequestParam(name="page",defaultValue = "0") int page,
                        @RequestParam(name="size",defaultValue = "5") int size,
                        @RequestParam(name = "keyword",defaultValue = "") String keyword
                        ) {
        //Model nous permet de passer des données à la vue (template) en utilisant le modèle MVC  de Spring
        Page<Patient> pagePatients= patientRepository.findByPrenomContainsIgnoreCaseOrNomContainsIgnoreCase(keyword,keyword,PageRequest.of(page, size));

        model.addAttribute("listPatients", pagePatients.getContent());
        model.addAttribute("pages", new int[pagePatients.getTotalPages()]);
        //la variables pages contient un tableau d'entiers de taille égale au nombre de pages total
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);

        return "patients";
    }

    @GetMapping("/deletePatient")
    public String delete(@RequestParam( name="id") Long id){
        patientRepository.deleteById(id);
        return "redirect:/index";
    }

}
