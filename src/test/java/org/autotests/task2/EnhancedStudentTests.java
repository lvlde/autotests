package org.autotests.task2;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class EnhancedStudentTests {

    private final Faker faker = new Faker();
    private EnhancedStudent student;

    @BeforeEach
    public void prepare() {
        student = new EnhancedStudent(faker.name().fullName());
    }

    @DisplayName("Добавление валидных оценок")
    @ParameterizedTest(name = "{index} - Добавление оценки {0}")
    @ValueSource(ints = {2, 3, 4, 5})
    public void shouldAllowToAddGradesWithinRange(int grade) {
        student.setService(new StudentServiceMock());
        student.addGrade(grade);
        Assertions.assertEquals(student.getGrades().get(0), grade);
    }

    @DisplayName("Добавление невалидных оценок")
    @ParameterizedTest(name = "{index} - Неуспешное добавление оценки {0}")
    @ValueSource(ints = {1, 0, 6})
    public void shouldNotAllowToAddGradesOutOfRange(int invalidGrade) {
        student.setService(new StudentServiceMock());
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                student.addGrade(invalidGrade));
    }
}
