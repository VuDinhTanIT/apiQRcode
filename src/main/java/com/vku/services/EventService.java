package com.vku.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vku.models.Event;
import com.vku.repositories.EventRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }
    public List<Event> getAllEventsByOrderByUpdateTimeDesc() {
        return eventRepository.findAllByOrderByUpdateTime();
    }

    public Event getEventById(Integer id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Event not found: " + id));
    }
//    public Event getEvnetByIdStr(String id) {
//    	return getEventById(Integer.parseInt(id));
//    }

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public Event updateEvent(Event event) {
        return eventRepository.save(event);
    }

    public void deleteEvent(Integer id) {
        eventRepository.deleteById(id);
    }
}
