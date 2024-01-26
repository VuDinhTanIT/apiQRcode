package com.vku.services;


import java.util.List;
import java.util.NoSuchElementException;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vku.dtos.MajorDTO;
import com.vku.models.Majors;
import com.vku.repositories.MajorRepository;




@Service
public class MajorService {
	@Autowired
	private MajorRepository majorRepository;
	public List<Majors> findAll(){
		return majorRepository.findAll();
	}
	
	public Majors save(Majors major) {
		major.setId(0);
		major = majorRepository.save(major);
		return major;
	}
	
	public void Update(Integer id, MajorDTO majorDTO) {
		Majors beanMajors = requireOne(id);
		BeanUtils.copyProperties(majorDTO, beanMajors);
		majorRepository.save(beanMajors);
	}
	
	 public Majors getById(Integer id) {
		 	Majors original = requireOne(id);
	        return original;
	    }
	 private Majors requireOne(Integer id) {
	        return majorRepository.findById(id)
	                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
	    }
	 public List<Majors> searchByKeyword(String Keyword) {
		 return majorRepository.searchByKeyword(Keyword);
	 }
}