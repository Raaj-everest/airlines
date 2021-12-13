package com.everest.airline;

import com.everest.airline.Search.SearchHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static com.everest.airline.Data.readDataFromFile;
import static com.everest.airline.Data.writeToFile;

@Controller
public class SearchController {
    public static List<Flight> searchedFlights;
    private String from;
    private String to;
    private String departureDate;
    private String ticket;
    private String classType;


    @RequestMapping(value = "/")
    public String home() {
        return "home";
    }

    @RequestMapping(value = "/search")
    public String search(String from, String to, String departureDate, String ticket,String classType, Model model) throws FileNotFoundException {
        if (from != null) {
            this.from = from;
            this.to = to;
            this.departureDate = departureDate;
            this.ticket = ticket;
            this.classType=classType;
        }
        System.out.println(classType);
        searchedFlights = SearchHelper.sourceToDestination(this.from, this.to, LocalDate.parse(this.departureDate), Integer.valueOf(this.ticket));
        if(searchedFlights.size()==0){
            return "noFlights";
        }
        model.addAttribute("flights", searchedFlights);
        return "search";
    }

    @RequestMapping(value = "/{number}")
    public String book(@PathVariable("number") String number, Model model) throws IOException {
        List<Flight> flights = readDataFromFile();
        for (Flight flight : flights) {
            if (flight.getNumber() == Integer.parseInt(number)) {
                flight.setOccupiedSeats();
            }
        }
        writeToFile(flights);
        return "redirect:search";
    }
}
