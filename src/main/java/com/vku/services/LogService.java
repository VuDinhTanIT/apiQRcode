package com.vku.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vku.models.Log;
import com.vku.repositories.LogRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class LogService {
	@Autowired
	private LogRepository LogRepository;

	public List<Log> getAllLogs() {
		return LogRepository.findAll();
	}

	public Log getLogById(Long id) {
		return LogRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
	}

	public void wirteLog(Log log) {
		log.setId(null);
		LogRepository.save(log);
	}

//    public Log updateLog(Log Log) {
//        return LogRepository.save(Log);
//    }
//
//    public void deleteLog(Long id) {
//        LogRepository.deleteById(id);
//    }
}