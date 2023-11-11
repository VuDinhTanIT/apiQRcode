package com.vku.controllers.admin;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vku.dtos.ErrorResponse;
import com.vku.models.Log;
import com.vku.models.Officer;
import com.vku.services.LogService;
import com.vku.services.OfficerService;

@RestController
@RequestMapping("api/admin/officers")
public class OfficerManagement {

   @Autowired
   private OfficerService officerService;
   @Autowired
   private LogService logService;
    @GetMapping
    public List<Officer> getAllOfficers() {
        return officerService.getAllOfficers();
    }

    @GetMapping("/{id}")
    public Officer getOfficerById(@PathVariable("id") int officerId) {
        return officerService.getOfficerById(officerId);
    }

    @PostMapping
    public Officer createOfficer(@RequestBody Officer officer) {
    	Officer officerCreated =  officerService.createOfficer(officer);
    	if(officer != null ) {
    		Log log = new Log();
//          log.setActor();
          log.setLog("Thêm officer: "+ officer.getOfficerCode() + " - " + officer.getName() );
          logService.wirteLog(log);
    	}
        return officerCreated ;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOfficer(@PathVariable("id") int officerId, @RequestBody Officer updatedOfficer) {
//        return officerService.updateOfficer(officerId, updatedOfficer);
    	Log log = new Log();
        try {
        	Officer officer = officerService.getOfficerById(officerId);
        	if(officer != null ) {
//              log.setActor();
              log.setLog("Sửa officer với id: " + officerId +" - mã cán bộ:"+ officer.getOfficerCode() + " - " + officer.getName() );
              logService.wirteLog(log);
        	}
        	return new ResponseEntity<>(officerService.updateOfficer(officerId, updatedOfficer), HttpStatus.OK);
          
        } catch (NoSuchElementException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), 404);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Đã xảy ra lỗi", 500);
            log.setLog("Sửa thất bại với id: " + officerId +  " - " + e.getMessage());
            logService.wirteLog(log);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteOfficer(@PathVariable("id") int officerId) {
        officerService.deleteOfficer(officerId);
    }
}