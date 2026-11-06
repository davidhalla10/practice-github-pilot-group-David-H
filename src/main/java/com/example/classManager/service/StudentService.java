package com.example.classManager.service;

import com.example.classManager.model.Student;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class StudentService {
    private final Map<Long, Student> students = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(0);

    public List<Student> getAllStudents() {
        return new ArrayList<>(students.values());
    }

    public Optional<Student> getStudentById(Long id) {
        return Optional.ofNullable(students.get(id));
    }

    public Student createStudent(Student student) {
        Long id = sequence.incrementAndGet();
        student.setId(id);
        students.put(id, student);
        return student;
    }

    public Student updateStudent(Long id, Student updatedStudent) {
        Student existingStudent = students.get(id);
        if (existingStudent == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found");
        }

        existingStudent.setFirstName(updatedStudent.getFirstName());
        existingStudent.setLastName(updatedStudent.getLastName());
        existingStudent.setEmail(updatedStudent.getEmail());
        existingStudent.setGradeLevel(updatedStudent.getGradeLevel());

        return existingStudent;
    }

    public void deleteStudent(Long id) {
        if (!students.containsKey(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found");
        }
        students.remove(id);
    }
}
