package com.techlab.iot;

import com.techlab.helper.BaseController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/iot")
@RequiredArgsConstructor
public class IotController extends BaseController {

    private final IotService iotService;

    @GetMapping
    public String index(Model model, Authentication authentication) {
        model.addAttribute("iots", iotService.findAll());
        addPrincipal(model, authentication);
        return "iot/index";
    }

    @GetMapping("/form")
    public String form(@RequestParam(value = "id", required = false) Long id,
            Model model,
            Authentication authentication) {
        Iot iot = (id != null) ? iotService.findById(id) : new Iot();
        // padrão de formulário: default ativo = true
        if (iot.getAtivo() == null)
            iot.setAtivo(Boolean.TRUE);

        model.addAttribute("iot", iot);
        addPrincipal(model, authentication);
        return "iot/form";
    }

    @PostMapping("/form")
    public String save(@Valid @ModelAttribute("iot") Iot iot,
            BindingResult result,
            RedirectAttributes redirect,
            Model model,
            Authentication authentication) {
        if (result.hasErrors()) {
            model.addAttribute("iot", iot);
            addPrincipal(model, authentication);
            return "iot/form";
        }

        iotService.save(iot);
        redirect.addFlashAttribute("message", "Dispositivo IoT salvo com sucesso!");
        return "redirect:/iot";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id,
            Model model,
            Authentication authentication) {
        model.addAttribute("iot", iotService.findById(id));
        addPrincipal(model, authentication);
        return "iot/form";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id,
            RedirectAttributes redirect) {
        iotService.delete(id);
        redirect.addFlashAttribute("message", "Dispositivo IoT removido com sucesso!");
        return "redirect:/iot";
    }
}
