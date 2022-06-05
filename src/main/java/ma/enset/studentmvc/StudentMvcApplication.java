package ma.enset.studentmvc;

import ma.enset.studentmvc.entities.Student;
import ma.enset.studentmvc.repositories.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class StudentMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentMvcApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository){
        return  args -> {
            studentRepository.save(
                    new Student(null,"Barazzouk","Bader","baderbarazzouk@gmail.com",new Date(),"MASCULIN",true));
            studentRepository.save(
                    new Student(null,"Bricha","Anass","bricha@gmail.com",new Date(),"MASCULIN",true));
            studentRepository.save(
                    new Student(null,"Hachmi","Mohamed Amin","hachmi@gmail.com",new Date(),"MASCULIN",false));
            studentRepository.save(
                    new Student(null,"Elhabbazi","Soufyan","soufyan@gmail.com",new Date(),"MASCULIN",false));
            studentRepository.save(
                    new Student(null,"souad","Barazzouk","souad@gmail.com",new Date(),"FEMININ",true));

            studentRepository.findAll().forEach(s->{
                System.out.println(s.getNom());
            });
        };
    }

}
