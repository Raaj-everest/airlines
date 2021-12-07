package com.everest.airline;

import com.everest.airline.Search.SearchHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.stream.Collectors;

import static com.everest.airline.Data.readDataFromFile;
import static com.everest.airline.Data.writeToFile;

@Controller
public class SearchController {

    @RequestMapping(value = "/")
    public String home() {
        return "home";
    }

    @RequestMapping(value = "/search")
    public String search(String from, String to, String ticket, String departureDate, Model model) throws FileNotFoundException {
        model.addAttribute("flights", SearchHelper.sourceToDestination(from, to, LocalDate.parse(departureDate), Integer.valueOf(ticket)));
//        writeToFile(from, to, LocalDate.parse(departureDate), Integer.valueOf(ticket));
        return "search";
    }
    @RequestMapping(value = "/{number}")
    public String book(@PathVariable("number") long number,Model model) throws IOException {
        System.out.println(number);
        model.addAttribute("flights", readDataFromFile().stream().filter(f-> (f.getNumber()==number)).collect(Collectors.toList()));
//        writeToFile(from, to, LocalDate.parse(departureDate), Integer.valueOf(ticket));
        return "book";
    }
}
