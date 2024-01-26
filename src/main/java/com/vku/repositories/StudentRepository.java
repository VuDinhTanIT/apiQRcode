package com.vku.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.vku.models.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, String>, JpaSpecificationExecutor<Student> {

	// Các phương thức tùy chỉnh khác có thể được khai báo ở đây nếu cần

	Student findByStudentCode(String studentCode);

	@Query("SELECT s FROM Student s " + "WHERE (:cccd IS NULL OR LOWER(s.cccd) LIKE CONCAT('%', LOWER(:cccd), '%')) "
			+ "AND (:studentCode IS NULL OR LOWER(s.studentCode) LIKE CONCAT('%', LOWER(:studentCode), '%')) "
			+ "AND (:name IS NULL OR LOWER(s.name) LIKE CONCAT('%', LOWER(:name), '%')) "
			+ "AND (:major IS NULL OR s.majors = :major) " + "AND (:khoa IS NULL OR s.khoa = :khoa) "
			+ "ORDER BY s.studentCode")
	List<Student> findByOptions(String cccd, String studentCode, String name, Integer major, Integer khoa);

}