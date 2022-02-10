package entities;

import javax.persistence.*;

@Entity
@IdClass(TeacherSemesterPK.class)
public class TeacherSemester {

    @Id
    private long teachingId;

    @Id
    private long teachersId;

    public long getTeachingId() {
        return teachingId;
    }

    public void setTeachingId(long teachingId) {
        this.teachingId = teachingId;
    }

    public long getTeachersId() {
        return teachersId;
    }

    public void setTeachersId(long teachersId) {
        this.teachersId = teachersId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TeacherSemester that = (TeacherSemester) o;

        if (teachingId != that.teachingId) return false;
        if (teachersId != that.teachersId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (teachingId ^ (teachingId >>> 32));
        result = 31 * result + (int) (teachersId ^ (teachersId >>> 32));
        return result;
    }
}
