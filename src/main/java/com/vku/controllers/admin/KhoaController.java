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

import com.vku.dtos.KhoaDTO;
import com.vku.models.Khoa;
import com.vku.services.KhoaService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/admin")
public class KhoaController {
	@Autowired
	private KhoaService khoaService;
	@GetMapping("/khoa")
//	@PreAuthorize("hasRole('TAIVU') or hasRole('DAOTAO') or hasRole('ADMIN') or hasRole('CTSV')")
	public ResponseEntity<Object> findAll(){
		return ResponseEntity.ok(khoaService.findAll());
	}
	@RequestMapping(method = RequestMethod.POST, path = "khoa/create", produces = "application/json; charset=utf-8")
    @ResponseBody
	public String createKhoa(@RequestBody @Valid Khoa khoa){
		return khoaService.save(khoa) != null ? "Thêm thành công":"Thêm thất bại" ;
	}
	@RequestMapping(method = RequestMethod.PUT, path = "khoa/edit/{id}", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String update (@RequestBody @Valid KhoaDTO khoaDTO, @Valid @NotNull @PathVariable("id") int id){
		this.khoaService.Update(id, khoaDTO);
		return khoaService.getById(id) != null ? "Cập nhập thành công":"Cập nhập thất bại";
	}
	@GetMapping("/khoa/search")
	public ResponseEntity<Object> findByKey(@RequestParam(value = "keyword", required  = false) String Key){
		return ResponseEntity.ok(khoaService.searchByKeyword(Key));
	}
	
//	@GetMapping("/search")
//    public ResponseEntity<List<Khoa>> searchSinhvienByKeyword(
//            @RequestParam("keyword") String keyword
//    ) {
//        List<Khoa> khoaList = khoaService.searchByKeyword(keyword);
//        return ResponseEntity.ok(khoaList);
//    }

}