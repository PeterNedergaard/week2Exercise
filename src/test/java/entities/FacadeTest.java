package entities;

import mappers.StudentInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FacadeTest {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
    Facade facade = new Facade();

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void getAllStudentsTest(){
        System.out.println("Get all students");
        EntityManager em = emf.createEntityManager();

        List<Student> expected = em.createQuery("SELECT s FROM Student s",Student.class).getResultList();
        List<Student> actual = facade.getAllStudents();

        assertEquals(expected,actual);
    }

    @Test
    public void getStudentsByNameTest(){
        System.out.println("Get students by name");
        EntityManager em = emf.createEntityManager();

        List<Student> expected = em.createQuery("SELECT s FROM Student s WHERE s.firstname = 'Anders'",Student.class).getResultList();
        List<Student> actual = facade.getStudentsByName("Anders");

        assertEquals(expected,actual);
    }

    @Test
    public void addStudentTest(){
        System.out.println("Add student");

        Student expected = new Student("Kim","Kimsen",2);
        Student actual = facade.addStudent("Kim","Kimsen",2);

        assertEquals(expected,actual);
    }

    @Test
    public void assignToNewSemTest(){
        System.out.println("Assign student to new semester");
        EntityManager em = emf.createEntityManager();

        long idOfStudent = 9;
        long expected = 1;
        long actual = facade.assignToNewSem(1,em.find(Student.class,idOfStudent));

        assertEquals(expected,actual);
    }

    @Test
    public void getStudentsByLastNameTest(){
        System.out.println("Get students by lastname");
        EntityManager em = emf.createEntityManager();

        List<Student> expected = em.createQuery("SELECT s FROM Student s WHERE s.lastname = 'And'",Student.class).getResultList();
        List<Student> actual = facade.getStudentsByLastName("And");

        assertEquals(expected,actual);
    }

    @Test
    public void NumOfStudentsBySemNameTest(){
        System.out.println("Total number of students by semester name");
        EntityManager em = emf.createEntityManager();

        String semesterName = "CLdat-b14e";
        int idOfSemester = em.createQuery("SELECT s.id FROM Semester s WHERE s.name = '" + semesterName + "'",Semester.class).getFirstResult();

        List<Student> expectedListOfStudents = em.createQuery("SELECT s FROM Student s WHERE s.currentSemester_id = '" + idOfSemester + "'",Student.class).getResultList();

        int expected = expectedListOfStudents.size();
        int actual = facade.NumOfStudentsBySemName(semesterName);

        assertEquals(expected,actual);
    }

    @Test
    public void numOfAllStudentsTest(){
        System.out.println("Number of all students");
        EntityManager em = emf.createEntityManager();

        int expected = em.createQuery("SELECT COUNT(s) FROM Student s",Student.class).getFirstResult();
        int actual = facade.numOfAllStudents();

        assertEquals(expected,actual);
    }

    @Test
    public void busiestTeacherTest(){
        System.out.println("The busiest teacher");
        EntityManager em = emf.createEntityManager();

        //TypedQuery<Teacher> tq = em.createQuery("SELECT ts, COUNT(ts) AS  FROM TeacherSemester ts",Teacher.class);

    }


    @Test
    public void studentsAsStudentInfoTest(){
        System.out.println("Students as StudentInfo");
        EntityManager em = emf.createEntityManager();

        List<Long> expectedListOfStudentIds = new ArrayList<>();
        List<Long> actualListOfStudentIds = new ArrayList<>();

        for (Student s : em.createQuery("SELECT s FROM Student s",Student.class).getResultList()) {
            expectedListOfStudentIds.add(s.getId());
        }

        for (StudentInfo si : facade.studentsAsStudentInfo()) {
            actualListOfStudentIds.add(si.studentId);
        }

        List<Long> expected = expectedListOfStudentIds;
        List<Long> actual = actualListOfStudentIds;

        assertEquals(expected,actual);
    }

    @Test
    public void getStudentInfoByIdTest(){
        System.out.println("Get StudentInfo by id");

        StudentInfo actualStudentInfo = facade.getStudentInfoByID(4);
        String expected = "Jane Doe";
        String actual = actualStudentInfo.fullName;

        assertEquals(expected,actual);
    }

}