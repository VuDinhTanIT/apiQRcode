package com.vku.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vku.models.Student_Course;

@Repository
public interface Student_CourseRepository extends JpaRepository<Student_Course, Long> {
    // Các phương thức tùy chỉnh khác có thể được khai báo ở đây nếu cần
}