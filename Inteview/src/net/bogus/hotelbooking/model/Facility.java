package net.bogus.hotelbooking.model;

import java.util.List;

public class Facility {

	long id;
	String title;
	String address;
	String phoneNumber;
	List<Duration> offPeriods;
	List<FacilityFeature<?>> features;
	
}
