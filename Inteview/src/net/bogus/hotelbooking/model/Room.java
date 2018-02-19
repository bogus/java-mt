package net.bogus.hotelbooking.model;

import java.util.List;

public class Room {

	long id;
	Facility facility;
	List<RoomFeature<?>> features;
	int numberOfGuests;
	RoomType roomType;
	int count;
	
}
