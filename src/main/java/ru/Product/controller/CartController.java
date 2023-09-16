package ru.Product.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.Product.model.Product;
import ru.Product.model.Cart;

import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@Controller
public class CartController {

    private final Cart productCart;

    @GetMapping("/")
    public ModelAndView get(Model model) {
        Set<Product> products = productCart.getProduct();

        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("products", products);
        modelAndView.addObject("product", new Product());

        return modelAndView;
    }

    @PostMapping("/")
    public String add(Product product) {
        productCart.add(product);
        return "redirect:/";
    }
}
