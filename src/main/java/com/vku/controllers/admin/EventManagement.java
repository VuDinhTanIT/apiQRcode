package com.vku.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vku.controllers.QRcode;
import com.vku.dtos.ErrorResponse;
import com.vku.models.Event;
import com.vku.services.EventService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/admin/events")
public class EventManagement{

    @Autowired
    private EventService eventService;
    @Autowired
    private QRcode qRcode;

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.getAllEventsByOrderByUpdateTimeDesc();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEventById(@PathVariable("id") Integer id) {
        try {
            Event event = eventService.getEventById(id);
            return ResponseEntity.ok(event);
        } catch (NoSuchElementException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), 404);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("An error occurred", 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event, HttpServletRequest request) throws Exception {
    	String eventQR = qRcode.generateQRcodeWithLogo(event.getQRCodeEvent(), request, "event");
    	event.setQRCodeEvent(eventQR);
        Event createdEvent = eventService.createEvent(event);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvent(@PathVariable("id") Integer id, @RequestBody Event event) {
        try {
            Event existingEvent = eventService.getEventById(id);
            event.setId(id);
            Event updatedEvent = eventService.updateEvent(event);
            return new ResponseEntity<>(updatedEvent, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), 404);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("An error occurred", 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable("id") Integer id) {
        Event existingEvent = eventService.getEventById(id);
        if (existingEvent == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        eventService.deleteEvent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
