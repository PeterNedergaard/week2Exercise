package entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class TeacherSemesterPK implements Serializable {
    private long teachingId;
    private long teachersId;

    @Id
    public long getTeachingId() {
        return teachingId;
    }

    public void setTeachingId(long teachingId) {
        this.teachingId = teachingId;
    }

    @Id
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

        TeacherSemesterPK that = (TeacherSemesterPK) o;

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
