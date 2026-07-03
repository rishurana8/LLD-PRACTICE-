package org.rishudesign.com.InterviewPractice;

import java.time.LocalDate;
import java.time.Period;

public class Employee {
    private String name;
    private String gender;
    private LocalDate dateofBirth;

    public Employee(String name,String gender,LocalDate dateofbirth){
        this.name = name;
        this.gender = gender;
        this.dateofBirth = dateofbirth;
    }

    public String getGender(){
        return this.gender;
    }

    public LocalDate getDateOfBirth(){
        return this.dateofBirth;
    }

    public int getAge(){
        return Period.between(dateofBirth,LocalDate.now()).getYears();
    }
}
