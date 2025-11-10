package org.autotests.task3;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("API тесты на Students Service из курса")
public class StudentsServiceTests {
    private final Faker faker = new Faker();
    private String name;
    private int id;

    private final String baseUri = "http://localhost:8080";
    private final String studentEndpoint = "/student";
    private final String topStudentEndpoint = "/topStudent";
    private final String nameField = "name";
    private final String idField = "id";

    @BeforeEach
    public void prepare() {
        name = faker.name().fullName();
        id = faker.hashCode();
    }

    @DisplayName("get /student/{id} возвращает JSON студента с ID и именем, если такой есть в базе, код 200")
    @Test
    public void getStudentIdReturnsExistentStudentIdAndNameCode200Test() {
        //Prepare
        StudentModel student = new StudentModel(id, name, new int[]{});
        RestAssured.given()
                .baseUri(baseUri)
                .contentType(ContentType.JSON)
                .body(student)
                .when()
                .post(studentEndpoint)
                .then()
                .statusCode(201);
        //Act-Assert
        RestAssured.given()
                .baseUri(baseUri + studentEndpoint + "/" + id)
                .when()
                .get()
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(nameField, Matchers.equalTo(name))
                .body(idField, Matchers.equalTo(id));
        //Cleanup
        RestAssured.given()
                .baseUri(baseUri)
                .when()
                .delete(studentEndpoint + "/" + id)
                .then()
                .statusCode(200);
    }

    @DisplayName("get /student/{id} возвращает код 404, если студента с данным ID в базе нет")
    @Test
    public void getStudentIdReturns404ForNonExistentStudentTest() {
        RestAssured.given()
                .baseUri(baseUri)
                .when()
                .get(studentEndpoint + "/" + id)
                .then()
                .statusCode(404);
    }

    @DisplayName("post /student добавляет студента с новым ID и заполненным именем в базу, код 201")
    @Test
    public void postStudentAddsStudentWithNewIdAndFilledNameToDbCode201Test() {
        //Act
        StudentModel student = new StudentModel(id, name, new int[]{});
        RestAssured.given()
                .baseUri(baseUri)
                .contentType(ContentType.JSON)
                .body(student)
                .when()
                .post(studentEndpoint)
                .then()
                .statusCode(201);
        //Assert
        RestAssured.given()
                .baseUri(baseUri)
                .when()
                .get(studentEndpoint + "/" + id)
                .then()
                .body(nameField, Matchers.equalTo(name))
                .body(idField, Matchers.equalTo(id));
        //Cleanup
        RestAssured.given()
                .baseUri(baseUri)
                .when()
                .delete(studentEndpoint + "/" + id)
                .then()
                .statusCode(200);
    }

    @DisplayName("post /student обновляет в базе студента с уже имеющимся ID и заполненным именем, код 201")
    @Test
    public void postStudentUpdatesStudentWithExistentIdAndFilledNameCode201Test() {
        //Prepare
        StudentModel student = new StudentModel(id, name, new int[]{});
        String newName = faker.name().fullName();
        RestAssured.given()
                .baseUri(baseUri)
                .body(student)
                .contentType(ContentType.JSON)
                .when()
                .post(studentEndpoint)
                .then()
                .statusCode(201);
        //Act
        student.setName(newName);
        RestAssured.given()
                .baseUri(baseUri)
                .body(student)
                .contentType(ContentType.JSON)
                .when()
                .post(studentEndpoint)
                .then()
                .statusCode(201);
        //Assert
        RestAssured.given()
                .baseUri(baseUri)
                .when()
                .get(studentEndpoint + "/" + id)
                .then()
                .body(nameField, Matchers.equalTo(newName))
                .body(idField, Matchers.equalTo(id));
        //Cleanup
        RestAssured.given()
                .baseUri(baseUri)
                .when()
                .delete(studentEndpoint + "/" + id)
                .then()
                .statusCode(200);
    }

    @DisplayName("post /student добавляет студента в базу, если ID null, то возвращается назначенный ID, код 201")
    @Test
    public void postStudentAddsStudentWithNullIdAndReturnsSomeIdCode201Test() {
        //Prepare
        StudentModel student = new StudentModel(null, name, new int[]{});
        //Act
        Integer idFromResponse = RestAssured.given()
                .baseUri(baseUri)
                .contentType(ContentType.JSON)
                .body(student)
                .when()
                .post(studentEndpoint)
                .then()
                .statusCode(201)
                .extract().as(Integer.class);
        //Assert
        RestAssured.given()
                .baseUri(baseUri)
                .when()
                .get(studentEndpoint + "/" + idFromResponse)
                .then()
                .body(nameField, Matchers.equalTo(name))
                .body(idField, Matchers.equalTo(idFromResponse));
        //Cleanup
        RestAssured.given()
                .baseUri(baseUri)
                .when()
                .delete(studentEndpoint + "/" + idFromResponse)
                .then()
                .statusCode(200);
    }

