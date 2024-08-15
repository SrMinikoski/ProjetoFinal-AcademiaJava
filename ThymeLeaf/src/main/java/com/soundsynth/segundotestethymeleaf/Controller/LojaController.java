package com.soundsynth.segundotestethymeleaf.Controller;

import com.soundsynth.segundotestethymeleaf.Model.Produto;
import com.soundsynth.segundotestethymeleaf.Config.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class LojaController {

//Sem API

    @GetMapping("/produto")
        public String produto(){
            return "produto";
        }
    @GetMapping("/vitrine")
    public String vitrine(){
        return "vitrine";
    }
    @GetMapping("/carrinho")
    public String carrinho(){
        return "carrinho";
    }
//Com API
private final RestTemplate restTemplate;

    public final String API_URL = "http://localhost:8080";

    @Autowired
    public LojaController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    @GetMapping("/produtos/listar")
    public String listar(Model model) {
        String url = API_URL + "/produtos/listar";
        Produto[] produtos = restTemplate.getForObject(url, Produto[].class);
        model.addAttribute("produtos", produtos);
        return "vitrine";
    }



    @GetMapping({"/index","/"})
    public String home(Model model) {
        String url = API_URL + "/produtos/listar";
        Produto[] produtos = restTemplate.getForObject(url, Produto[].class);
        model.addAttribute("produtos", produtos);
        return "index";
    }





}
