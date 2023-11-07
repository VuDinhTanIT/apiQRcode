package com.vku.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vku.models.Officer;

@Repository
public interface OfficerRepository extends JpaRepository<Officer, Integer> {
    // Các phương thức tùy chỉnh khác có thể được khai báo ở đây nếu cần
}