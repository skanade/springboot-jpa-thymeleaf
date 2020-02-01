package com.example.shun.app;

import com.example.shun.app.model.People;
import com.example.shun.app.repository.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path="/")
public class PeopleController {
    @Value("${spring.application.name}")
    String appName;

    @Autowired
    private PeopleRepository peopleRepository;

    private void setAppName(Model model) {
        model.addAttribute("appName", appName);
    }

    @GetMapping(path="/")
    public String getAllPeopleView(Model model) {
        setAppName(model);
        model.addAttribute("peoples", findAll());
        return "people"; // view
    }

    @GetMapping(path="/people/create")
    public String create(Model model) {
        setAppName(model);
        return "people_create";
    }

    @PostMapping(path="/people/save")
    public String save(@RequestParam("first") String first, @RequestParam("last") String last, Model model) {
        setAppName(model);
        People people = new People(first, last);
        peopleRepository.save(people);
        model.addAttribute("people", people);
        return "people_update";
    }

    @GetMapping(path="/people/update")
    public String updateView(@RequestParam("id") Long id, Model model) {
        setAppName(model);
        People people = findPeopleById(id);
        model.addAttribute("people", people);
        return "people_update";
    }

    private People findPeopleById(Long id) {
        Optional<People> peopleOptional = peopleRepository.findById(id);
        if (!peopleOptional.isPresent()) {
            throw new RuntimeException("Person with id: "+id+" does NOT exist");
        }
        People people = peopleOptional.get();
        return people;
    }

    @PostMapping(path="/people/update")
    public String update(@RequestParam("id") Long id, @RequestParam("first") String first, @RequestParam("last") String last,
                         Model model) {
        setAppName(model);
        People people = findPeopleById(id);
        people.setFirst(first);
        people.setLast(last);

        peopleRepository.save(people);
        model.addAttribute("people", people);
        return "people_update";
    }

    @GetMapping(path="/people/delete")
    public String delete(@RequestParam("id") Long id, Model model) {
        setAppName(model);
        peopleRepository.deleteById(id);
        return getAllPeopleView(model);
    }

    @GetMapping(path="/people/search")
    public String searchView(Model model) {
        setAppName(model);
        return "people_search";
    }

    @RequestMapping(value="/people/search", produces="application/json", consumes="application/json")
    @ResponseBody
    public List<People> search(@RequestBody SearchText searchText) {
        List<People> result = peopleRepository.findByFirstOrLastLike(searchText.getText());
        return result;
    }

    private List<People> findAll() {
        return peopleRepository.findAll();
    }

}
