package net.bogus.hotelbooking.model;

import java.util.Date;

public class Reservation {

	Customer customer;
	Room room;
	Duration duration;
	int numberOfGuests;
	double price;
	double cancellationFee;
	boolean isCheckoutComplete;
	Date created;
	
}
