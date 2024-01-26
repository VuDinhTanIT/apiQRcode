package com.vku.services;


import java.util.List;
import java.util.NoSuchElementException;

import javax.sound.midi.VoiceStatus;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vku.dtos.KhoaDTO;
import com.vku.models.Khoa;
import com.vku.repositories.KhoaRepository;




@Service
public class KhoaService {
	@Autowired
	private KhoaRepository khoaRepository;
	public List<Khoa> findAll(){
		return khoaRepository.findAll();
	}
	
	public Khoa save(Khoa khoa) {
		khoa.setId(null);
		khoa = khoaRepository.save(khoa);
		return khoa;
	}
	
	public void Update(Integer id, KhoaDTO khoaDTO) {
		Khoa beanKhoa = requireOne(id);
		BeanUtils.copyProperties(khoaDTO, beanKhoa);
		khoaRepository.save(beanKhoa);
	}
	
	 public Khoa getById(Integer id) {
		 	Khoa original = requireOne(id);
	        return original;
	    }
	 private Khoa requireOne(Integer id) {
	        return khoaRepository.findById(id)
	                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
	    }
	 public List<Khoa> searchByKeyword(String Keyword) {
		 return khoaRepository.searchByKeyword(Keyword);
	 }
}