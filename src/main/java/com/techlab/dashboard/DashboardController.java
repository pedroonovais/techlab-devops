package com.techlab.dashboard;

import com.techlab.helper.BaseController;
import com.techlab.iot.Iot;
import com.techlab.iot.IotService;
import com.techlab.moto.MotoService;
import com.techlab.patio.PatioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class DashboardController extends BaseController {

    private final PatioService patioService;
    private final IotService iotService;
    private final MotoService motoService;

    @GetMapping("/index")
    public String index(Model model, Authentication authentication) {
        var patios = patioService.findAll();
        var iots = iotService.findAll(); // ENTIDADES
        var motos = motoService.findAll();

        long totalPatios = patios.size();
        long totalIots = iots.size();
        long totalMotos = motos.size();

        long iotsAtivos = iots.stream().filter(i -> Boolean.TRUE.equals(i.getAtivo())).count();

        // média de bateria
        var batStats = iots.stream()
                .filter(i -> i.getBateria() != null)
                .mapToInt(i -> i.getBateria())
                .summaryStatistics();
        Integer bateriaMedia = batStats.getCount() > 0 ? (int) Math.round(batStats.getAverage()) : null;

        // buckets de bateria: [0–20, 21–40, 41–60, 61–80, 81–100]
        int[] buckets = new int[5];
        iots.stream().map(Iot::getBateria).filter(b -> b != null).forEach(b -> {
            if (b <= 20)
                buckets[0]++;
            else if (b <= 40)
                buckets[1]++;
            else if (b <= 60)
                buckets[2]++;
            else if (b <= 80)
                buckets[3]++;
            else
                buckets[4]++;
        });
        List<Integer> bateriaBuckets = Arrays.asList(buckets[0], buckets[1], buckets[2], buckets[3], buckets[4]);

        model.addAttribute("patios", patios);
        model.addAttribute("iots", iots);
        model.addAttribute("motos", motos);

        model.addAttribute("totalPatios", totalPatios);
        model.addAttribute("totalIots", totalIots);
        model.addAttribute("totalMotos", totalMotos);
        model.addAttribute("iotsAtivos", iotsAtivos);
        model.addAttribute("bateriaMedia", bateriaMedia);

        model.addAttribute("iotInativos", Math.max(0, totalIots - iotsAtivos));
        model.addAttribute("bateriaBuckets", bateriaBuckets);

        addPrincipal(model, authentication);
        return "index";
    }
}
