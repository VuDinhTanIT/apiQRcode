package com.vku.models;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chuongtrinhhoc")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class Curriculum {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "tenchuontrinh", nullable = false)
	@NotNull
	@NotEmpty
	private String name;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_khoa")
	private Khoa khoa;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_nganh")
	private Majors majors;
	
	@Column(name = "status", columnDefinition = "tinyInt(1)", nullable = false)
	private boolean status;
	
	@Column(name = "create_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false, nullable = false)
	private Timestamp createTime;
	
	
	@Column(name = "update_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false, nullable = false)
	private Timestamp updateTime;
	

//	@JsonIgnore
//	@OneToMany(mappedBy = "curriculum")
//	private List<StudentCurriculum> studentCurriculum;
	
	@JsonIgnore
	@OneToMany(mappedBy = "curriculum")
	private List<Student> studentCurriculum;
	

}