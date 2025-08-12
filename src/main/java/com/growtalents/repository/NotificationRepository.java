package com.growtalents.repository;

import com.growtalents.model.Notification;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, String> {

    @Query("""
      select n from Notification n
      where n.receiver.userId = :studentId
      order by n.createdAt desc
    """)
    List<Notification> findAllByStudent(@Param("studentId") String studentId);

    @Query("""
      select n from Notification n
      where n.receiver.userId = :studentId
        and n.course.courseId = :courseId
      order by n.createdAt desc
    """)
    List<Notification> findAllByStudentAndCourse(
            @Param("studentId") String studentId,
            @Param("courseId") String courseId
    );
}


