package com.example.classManager.service;

import com.example.classManager.model.Teacher;
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
public class TeacherService {
    private final Map<Long, Teacher> teachers = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(0);

    public List<Teacher> getAllTeachers() {
        return new ArrayList<>(teachers.values());
    }

    public Optional<Teacher> getTeacherById(Long id) {
        return Optional.ofNullable(teachers.get(id));
    }

    public Teacher createTeacher(Teacher teacher) {
        Long id = sequence.incrementAndGet();
        teacher.setId(id);
        teachers.put(id, teacher);
        return teacher;
    }

    public Teacher updateTeacher(Long id, Teacher updatedTeacher) {
        Teacher existingTeacher = teachers.get(id);
        if (existingTeacher == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Teacher not found");
        }

        existingTeacher.setFirstName(updatedTeacher.getFirstName());
        existingTeacher.setLastName(updatedTeacher.getLastName());
        existingTeacher.setEmail(updatedTeacher.getEmail());
        existingTeacher.setSubject(updatedTeacher.getSubject());

        return existingTeacher;
    }

    public void deleteTeacher(Long id) {
        if (!teachers.containsKey(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Teacher not found");
        }
        teachers.remove(id);
    }
}
