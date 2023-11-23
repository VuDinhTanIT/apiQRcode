package com.vku.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vku.models.Guest;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Integer> {

	Guest findByPhoneNumberAndCccd(String phoneNumber, String cccd);
    // Các phương thức tùy chỉnh khác có thể được khai báo ở đây nếu cần
}