package com.techlab.patio;

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
@RequestMapping("/patio")
@RequiredArgsConstructor
public class PatioController extends BaseController {

    private final PatioService patioService;

    @GetMapping
    public String index(Model model, Authentication authentication) {
        model.addAttribute("patios", patioService.findAll());
        addPrincipal(model, authentication);
        return "patio/index";
    }

    @GetMapping("/form")
    public String form(@RequestParam(value = "id", required = false) Long id,
            Model model,
            Authentication authentication) {
        PatioDTO dto = (id != null) ? patioService.findById(id) : new PatioDTO();
        model.addAttribute("patio", dto);
        addPrincipal(model, authentication);
        return "patio/form";
    }

    @PostMapping("/form")
    public String save(@Valid @ModelAttribute("patio") PatioDTO dto,
            BindingResult result,
            RedirectAttributes redirect,
            Model model,
            Authentication authentication) {

        if (result.hasErrors()) {
            model.addAttribute("patio", dto);
            addPrincipal(model, authentication);
            return "patio/form";
        }

        try {
            patioService.save(dto);
        } catch (RuntimeException e) {
            result.rejectValue("nome", "nome.duplicado", e.getMessage());
            model.addAttribute("patio", dto);
            addPrincipal(model, authentication);
            return "patio/form";
        }

        redirect.addFlashAttribute("message", "Pátio salvo com sucesso!");
        return "redirect:/patio";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id,
            Model model,
            Authentication authentication) {
        model.addAttribute("patio", patioService.findById(id));
        addPrincipal(model, authentication);
        return "patio/form";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id,
            RedirectAttributes redirect) {
        patioService.delete(id);
        redirect.addFlashAttribute("message", "Pátio removido com sucesso!");
        return "redirect:/patio";
    }
}
