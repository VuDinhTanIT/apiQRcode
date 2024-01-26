package com.vku.dtos;

import java.sql.Timestamp;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class MajorDTO {

	private int id;
	
	private String majorsID;
	

	private String majorsName;
	
	@NotNull
	@NotEmpty
	private String majorsNameStandFor;
	
	private boolean status;
	
	private Timestamp createTime;
	
	
	private Timestamp updateTime;
}
