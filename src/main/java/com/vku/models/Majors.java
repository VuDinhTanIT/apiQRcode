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
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@Table(name = "nganh")
@NoArgsConstructor
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class Majors {
	@Id
	@Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "ma_nganh", nullable = false)
	private String majorsID;
	
	@Column(name = "ten_nganh", nullable = false)
	@NotNull
	@NotEmpty
	private String majorsName;
	
	@Column(name = "ten_nganh_viet_tat")
	@NotNull
	@NotEmpty
	private String majorsNameStandFor;
	
	@Column(name = "status", columnDefinition = "tinyInt(1)", nullable = false)
	private boolean status;
	
	@Column(name = "create_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false, nullable = false)
	private Timestamp createTime;
	
	
	@Column(name = "update_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false, nullable = false)
	private Timestamp updateTime;
	
	@JsonIgnore
	@OneToMany(mappedBy = "majors")
    private List<Curriculum> curriculums;

	
	@JsonIgnore
	@OneToMany(mappedBy = "majors")
	private List<Student> student;
}