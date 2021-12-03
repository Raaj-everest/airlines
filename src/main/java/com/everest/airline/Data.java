package com.everest.airline;

import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class Data {
    public static List<Flight> flights = List.of(
            new Flight(1001, "Hyderabad", "Bangalore",LocalDate.of(2021, Month.DECEMBER, 6)),
            new Flight(1002, "Bangalore", "Hyderabad",LocalDate.of(2021, Month.DECEMBER, 7)),
            new Flight(1003, "Goa", "Bangalore",LocalDate.of(2021, Month.DECEMBER, 8)),
            new Flight(1004, "Bangalore", "Goa",LocalDate.of(2021, Month.DECEMBER, 9)),
            new Flight(1005, "Bangalore", "Hyderabad",LocalDate.of(2021, Month.DECEMBER, 10)),
            new Flight(1006, "Hyderabad", "Bangalore",LocalDate.of(2021, Month.DECEMBER, 11)));
}
