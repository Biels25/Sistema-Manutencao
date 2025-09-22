package br.com.aweb.to_do_list.controller;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http .HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import br.com.aweb.to_do_list.model.MaintenanceRequest;
import br.com.aweb.to_do_list.repository.MaintenanceRequestRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/maintenance")
public class MaintenanceRequestController {

    @Autowired
    private MaintenanceRequestRepository maintenanceRequestRepository;

    @ModelAttribute("urgencyOptions")
    public MaintenanceRequest.Urgency[] getUrgencyOptions() {
        return MaintenanceRequest.Urgency.values();
    }

    @ModelAttribute("maintenanceTypeOptions")
    public MaintenanceRequest.MaintenanceType[] getMaintenanceTypeOptions() {
        return MaintenanceRequest.MaintenanceType.values();
    }

    @GetMapping
    public ModelAndView list() {
        return new ModelAndView("maintenance/list", Map.of("requests",
                maintenanceRequestRepository.findAll(Sort.by("requestTimestamp"))));
    }

    @GetMapping("/create")
    public ModelAndView create() {
        return new ModelAndView("maintenance/form", Map.of("request", new MaintenanceRequest()));
    }

    @PostMapping("/create")
    public String create(@Valid MaintenanceRequest request, BindingResult result) {
        if (result.hasErrors()) {
            return "maintenance/form";
        }
        maintenanceRequestRepository.save(request);
        return "redirect:/maintenance";
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable Long id) {
        var request = maintenanceRequestRepository.findById(id);
        if (request.isPresent()) {
            return new ModelAndView("maintenance/delete", Map.of("request", request.get()));
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/delete/{id}")
    public String delete(MaintenanceRequest request) {
        maintenanceRequestRepository.delete(request);
        return "redirect:/maintenance";
    }

    @PostMapping("/finish/{id}")
    public String finish(@PathVariable Long id) {
        var optionalRequest = maintenanceRequestRepository.findById(id);
        if (optionalRequest.isPresent() && optionalRequest.get().getFinishedTimestamp() == null) {
            var request = optionalRequest.get();
            request.setFinishedTimestamp(LocalDateTime.now());
            maintenanceRequestRepository.save(request);
            return "redirect:/maintenance";
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}