package com.example.shun.app;

import com.example.shun.app.model.People;
import com.example.shun.app.repository.PeopleRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/people")
public class PeopleApiController {

    @Autowired
    private PeopleRepository peopleRepository;

    @GetMapping(path="/")
    public @ResponseBody
    List<People> findAllPeople() {
        // This returns a JSON or XML with the users
        return peopleRepository.findAll();
    }

    @GetMapping("/first/{firstName}")
    public List<People> findByFirst(@PathVariable String firstName) {
        List<People> result = peopleRepository.findByFirst(firstName);
        return result;
    }

    @GetMapping("/last/{lastName}")
    public List<People> findByLast(@PathVariable String lastName) {
        return peopleRepository.findByLast(lastName);
    }

    @GetMapping("/{id}")
    public @ResponseBody Optional<People> findById(@PathVariable Long id) {
        return peopleRepository.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public People create(@RequestBody People person) {
        People result = peopleRepository.save(person);
        return result;
    }

    @PutMapping("/{id}")
    public People updatePeople(@RequestBody People people, @PathVariable Long id) {
        if (people.getId() != id) {
            throw new PeopleIdMismatchException();
        }
        peopleRepository.findById(id).orElseThrow(RuntimeException::new);
        return peopleRepository.save(people);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        peopleRepository.findById(id).orElseThrow(PeopleNotFoundException::new);
        peopleRepository.deleteById(id);
    }

}
