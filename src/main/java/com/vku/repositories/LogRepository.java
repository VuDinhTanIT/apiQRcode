package com.vku.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vku.models.Log;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
    // Các phương thức tùy chỉnh khác có thể được khai báo ở đây nếu cần
}