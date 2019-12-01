package com.example.shun.app;

import com.example.shun.app.model.People;
import com.example.shun.app.repository.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(path="/")
public class PeopleController {
    @Value("${spring.application.name}")
    String appName;

    @Autowired
    private PeopleRepository peopleRepository;


    @GetMapping(path="/")
    public String getAllPeopleView(Model model) {
        model.addAttribute("appName", appName);
        model.addAttribute("peoples", findAll());
        return "people"; // view
    }

    private List<People> findAll() {
        return peopleRepository.findAll();
    }

}
