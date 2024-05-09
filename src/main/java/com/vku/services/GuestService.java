package com.vku.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vku.models.Guest;
import com.vku.repositories.GuestRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class GuestService {
	@Autowired
	private GuestRepository guestRepository;

	public List<Guest> getAllGuests() {
		return guestRepository.findAll();
	}

	public Guest getGuestById(int id) {
		return guestRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
	}

	public Guest createGuest(Guest guest) {
		guest.setId(0);
		return guestRepository.save(guest);
	}

	public Guest updateGuest(Guest guest) {
		return guestRepository.save(guest);
	}

//	public Guest loginByPhoneNumber(String phoneNumber) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	public void deleteGuest(int id) {
		guestRepository.deleteById(id);
	}

	public Guest loginByPhoneNumberAndUserName(String phoneNumber, String userName) {
		// TODO Auto-generated method stub
		return guestRepository.findByPhoneNumberAndName(phoneNumber, userName);
	}
}