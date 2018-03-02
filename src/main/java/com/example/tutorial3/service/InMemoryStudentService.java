package com.example.tutorial3.service;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import com.example.tutorial3.model.StudentModel;

public class InMemoryStudentService implements StudentService{
	
	private static List<StudentModel> studentList = new ArrayList<StudentModel>();
	
	@Override
	public StudentModel selectStudent(String npm) {
		for(StudentModel student: studentList) {
			if(student.getNpm().equals(npm)) {				
				return student;
			}
		}
		return null;
	}
	
	@Override
	public List<StudentModel> selectAllStudents(){
		return studentList;
	}
	
	public void addStudent(StudentModel student) {
		studentList.add(student);
	}
	
	public boolean deleteStudent(String npm) {
		boolean isFound = false;
		if(this.selectStudent(npm) != null) {
			ListIterator<StudentModel> iter = studentList.listIterator();
			while(iter.hasNext()){
			    if(iter.next().getNpm().equals(npm)){
			        iter.remove();
			        isFound = true;
			        break;
			    }
			}
			
		}
		
		return isFound;
	}
}
