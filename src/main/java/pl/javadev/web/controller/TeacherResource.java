package pl.javadev.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.javadev.teacher.TeacherService;

@RestController
@RequestMapping("/api/teachers")
public class TeacherResource {
    private TeacherService teacherService;

    public TeacherResource(TeacherService teacherService) {
        this.teacherService = teacherService;
    }


}
