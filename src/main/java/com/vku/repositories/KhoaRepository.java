package com.vku.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vku.models.Khoa;

public interface KhoaRepository extends JpaRepository<Khoa, Integer> {
	@Query("SELECT k FROM Khoa k WHERE k.khoa LIKE %:keyword% OR k.namBatDau LIKE %:keyword% OR k.namKetThuc LIKE %:keyword%")
	List<Khoa> searchByKeyword(String keyword);

	@Query("SELECT k FROM Khoa k WHERE k.status = true")
	List<Khoa> getKhoaNameByStatus();
}
