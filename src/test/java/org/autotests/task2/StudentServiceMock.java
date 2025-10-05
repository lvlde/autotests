package org.autotests.task2;

public class StudentServiceMock implements StudentService {

    @Override
    public boolean checkGrade(int grade) {
        return grade >= 2 && grade <= 5;
    }

    @Override
    public int getRatingForGradeSum(int sum) {
        return 0;
    }


}
