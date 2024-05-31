package com.vku.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vku.models.SchoolYear_Semester;

@Repository
public interface SchoolYear_SemesterRepository extends JpaRepository<SchoolYear_Semester, Integer> {
    // Các phương thức tùy chỉnh khác có thể được khai báo ở đây nếu cần
//	List<SchoolYear_Semester> findAllByOrderByUpdateTime();
//	SchoolYear_Semester findByCurrent(boolean b);
}