package com.example.springboot_security403;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    CarRepository carRepository;
    @Autowired
    CloudinaryConfig cloudc;


    @RequestMapping("/secure")
    public String secure(Principal principal, Model model){
        String username = principal.getName();
        model.addAttribute("user", userRepository.findByUsername(username));
        return "secure";
    }

    @RequestMapping("/")
    public String listCars(Model model) {
        model.addAttribute("cars", carRepository.findAll());
        return "list";
    }
    @GetMapping("/add")
    public String newCar(Model model){
        model.addAttribute("car", new Car());
        return "carForm";
    }
    @PostMapping("/add")
    public String processCar(@ModelAttribute Car car,
                             @RequestParam("file")MultipartFile file){
        if(file.isEmpty()){
            return "redirect:/add";
        }
        try {
            Map uploadResult = cloudc.upload(file.getBytes(),
                    ObjectUtils.asMap("resourcetype","auto"));
            car.setHeadshot(uploadResult.get("url").toString());
            carRepository.save(car);
        } catch (IOException e){
            return "redirect:/add";
        }
        return "redirect:/";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }
    @RequestMapping("/admin")
    public String admin(){
        return "admin";
    }
    @RequestMapping("/logout")
    public String logout(){
        return "redirect:/login?logout=true";
    }

}
