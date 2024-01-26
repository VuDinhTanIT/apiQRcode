package com.vku.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.vku.dtos.MajorDTO;
import com.vku.models.Majors;
import com.vku.services.MajorService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/admin")
public class MajorController {
	@Autowired
	private MajorService majorService;
	@GetMapping("/majors")
//	@PreAuthorize("hasRole('TAIVU') or hasRole('DAOTAO') or hasRole('ADMIN') or hasRole('CTSV')")
	public ResponseEntity<Object> findAll(){
		return ResponseEntity.ok(majorService.findAll());
	}
	@RequestMapping(method = RequestMethod.POST, path = "major/create", produces = "application/json; charset=utf-8")
    @ResponseBody
	public String createmajor(@RequestBody @Valid Majors major){
		return majorService.save(major) != null ? "Thêm thành công":"Thêm thất bại" ;
	}
	@RequestMapping(method = RequestMethod.PUT, path = "major/edit/{id}", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String update (@RequestBody @Valid MajorDTO majorDTO, @Valid @NotNull @PathVariable("id") int id){
		this.majorService.Update(id, majorDTO);
		return majorService.getById(id) != null ? "Cập nhập thành công":"Cập nhập thất bại";
	}
	@GetMapping("/majors/search")
	public ResponseEntity<Object> findByKey(@RequestParam(value = "keyword", required  = false) String Key){
		return ResponseEntity.ok(majorService.searchByKeyword(Key));
	}
	
//	@GetMapping("/search")
//    public ResponseEntity<List<major>> searchSinhvienByKeyword(
//            @RequestParam("keyword") String keyword
//    ) {
//        List<major> majorList = majorService.searchByKeyword(keyword);
//        return ResponseEntity.ok(majorList);
//    }

}