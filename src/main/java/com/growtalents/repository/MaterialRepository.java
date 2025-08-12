package com.growtalents.repository;

import com.growtalents.enums.MaterialType;
import com.growtalents.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MaterialRepository extends JpaRepository<Material, String> {
    // Lấy 1 component (mỗi course + type là duy nhất theo unique constraint)
    Optional<Material> findByCourse_CourseIdAndType(String courseId, MaterialType type);

    // Kiểm tra tồn tại (phục vụ delete/update)
    boolean existsByCourse_CourseIdAndType(String courseId, MaterialType type);

    // Xoá 1 component theo course + type
    void deleteByCourse_CourseIdAndType(String courseId, MaterialType type);
}
