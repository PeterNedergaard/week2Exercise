package mappers;

import entities.Semester;
import entities.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class StudentInfo {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");

    public String fullName;
    public long studentId;
    public String classNameThisSemenster;
    public String classDescription;

    public StudentInfo(long studentId) {
        EntityManager em = emf.createEntityManager();
        Student student = em.find(Student.class,studentId);
        Semester semester = em.find(Semester.class,student.getCurrentSemester_id());

        this.studentId = studentId;
        this.fullName = student.getFirstname() + " " + student.getLastname();
        this.classNameThisSemenster = semester.getName();
        this.classDescription = semester.getDescription();
    }
}
