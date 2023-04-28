package edu.uoc.epcsd.user.controllers;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import edu.uoc.epcsd.user.entities.Alert;
import edu.uoc.epcsd.user.entities.User;
import edu.uoc.epcsd.user.repositories.AlertRepository;
import edu.uoc.epcsd.user.repositories.UserRepository;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AlertRepository alertRepository;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsers() {
        log.trace("getAllUsers");

        return userRepository.findAll();
    }

    // add the code for the missing system operations here

    // alta d’usuari
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user) {
        log.trace("createUser");
        return userRepository.save(user);
    }

    // alta d’alerta
    @PostMapping("/{userId}/alerts")
    @ResponseStatus(HttpStatus.CREATED)
    public Alert createAlert(@PathVariable Long userId, @RequestBody Alert alert) {
        log.trace("createAlert");
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        alert.setUser(user);
        return alertRepository.save(alert);
    }

    // TODO: consulta de les alertes d’un usuari per un interval concret de dates
    @GetMapping("/{userId}/alerts")
    @ResponseStatus(HttpStatus.OK)
    public List<Alert> getUserAlertsByDateRange(@PathVariable Long userId, @RequestParam("from") Date from, @RequestParam("to") Date to) {
        log.trace("getUserAlertsByDateRange");
        return alertRepository.findByUserIdAndDateRange(userId, from, to);
    }
    

    // TODO: consulta de les alertes per producte i data
    @GetMapping("/alerts")
    @ResponseStatus(HttpStatus.OK)
    public List<Alert> getAlertsByProductAndDate(@RequestParam("product") String product, @RequestParam("date") Date date) {
        log.trace("getAlertsByProductAndDate");
        return alertRepository.findByProductAndDate(product, date);
    }

    // TODO: consulta d’usuaris a alertar per un producte i data (operació getUsersToAlert)
    @GetMapping("/alerts/users-to-alert")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getUsersToAlert(@RequestParam("product") String product, @RequestParam("date") Date date) {
        log.trace("getUsersToAlert");
        List<Alert> alerts = alertRepository.findByProductAndDate(product, date);
        return alerts.stream().map(Alert::getUser).collect(Collectors.toList());
    }
    // TODO: consulta del detall d’una alerta
    @GetMapping("/alerts/{alertId}")
    @ResponseStatus(HttpStatus.OK)
    public Alert getAlertDetails(@PathVariable Long alertId) {
        log.trace("getAlertDetails");
        return alertRepository.findById(alertId).orElseThrow(() -> new RuntimeException("Alert not found"));
    }
    
}
