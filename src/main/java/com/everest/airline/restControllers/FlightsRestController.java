package com.everest.airline.restControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class FlightsRestController {

    @Autowired
    private U u;

//    @GetMapping("/flights")
//    public List<Flight> getAllFlights() throws IOException {
//        return jdbcTemplate.query(" select number,source,destination,departuredate," +
//                "a.occupiedSeats,a.Capacity,a.basefare," +
//                "b.occupiedSeats,b.Capacity,b.basefare," +
//                "c.occupiedSeats,c.Capacity,c.basefare from flight" +
//                " left join cabin a on flight.firstclass=a.id " +
//                "left join cabin b on flight.businessclass=b.id" +
//                " left join cabin c  on flight.economicclass=c.id;", new FlightRowMapper());
//    }

    //    @GetMapping("/flights/{number}")
//    public List<Flight> getFlights(@PathVariable long number) throws IOException {
//        Map<String, Object> map = new HashMap();
//        map.put("number",number);
//        System.out.println(number);
//        return jdbcTemplate.query(" select number,source,destination,departuredate," +
//                "a.occupiedSeats,a.Capacity,a.basefare," +
//                "b.occupiedSeats,b.Capacity,b.basefare," +
//                "c.occupiedSeats,c.Capacity,c.basefare from flight" +
//                " left join cabin a on flight.firstclass=a.id " +
//                "left join cabin b on flight.businessclass=b.id" +
//                " left join cabin c  on flight.economicclass=c.id where number=:number;", map,new FlightRowMapper());
//    }
////
//    @PostMapping("/flights")
//    public long create(String source, String destination, String departureDate, Integer economyClassCapacity, Integer firstClassCapacity, Integer businessClassCapacity, Integer occupiedEconomicSeats, Integer occupiedFirstClassSeats, Integer occupiedBusinessClassSeats, Integer economyClassBaseFare, Double firstClassBaseFare, Double businessClassBaseFare) throws IOException {
//        Cabin firstClass = new Cabin(firstClassCapacity, occupiedFirstClassSeats, firstClassBaseFare, CabinType.FIRST);
//        Cabin businessClass = new Cabin(businessClassCapacity, occupiedBusinessClassSeats, businessClassBaseFare, CabinType.BUSINESS);
//        Cabin economyClass = new Cabin(economyClassCapacity, occupiedEconomicSeats, economyClassBaseFare, CabinType.ECONOMIC);
//        long numberId = dataWriter.generateNUmber();
//        Flight flight = new Flight(numberId, source, destination, LocalDate.parse(departureDate), firstClass, businessClass, economyClass);
//        File file = dataWriter.create(numberId);
//        dataWriter.write(flight, file);
//        return numberId;
//    }
//
//
//    @DeleteMapping("/flights/{number}")
//    public String delete(@PathVariable long number) throws IOException {
//        File file = dataReader.getFile(String.valueOf(number));
//        if (file.delete()) {
//            return ("Flight : " + number + " deleted successfullyp");
//        }
//        throw new FlightNotFoundException("Unable to locate the flight to delete");
//    }
//
    @PutMapping("/flights")
    public long create( String source, String destination, String departureDate, Integer economyClassCapacity, Integer firstClassCapacity, Integer businessClassCapacity, Integer occupiedEconomicSeats, Integer occupiedFirstClassSeats, Integer occupiedBusinessClassSeats, Integer economyClassBaseFare, Double firstClassBaseFare, Double businessClassBaseFare) {
        return u.write( source, destination, departureDate, economyClassCapacity, firstClassCapacity, businessClassCapacity, occupiedEconomicSeats, occupiedFirstClassSeats, occupiedBusinessClassSeats, economyClassBaseFare, firstClassBaseFare, businessClassBaseFare);
    }

    @PostMapping("/flights")
    public String update( String number,String source, String destination, String departureDate, Integer economyClassCapacity, Integer firstClassCapacity, Integer businessClassCapacity, Integer occupiedEconomicSeats, Integer occupiedFirstClassSeats, Integer occupiedBusinessClassSeats, Integer economyClassBaseFare, Double firstClassBaseFare, Double businessClassBaseFare) {
        return u.update( number,source, destination, departureDate, economyClassCapacity, firstClassCapacity, businessClassCapacity, occupiedEconomicSeats, occupiedFirstClassSeats, occupiedBusinessClassSeats, economyClassBaseFare, firstClassBaseFare, businessClassBaseFare);
    }
//    private SqlParameterSource flighrMapper(Flight flight){
//        return new MapSqlParameterSource()
//                .addValue("number",flight.getNumber())
//                .addValue("source",flight.getSource())
//                .addValue("destination",flight.getDestination())
//                .addValue("departureDate",flight.getDepartureDate());
//    }
//    private SqlParameterSource cabinMapper(Cabin cabin){
//        return new MapSqlParameterSource()
//                .addValue("capacity",cabin.getCapacity())
//                .addValue("occupiedSeats",cabin.getOccupiedSeats())
//                .addValue("availableSeats",(cabin.getCapacity()-cabin.getOccupiedSeats()))
//                .addValue("baseFare",cabin.getBaseFare())
//                .addValue("type",cabin.getCabinType());
//    }
}