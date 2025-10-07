package org.autotests.task2;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@ToString
@EqualsAndHashCode
public class EnhancedStudent {

    @Getter
    @Setter
    private String name;
    private List<Integer> grades = new ArrayList<>();

    @Setter
    StudentService service;

    public EnhancedStudent(String name) {
        this.name = name;
    }

    public List<Integer> getGrades() {
        return new ArrayList<>(grades);
    }

    public void addGrade(int grade) {
        if (service.checkGrade(grade)) {
            grades.add(grade);
        } else {
            throw new IllegalArgumentException(grade + " is wrong grade");
        }
    }

    public int rating() {
        return service.getRatingForGradeSum(grades.stream().mapToInt(x->x).sum());
    }
}
