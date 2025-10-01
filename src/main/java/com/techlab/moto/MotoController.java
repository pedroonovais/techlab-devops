package com.techlab.moto;

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
@RequestMapping("/moto")
@RequiredArgsConstructor
public class MotoController extends BaseController {

    private final MotoService motoService;

    @GetMapping
    public String index(Model model, Authentication authentication) {
        model.addAttribute("motos", motoService.findAll());
        addPrincipal(model, authentication);
        return "moto/index";
    }

    @GetMapping("/form")
    public String form(@RequestParam(value = "id", required = false) Long id,
            Model model,
            Authentication authentication) {
        MotoDTO dto = (id != null) ? motoService.findById(id) : new MotoDTO();
        model.addAttribute("moto", dto);
        addPrincipal(model, authentication);
        return "moto/form";
    }

    @PostMapping("/form")
    public String save(@Valid @ModelAttribute("moto") MotoDTO dto,
            BindingResult result,
            RedirectAttributes redirect,
            Model model,
            Authentication authentication) {

        if (result.hasErrors()) {
            model.addAttribute("moto", dto);
            addPrincipal(model, authentication);
            return "moto/form";
        }

        try {
            motoService.save(dto);
        } catch (RuntimeException e) {
            // Exibe erro de negócio (ex.: placa duplicada)
            result.rejectValue("placa", "placa.duplicada", e.getMessage());
            model.addAttribute("moto", dto);
            addPrincipal(model, authentication);
            return "moto/form";
        }

        redirect.addFlashAttribute("message", "Moto salva com sucesso!");
        return "redirect:/moto";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id,
            Model model,
            Authentication authentication) {
        model.addAttribute("moto", motoService.findById(id));
        addPrincipal(model, authentication);
        return "moto/form";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id,
            RedirectAttributes redirect) {
        motoService.delete(id);
        redirect.addFlashAttribute("message", "Moto removida com sucesso!");
        return "redirect:/moto";
    }
}
