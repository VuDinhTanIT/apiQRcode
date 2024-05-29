package com.vku.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DataRespone<T1, T2> {
//	public DataRespone(QR_Parameters param, Object data) {
//		// TODO Auto-generated constructor stub
//	}
	T1 msg;
	T2 obj;
}
	