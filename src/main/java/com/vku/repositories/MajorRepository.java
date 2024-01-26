package com.vku.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vku.models.Khoa;
import com.vku.models.Majors;

public interface MajorRepository extends JpaRepository<Majors, Integer> {
	@Query("SELECT m FROM Majors m WHERE m.majorsID LIKE %:keyword% OR m.majorsName LIKE %:keyword%")
	List<Majors> searchByKeyword(String keyword);
}
