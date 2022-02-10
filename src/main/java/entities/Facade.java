package entities;

import mappers.StudentInfo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class Facade {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");

    public List<Student> getAllStudents() {
        EntityManager em = emf.createEntityManager();

        return em.createQuery("SELECT s FROM Student s", Student.class).getResultList();
    }


    public List<Student> getStudentsByName(String name) {
        EntityManager em = emf.createEntityManager();

        return em.createQuery("SELECT s FROM Student s WHERE s.firstname = '" + name + "'", Student.class).getResultList();
    }


    public Student addStudent(String firstName, String lastName, int currentSemester_id) {
        EntityManager em = emf.createEntityManager();
        Student studentToAdd = new Student(firstName,lastName,currentSemester_id);

        try {
            em.getTransaction().begin();
            em.persist(studentToAdd);
            em.getTransaction().commit();

            TypedQuery<Student> tq = em.createQuery("SELECT s FROM Student s ORDER BY s.id DESC",Student.class);
            tq.setMaxResults(1);
            return tq.getSingleResult();
        } finally {
            em.close();
        }
    }


    public long assignToNewSem(int newSemester, Student student) {
        EntityManager em = emf.createEntityManager();

        Student found = em.find(Student.class,student.getId());
        found.setCurrentSemester_id(newSemester);

        try {
            em.getTransaction().begin();
            em.merge(found);
            em.getTransaction().commit();

            return em.find(Student.class,student.getId()).getCurrentSemester_id();
        } finally {
            em.close();
        }

    }


    public List<Student> getStudentsByLastName(String lastName) {
        EntityManager em = emf.createEntityManager();

        return em.createQuery("SELECT s FROM Student s WHERE s.lastname = '" + lastName + "'", Student.class).getResultList();
    }


    public int NumOfStudentsBySemName(String semesterName) {
        EntityManager em = emf.createEntityManager();

        int idOfSemester = em.createQuery("SELECT s.id FROM Semester s WHERE s.name = '" + semesterName + "'",Semester.class).getFirstResult();

        List<Student> listOfStudents = em.createQuery("SELECT s FROM Student s WHERE s.currentSemester_id = '" + idOfSemester + "'",Student.class).getResultList();

        return listOfStudents.size();
    }


    public int numOfAllStudents() {
        EntityManager em = emf.createEntityManager();

        return em.createQuery("SELECT COUNT(s) FROM Student s",Student.class).getFirstResult();
    }


    public List<StudentInfo> studentsAsStudentInfo() {
        EntityManager em = emf.createEntityManager();

        List<Student> studentList = em.createQuery("SELECT s FROM Student s",Student.class).getResultList();
        List<StudentInfo> studentInfoList = new ArrayList<>();

        for (Student s : studentList) {
            studentInfoList.add(new StudentInfo(s.getId()));
        }

        return studentInfoList;
    }


    public StudentInfo getStudentInfoByID(long id) {
        StudentInfo found = null;

        for (StudentInfo si : studentsAsStudentInfo()) {
            if (si.studentId == id){
                found = si;
            }
        }

        return found;
    }
}
