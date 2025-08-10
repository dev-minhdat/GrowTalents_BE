package com.growtalents.repository;

import com.growtalents.model.ClassSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ClassSessionRepository extends JpaRepository<ClassSession, String> {

    // Kiểm tra xem giáo viên này trong ngày đó có buổi khác bị overlap hay không
    @Query("""
       select count(cs) > 0
       from ClassSession cs
       join cs.course c
       join TeacherCourse tc on tc.course = c
       where tc.teacher.userId = :teacherId
         and cs.sessionDate = :date
         and cs.sessionId <> :excludeSessionId
         and cs.startTime < :proposedEnd
         and cs.endTime   > :proposedStart
    """)
    boolean existsTimeConflict(@Param("teacherId") String teacherId,
                               @Param("date") LocalDate date,
                               @Param("proposedStart") LocalTime proposedStart,
                               @Param("proposedEnd") LocalTime proposedEnd,
                               @Param("excludeSessionId") String excludeSessionId);

    // Lấy danh sách các session có đề xuất dời lịch của giáo viên (không đụng entity)
    @Query("""
    select cs
    from ClassSession cs
    join cs.course c
    join TeacherCourse tc on tc.course = c
    where tc.teacher.userId = :teacherId
      and cs.rescheduleStatus is not null
    order by cs.rescheduleSubmittedAt desc
""")
    List<ClassSession> findReschedulesByTeacher(@Param("teacherId") String teacherId);

}