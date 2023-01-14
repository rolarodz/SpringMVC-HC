package com.mx.accenture.springmvc.example.controller;

import com.mx.accenture.springmvc.example.dto.CourseDTO;
import com.mx.accenture.springmvc.example.service.ICourseService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/list")
    public List<CourseDTO> listCourse(Model model){
        log.info("Execute controller to list in Spring MVC");
        List<CourseDTO> listRepository = courseService.listCourse();
        return listRepository;
    }

    @GetMapping("/delete/{id}")
    public void deleteCourse(@PathVariable String id){
        log.info("Execute controller to delete in Spring MVC");
        courseService.deleteCourse(id);
    }
}
