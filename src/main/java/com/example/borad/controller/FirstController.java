package com.example.borad.controller;

import com.example.borad.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {
    @Autowired
    private QuoteService quoteService;

   @GetMapping("/hi")
    public String niceToMeetYou(Model model) {
       model.addAttribute("username","호정");
    return "/greetings";
}

    @GetMapping("/bye")
    public String bye(Model model) {
       model.addAttribute("username","호정");
       return "/goodbye";
    }

    @GetMapping("/random-quote")
    public String randomQuote(Model model) {
      model.addAttribute("quote",quoteService.randomQuote()) ;
      return "/quote";
    }
}
