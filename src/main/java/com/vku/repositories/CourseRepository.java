package com.vku.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vku.models.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    // Các phương thức tùy chỉnh khác có thể được khai báo ở đây nếu cần
}