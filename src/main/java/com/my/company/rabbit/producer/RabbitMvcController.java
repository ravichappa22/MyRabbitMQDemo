package com.my.company.rabbit.producer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

	@Controller
	public class RabbitMvcController {
		
	@RequestMapping("/helloWorld")
    public String helloWorld(Model model) {
        model.addAttribute("message", "Hello World!");
        return "helloWorld";
    }
	
	@RequestMapping(path = "/new", method = RequestMethod.GET)
	public String getMyName(){
		return "Hello My name is Ravi";
	}
}
