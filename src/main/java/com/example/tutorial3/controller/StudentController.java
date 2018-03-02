package com.example.tutorial3.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.tutorial3.model.StudentModel;
import com.example.tutorial3.service.*;

@Controller
public class StudentController {
	private final StudentService studentService;
	
	public StudentController() {
		studentService = new InMemoryStudentService();
	}
	
	@RequestMapping("/student/add")
	public String add(@RequestParam(value = "npm", required = true) String npm,
						@RequestParam(value = "name", required = true) String name,
						@RequestParam(value = "gpa", required = true) double gpa) {
		
		StudentModel student = new StudentModel(npm, name, gpa);
		studentService.addStudent(student);
		
		return "add";
	}
	
	@RequestMapping("/student/view")
	public String view(Model model, @RequestParam(value = "npm", required = true) String npm) {
		StudentModel student = studentService.selectStudent(npm);
		model.addAttribute("student", student);
		
		return "view";
	}
	
	@RequestMapping("/student/viewall")
	public String viewAll(Model model) {
		List<StudentModel> students = studentService.selectAllStudents();
		model.addAttribute("students", students);
		
		return "viewall";
	}
	
	@RequestMapping(value = {"/student/view/", "student/view/{npm}"})
	public String viewStudent	(@PathVariable Optional<String> npm, Model model) {
		if(!npm.isPresent()) {
			model.addAttribute("errorMessage", "Please add parameter npm on request URL");
			
		} else {
			StudentModel student = studentService.selectStudent(npm.get());
			if(student != null) {
				model.addAttribute("student", student);
			} else {
				model.addAttribute("errorMessage", "NPM "+npm.get()+" not found.");
			}
			
		}
		
		return "view";
	}
	
	@RequestMapping(value = {"/student/delete", "student/delete/{npm}"})
	public String deleteStudent(@PathVariable Optional<String> npm, Model model) {
		if(!npm.isPresent()) {
			model.addAttribute("message", "Please add parameter npm on request URL");
			
		} else {
			Boolean isDeleted = studentService.deleteStudent(npm.get());
			if(isDeleted) {
				model.addAttribute("message", "Success to delete Student with NPM "+npm.get());
			} else {
				model.addAttribute("message", "Failed to delete student, becase NPM "+npm.get()+" not found!");
			}
			
		}
		
		return "delete";
	}
}
