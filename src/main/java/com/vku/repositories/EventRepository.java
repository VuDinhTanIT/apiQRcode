package com.vku.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vku.models.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    // Các phương thức tùy chỉnh khác có thể được khai báo ở đây nếu cần
}