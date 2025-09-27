package br.com.aweb.to_do_list.controller;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http .HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.aweb.to_do_list.model.MaintenanceRequest;
import br.com.aweb.to_do_list.repository.MaintenanceRequestRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/manutencao")
public class MaintenanceRequestController {

    @Autowired
    private MaintenanceRequestRepository maintenanceRequestRepository;

    @GetMapping
    public ModelAndView list() {
        return new ModelAndView("maintenance/list", Map.of("requests",
                maintenanceRequestRepository.findAll(Sort.by("requestTimestamp"))));
    }

    @GetMapping("/criar")
    public ModelAndView create() {
        return new ModelAndView("maintenance/form", Map.of("request", new MaintenanceRequest()));
    }

    @PostMapping("/criar")
    public String create(@Valid MaintenanceRequest request, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "maintenance/form";
        }
        maintenanceRequestRepository.save(request);
        redirectAttributes.addFlashAttribute("successMessage", "Solicitação de manutenção criada com sucesso!");
        return "redirect:/manutencao";
    }

    // MÉTODO GET PARA DELETE FOI REMOVIDO DAQUI

    @PostMapping("/excluir/{id}")
    public String delete(MaintenanceRequest request, RedirectAttributes redirectAttributes) {
        maintenanceRequestRepository.delete(request);
        redirectAttributes.addFlashAttribute("successMessage", "Solicitação de manutenção excluída com sucesso!");
        return "redirect:/manutencao";
    }

    @PostMapping("/finalizar/{id}")
    public String finish(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        var optionalRequest = maintenanceRequestRepository.findById(id);
        if (optionalRequest.isPresent() && optionalRequest.get().getFinishedTimestamp() == null) {
            var request = optionalRequest.get();
            request.setFinishedTimestamp(LocalDateTime.now());
            maintenanceRequestRepository.save(request);
            redirectAttributes.addFlashAttribute("successMessage", "Solicitação de manutenção finalizada com sucesso!");
            return "redirect:/manutencao";
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}