    @DisplayName("post /student возвращает код 400, если имя не заполнено")
    @Test
    public void postStudentReturns400IfNameIsNotFilledTest() {
        //Prepare
        StudentModel student = new StudentModel(id, null, new int[]{});
        //Act-Assert
        RestAssured.given()
                .baseUri(baseUri)
                .contentType(ContentType.JSON)
                .body(student)
                .when()
                .post(studentEndpoint)
                .then()
                .statusCode(400);
    }

    @DisplayName("delete /student/{id} удаляет студента с указанным ID из базы, код 200")
    @Test
    public void deleteStudentIdDeletesExistentStudentCode200Test() {
        //Prepare
        StudentModel student = new StudentModel(id, name, new int[]{});
        RestAssured.given()
                .baseUri(baseUri)
                .contentType(ContentType.JSON)
                .body(student)
                .when()
                .post(studentEndpoint)
                .then()
                .statusCode(201);
        //Act
        RestAssured.given()
                .baseUri(baseUri)
                .when()
                .delete(studentEndpoint + "/" + id)
                .then()
                .statusCode(200);
        //Assert
        RestAssured.given()
                .baseUri(baseUri)
                .when()
                .get(studentEndpoint + "/" + id)
                .then()
                .statusCode(404);
    }

    @DisplayName("delete /student/{id} возвращает код 404, если студента с таким ID в базе нет")
    @Test
    public void deleteStudentIdReturns404ForNonExistentStudentTest() {
        //Act-Assert
        RestAssured.given()
                .baseUri(baseUri)
                .when()
                .delete(studentEndpoint + "/" + id)
                .then()
                .statusCode(404);
    }

    @DisplayName("get /topStudent код 200 и пустое тело, если студентов в базе нет")
    @Test
    public void getTopStudentReturnsCode200AndEmptyBodyIfNoStudentsInDbTest() {
        //Act-Assert
        String response = RestAssured.given()
                .baseUri(baseUri)
                .when()
                .get(topStudentEndpoint)
                .then()
                .statusCode(200)
                .extract().body().asString();
        assertTrue(response.trim().isEmpty());
    }

    @DisplayName("get /topStudent код 200 и пустое тело, если ни у кого из студентов в базе нет оценок")
    @Test
    public void getTopStudentReturnsCode200AndEmptyBodyIfNoStudentsHaveMarksInDbTest() {
        //Prepare
        StudentModel student = new StudentModel(id, name, new int[]{});
        RestAssured.given()
                .baseUri(baseUri)
                .contentType(ContentType.JSON)
                .body(student)
                .when()
                .post(studentEndpoint)
                .then()
                .statusCode(201);
        //Assert
        String response = RestAssured.given()
                .baseUri(baseUri)
                .when()
                .get(topStudentEndpoint)
                .then()
                .statusCode(200)
                .extract().body().asString();
        assertTrue(response.trim().isEmpty());
        //Cleanup
        RestAssured.given()
                .baseUri(baseUri)
                .when()
                .delete(studentEndpoint + "/" + id)
                .then()
                .statusCode(200);
    }

    @DisplayName("get /topStudent код 200 и один студент, если у него максимальная средняя оценка")
    @Test
    public void getTopStudentReturnsCode200AndOneStudentWithMaxAverageMarkTest() {
        //Prepare
        StudentModel student = new StudentModel(id, name, new int[]{5, 5, 5});
        RestAssured.given()
                .baseUri(baseUri)
                .contentType(ContentType.JSON)
                .body(student)
                .log().all()
                .when()
                .post(studentEndpoint)
                .then()
                .statusCode(201)
                .log().all();
        int secondId = id + 1;
        StudentModel secondStudent = new StudentModel(secondId, faker.name().fullName(), new int[]{3, 4, 5});
        RestAssured.given()
                .baseUri(baseUri)
                .contentType(ContentType.JSON)
                .body(secondStudent)
                .log().all()
                .when()
                .post(studentEndpoint)
                .then()
                .statusCode(201)
                .log().all();
        //Act-Assert
        List<StudentModel> response = RestAssured.given()
                .baseUri(baseUri)
                .when()
                .get(topStudentEndpoint)
                .then()
                .log().all()
                .statusCode(200)
                .extract().as(new TypeRef<>() {
                });
        assertEquals(1, response.size());
        assertEquals(student.getId(), response.get(0).getId());
        //Cleanup
        RestAssured.given()
                .baseUri(baseUri)
                .when()
                .delete(studentEndpoint + "/" + id)
                .then()
                .statusCode(200);
        RestAssured.given()
                .baseUri(baseUri)
                .when()
                .delete(studentEndpoint + "/" + secondId)
                .then()
                .statusCode(200);
    }

