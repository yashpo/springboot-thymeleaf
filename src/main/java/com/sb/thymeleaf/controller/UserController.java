package com.sb.thymeleaf.controller;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sb.thymeleaf.domain.User;
import com.sb.thymeleaf.service.UserService;

@Controller
@RequestMapping("app")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);	
	@Autowired
	private UserService userService;
	@GetMapping("create-user")
	public ModelAndView createUserView() {
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("user-creation");
	    mav.addObject("user", new User());
	    mav.addObject("allProfiles", getProfiles());
	    return mav;
    }
	@PostMapping("create-user")
	public ModelAndView createUser(@Valid User user, BindingResult result) {
	    ModelAndView mav = new ModelAndView();
        if(result.hasErrors()) {
        	logger.info("Validation errors while submitting form.");
        	mav.setViewName("user-creation");
    	    mav.addObject("user", user);
    	    mav.addObject("allProfiles", getProfiles());
    	    return mav;
        }		
		userService.addUser(user);
	    mav.addObject("allUsers", userService.getAllUserArticles());
	    mav.setViewName("user-info");
    	logger.info("Form submitted successfully.");	    
	    return mav;
    }	
	private List<String> getProfiles() {
		List<String> list = new ArrayList<>();
		list.add("Developer");
		list.add("Manager");
		list.add("Director");
		return list;
	}
} 