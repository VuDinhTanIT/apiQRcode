package com.vku.models;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name  = "khoa")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class Khoa {
	@Id
	@Column(name = "id", nullable = false)
	@PrimaryKeyJoinColumn(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "khoa",nullable = false )
	private String khoa;
	
	@Column(name = "nambatdau",nullable = false)
	private Integer namBatDau;
	
	@Column(name = "namketthuc",nullable = false)
	private Integer namKetThuc;
	
	@Column(name = "thoigianbatdau", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
	private Timestamp thoiGianBatDau ;
	
	@Column(name = "thoigianketthuc",columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP",  nullable = false)
	private Timestamp thoiGianKetThuc;
	
	@Column(name = "status", columnDefinition = "tinyInt(1)", nullable = false)
	private boolean status;
	
	
	@Column(name = "create_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false, nullable = false)
	private Timestamp createTime;
	
	
	@Column(name = "update_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false, nullable = false)
	private Timestamp updateTime;
	
	@JsonIgnore
	@OneToMany(mappedBy = "khoa")
    private List<Curriculum> curriculums;
	

	
	@JsonIgnore
	@OneToMany(mappedBy = "khoa")
	private List<Student> student;


}