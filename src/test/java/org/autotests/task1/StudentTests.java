package org.autotests.task1;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

@DisplayName("Юнит-тесты на класс Student")
public class StudentTests {

    private final Faker faker = new Faker();
    private Student student;

    @BeforeEach
    public void prepare() {
        student = new Student(faker.name().fullName());
    }

    @DisplayName("Инкапсуляция - невозможно добавить оценку в полученный список")
    @Test
    public void shouldNotAllowAddingGradesWhenEncapsulationIsViolated() {
        List<Integer> grades = student.getGrades();
        Assertions.assertThrows(UnsupportedOperationException.class, () ->
                grades.add(4));
    }

    @DisplayName("Инкапсуляция - невозможно удалить оценку из полученного списка")
    @Test
    public void shouldNotAllowRemovingGradesWhenEncapsulationIsViolated() {
        student.addGrade(3);
        List<Integer> grades = student.getGrades();
        Assertions.assertThrows(UnsupportedOperationException.class, () ->
                grades.remove(Integer.valueOf(3)));
    }

    @DisplayName("Изменение имени")
    @Test
    public void shouldAllowToChangeName() {
        student.setName("Maria");
        Assertions.assertEquals("Maria", student.getName(), "Имя не соответствует ожидаемому");
    }

    @DisplayName("Добавление валидных оценок")
    @ParameterizedTest(name = "{index} - Добавление оценки {0}")
    @ValueSource(ints = {2, 3, 4, 5})
    public void shouldAllowToAddGradesWithinRange(int grade) {
        student.addGrade(grade);
        Assertions.assertEquals(student.getGrades().get(0), grade);
    }

    @DisplayName("Добавление невалидных оценок")
    @ParameterizedTest(name = "{index} - Неуспешное добавление оценки {0}")
    @ValueSource(ints = {1, 0, 6})
    public void shouldNotAllowToAddGradesOutOfRange(int invalidGrade) {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                student.addGrade(invalidGrade));
    }

    @DisplayName("equals() - два разных эквивалентных объекта")
    @Test
    public void twoEquivalentObjectsShouldBeEqual() {
        Student secondStudent = new Student(student.getName());
        Assertions.assertTrue(student.equals(secondStudent), "Студенты не равны");
    }

    @DisplayName("equals() - две ссылки на один объект")
    @Test
    public void twoReferencesForOneObjectShouldBeEqual() {
        Student secondStudent = student;
        Assertions.assertTrue(student.equals(secondStudent), "Студенты не равны");
    }

    @DisplayName("equals() - объект сравнивается со ссылкой на null")
    @Test
    public void nullAndObjectShouldNotBeEqual() {
        Student secondStudent = null;
        Assertions.assertFalse(student.equals(secondStudent), "Студенты равны");
    }

    @DisplayName("equals() - два объекта разного типа")
    @Test
    public void twoObjectsOfDifferentTypesShouldNotBeEqual() {
        String secondStudent = "Maria";
        Assertions.assertFalse(student.equals(secondStudent), "Студенты равны");
    }

    @DisplayName("equals() - два студента с разными именами")
    @Test
    public void twoStudentsWithDifferentNamesShouldNotBeEqual() {
        Student secondStudent = new Student("Apollinaria");
        Assertions.assertFalse(student.equals(secondStudent), "Студенты равны");
    }

    @DisplayName("equals() - два студента с разными оценками")
    @Test
    public void twoStudentsWithDifferentGradesShouldNotBeEqual() {
        Student secondStudent = new Student(student.getName());
        student.addGrade(2);
        student.addGrade(3);
        secondStudent.addGrade(2);
        Assertions.assertFalse(student.equals(secondStudent), "Студенты равны");
    }

    @DisplayName("toString()")
    @ParameterizedTest(name = "{index} - toString(), оценки: {0}")
    @MethodSource("org.autotests.task1.GradeSource#listsOfGrades")
    public void toStringTest(List<Integer> grades) {
        String newName = faker.name().fullName();
        student.setName(newName);
        for (Integer grade : grades) {
            student.addGrade(grade);
        }
        String studentString = student.toString();
        Assertions.assertEquals("Student{" + "name=" + newName + ", grades=" + grades + '}', studentString,
                "Строки не равны");
    }

    @DisplayName("hashCode() - одинаковый для двух равных объектов")
    @Test
    public void twoEqualObjectsShouldHaveEqualHashCodes() {
        Student secondStudent = new Student(student.getName());
        Assertions.assertTrue(student.equals(secondStudent), "Студенты не равны");
        int firstHashCode = student.hashCode();
        int secondHashCode = secondStudent.hashCode();
        Assertions.assertEquals(firstHashCode, secondHashCode, "Хэшкоды не равны");
    }

    @DisplayName("hashCode() - одинаковый и устойчивый для одного объекта")
    @Test
    public void oneObjectShouldHaveConsistentHashCode() {
        int firstHashCode = student.hashCode();
        int secondHashCode = student.hashCode();
        int thirdHashCode = student.hashCode();
        Assertions.assertEquals(firstHashCode, secondHashCode, "Хэшкоды не равны");
        Assertions.assertEquals(firstHashCode, thirdHashCode, "Хэшкоды не равны");
    }
}
