package com.example.clientconnectivity.controller;

import com.example.clientconnectivity.model.Client;
import com.example.clientconnectivity.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RestController
public class AuthenticationController {

    @Autowired
    ClientService clientService;

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login"); // resources/template/login.html
        return modelAndView;
    }

    @GetMapping("/register")
    public ModelAndView register() {
        ModelAndView modelAndView = new ModelAndView();
        Client client = new Client();
        modelAndView.addObject("client", client);
        modelAndView.setViewName("register"); // resources/template/register.html
        return modelAndView;
    }

    @GetMapping("/home")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home"); // resources/template/home.html
        return modelAndView;
    }

    @GetMapping("/admin")
    public ModelAndView admin() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin"); // resources/template/admin.html
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView registerClient(@Valid Client client, BindingResult bindingResult, ModelMap modelMap){
       ModelAndView modelAndView = new ModelAndView();

       if(bindingResult.hasErrors()){
           modelAndView.addObject("successMessage", "Check errors");
           modelMap.addAttribute("bindingResult", bindingResult);
       } else if(clientService.isClientAlreadyPresent(client)){
           modelAndView.addObject("successMessage", "client already exists!");
       }
       // we will save the user if, no binding errors
       else {
           clientService.saveClient(client);
           modelAndView.addObject("successMessage", "Client is registered successfully!");
       }
       modelAndView.addObject("client", new Client());
       modelAndView.setViewName("register");
       return modelAndView;
    }
}