    @DisplayName("get /topStudent код 200 и один студент, если среди всех студентов с максимальной средней оценкой " +
            "у него их больше всего")
    @Test
    public void getTopStudentReturnsCode200AndOneStudentWithMostMaxAverageMarksAmongOthersTest() {
        //Prepare
        StudentModel student = new StudentModel(id, name, new int[]{3, 5, 3, 5});
        RestAssured.given()
                .baseUri(baseUri)
                .contentType(ContentType.JSON)
                .body(student)
                .log().all()
                .when()
                .post(studentEndpoint)
                .then()
                .statusCode(201)
                .log().all();
        int secondId = id + 1;
        StudentModel secondStudent = new StudentModel(secondId, faker.name().fullName(), new int[]{4, 4, 4});
        RestAssured.given()
                .baseUri(baseUri)
                .contentType(ContentType.JSON)
                .body(secondStudent)
                .log().all()
                .when()
                .post(studentEndpoint)
                .then()
                .statusCode(201)
                .log().all();
        int thirdId = id + 2;
        StudentModel thirdStudent = new StudentModel(thirdId, faker.name().fullName(), new int[]{4, 4, 4, 4, 4});
        RestAssured.given()
                .baseUri(baseUri)
                .contentType(ContentType.JSON)
                .body(thirdStudent)
                .log().all()
                .when()
                .post(studentEndpoint)
                .then()
                .statusCode(201)
                .log().all();
        //Act-Assert
        List<StudentModel> response = RestAssured.given()
                .baseUri(baseUri)
                .when()
                .get(topStudentEndpoint)
                .then()
                .log().all()
                .statusCode(200)
                .extract().as(new TypeRef<>() {
                });
        assertEquals(1, response.size());
        assertEquals(thirdId, response.get(0).getId());
        //Cleanup
        RestAssured.given()
                .baseUri(baseUri)
                .when()
                .delete(studentEndpoint + "/" + id)
                .then()
                .statusCode(200);
        RestAssured.given()
                .baseUri(baseUri)
                .when()
                .delete(studentEndpoint + "/" + secondId)
                .then()
                .statusCode(200);
        RestAssured.given()
                .baseUri(baseUri)
                .when()
                .delete(studentEndpoint + "/" + thirdId)
                .then()
                .statusCode(200);
    }

    @DisplayName("get /topStudent код 200 и несколько студентов, если у них всех эта оценка максимальная и при этом они равны по количеству оценок")
    @Test
    public void getTopStudentReturns200AndSeveralStudentsWithMaxAverageMarkAndSameMarksCount() {
        //Prepare
        StudentModel student = new StudentModel(id, name, new int[]{2, 2, 2, 2});
        RestAssured.given()
                .baseUri(baseUri)
                .contentType(ContentType.JSON)
                .body(student)
                .log().all()
                .when()
                .post(studentEndpoint)
                .then()
                .statusCode(201)
                .log().all();
        int secondId = id + 1;
        StudentModel secondStudent = new StudentModel(secondId, faker.name().fullName(), new int[]{2, 2, 2, 2});
        RestAssured.given()
                .baseUri(baseUri)
                .contentType(ContentType.JSON)
                .body(secondStudent)
                .log().all()
                .when()
                .post(studentEndpoint)
                .then()
                .statusCode(201)
                .log().all();
        int thirdId = id + 2;
        StudentModel thirdStudent = new StudentModel(thirdId, faker.name().fullName(), new int[]{2, 2, 2, 2});
        RestAssured.given()
                .baseUri(baseUri)
                .contentType(ContentType.JSON)
                .body(thirdStudent)
                .log().all()
                .when()
                .post(studentEndpoint)
                .then()
                .statusCode(201)
                .log().all();
        //Act-Assert
        List<StudentModel> response = RestAssured.given()
                .baseUri(baseUri)
                .when()
                .get(topStudentEndpoint)
                .then()
                .log().all()
                .statusCode(200)
                .extract().as(new TypeRef<>() {
                });
        assertEquals(3, response.size());
        assertEquals(id, response.get(0).getId());
        assertEquals(secondId, response.get(1).getId());
        assertEquals(thirdId, response.get(2).getId());
        //Cleanup
        RestAssured.given()
                .baseUri(baseUri)
                .when()
                .delete(studentEndpoint + "/" + id)
                .then()
                .statusCode(200);
        RestAssured.given()
                .baseUri(baseUri)
                .when()
                .delete(studentEndpoint + "/" + secondId)
                .then()
                .statusCode(200);
        RestAssured.given()
                .baseUri(baseUri)
                .when()
                .delete(studentEndpoint + "/" + thirdId)
                .then()
                .statusCode(200);
    }
}
