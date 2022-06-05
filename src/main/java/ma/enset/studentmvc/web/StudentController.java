package ma.enset.studentmvc.web;

import lombok.AllArgsConstructor;
import ma.enset.studentmvc.entities.Student;
import ma.enset.studentmvc.repositories.StudentRepository;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
public class StudentController {
    private StudentRepository studentRepository;
    @GetMapping(path = "/index")
    public String students(Model model,
                           @RequestParam (name = "page",defaultValue="0")int page,
                           @RequestParam (name = "size",defaultValue="10")int size,
                           @RequestParam (name = "keyword",defaultValue="")String keyword
                           ){
        Page<Student> pageStudents=studentRepository.findByNomContains(keyword,PageRequest.of(page,size));
        model.addAttribute("ListStudents",pageStudents.getContent());
        model.addAttribute("page", new int[pageStudents.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",keyword);
        return "students";
    }

    @GetMapping("/delete")
    public String delete(Long id, String keyword, int page){
        studentRepository.deleteById(id);
        return "redirect:/index?page="+page+"&keyword="+keyword;
    }
    @GetMapping("/")
    public String home() {
        return "redirect:/index";
    }
    @GetMapping("/students")
    @ResponseBody
    public List<Student> listStudents(){
        return studentRepository.findAll();
    }

    @GetMapping("/formStudents")
    public String formStudents(Model model){
        model.addAttribute("student",new Student());
        return "formStudents";
    }

    @PostMapping(path = "/save")
    public String save(Model model, @Valid Student students, BindingResult bindingResult, int page, String keyword){
        if(bindingResult.hasErrors()) return "formStudents";
        studentRepository.save(students);
        return "redirect:/index?page="+page+"&keyword="+keyword;

    }

    @GetMapping("/editStudent")
    public String editStudent(Model model, Long id, String keyword, int page){
        Student student=studentRepository.findById(id).orElse(null);
        if(student==null) throw  new RuntimeException("Student doesn't exist!");
        model.addAttribute("student", student);
        model.addAttribute("page", page);
        model.addAttribute("keyword", keyword);
        return "editStudent";
    }
}
