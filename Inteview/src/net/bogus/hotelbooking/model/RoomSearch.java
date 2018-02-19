package net.bogus.hotelbooking.model;

import java.util.Date;
import java.util.List;

public class RoomSearch {

	Customer customer;
	List<RoomFeature<?>> roomFeatures;
	List<FacilityFeature<?>> facilityFeatures;
	Duration duration;
	String location;
	int radius;
	Date created;
	
}
