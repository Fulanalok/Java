package br.com.alunoonline.api.service;

import br.com.alunoonline.api.dtos.CreateStudentRequest;
import br.com.alunoonline.api.enums.FinancesStatusEnum;
import br.com.alunoonline.api.model.Course;
import br.com.alunoonline.api.model.FinancesStudent;
import br.com.alunoonline.api.model.Student;
import br.com.alunoonline.api.repository.CourseRepository;
import br.com.alunoonline.api.repository.FinancesStudentRepository;
import br.com.alunoonline.api.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    FinancesStudentRepository financesStudentRepository;

    @Autowired
    CourseRepository courseRepository;

    public void create(CreateStudentRequest createStudentRequest) {
        Course course = courseRepository.findById(createStudentRequest.getCourse_id())
        .orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Course não encontrada"));

        Student studentSaved = studentRepository.save(
                new Student(
                        null,
                        createStudentRequest.getName(),
                        createStudentRequest.getEmail(),
                        course
                )
        );
    }

    public List<Student> findAll() {

        return studentRepository.findAll();
    }

    public Optional<Student> findById(Long id) {

        return studentRepository.findById(id);
    }

    public void update(Long id, Student student) {
        Optional<Student> studentFromBd = findById(id);

        if (studentFromBd.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado no banco de dados");
        }

        Student studentUpdated = studentFromBd.get();

        studentUpdated.setName(student.getName());
        studentUpdated.setEmail(student.getEmail());

        studentRepository.save(studentUpdated);
    }

    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }

    public void createFinancesInformation(Student student, CreateStudentRequest createStudentRequest) {
        FinancesStudent financesStudent = new FinancesStudent(
                null,
                student,
                createStudentRequest.getDiscount(),
                createStudentRequest.getDueDate(),
                    FinancesStatusEnum.EM_DIA
        );}
}
