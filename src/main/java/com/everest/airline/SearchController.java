package com.everest.airline;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
public class SearchController {

    @RequestMapping(value = "/")
    public String home() {
        return "home";
    }

    @RequestMapping(value = "/search")
    public String search(String from, String to, Model model) {
        for(int i=0;i<Data.flights.size();i++){
            if((from.equals(Data.flights.get(i).getSource()))&&(to.equals(Data.flights.get(i).getDestination()))){
                model.addAttribute("flights", Data.flights.get(i));
            }
        }
//        model.addAttribute("flights", Data.flights);
        return "search";
    }
}
