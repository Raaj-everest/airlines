package com.everest.airline;

import com.everest.airline.Search.SearchHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.FileNotFoundException;
import java.time.LocalDate;

@Controller
public class SearchController {

    @RequestMapping(value = "/")
    public String home() {
        return "home";
    }

    @RequestMapping(value = "/search")
    public String search(String from, String to, String ticket, String departureDate, Model model) throws FileNotFoundException {
        model.addAttribute("flights", SearchHelper.sourceToDestination(from, to, LocalDate.parse(departureDate), Integer.valueOf(ticket)));
        return "search";
    }
}
