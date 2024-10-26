package lu.sgbt.maks.controller;

import lu.sgbt.maks.mode.Order;
import org.jeasy.random.EasyRandom;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController {

    private List<Order> orders = new ArrayList<>();
    private EasyRandom generator = new EasyRandom();

    // Initialize with some sample data
    public OrderController() {
        orders.add(new Order(1L, "NEW", BigDecimal.valueOf(100.0), "First order"));
        orders.add(new Order(2L, "PROCESSING", BigDecimal.valueOf(200.0), "Second order"));
        orders.add(new Order(3L, "COMPLETED", BigDecimal.valueOf(150.0), "Third order"));
        // Add more orders as needed
    }

    @GetMapping("/")
    public String viewBlotter(Model model) {
        model.addAttribute("orders", orders);
        return "blotter";
    }

    @GetMapping("/filter")
    public String viewFilterPage(Model model) {
        model.addAttribute("orders", orders);
        return "filter";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // API endpoint to get orders (could add filtering parameters)
    @GetMapping("/blotter")
    public String getOrders(Model model) {
        model.addAttribute("orders", orders);
        return "blotter";  // Full page template
    }

    // API endpoint to refresh orders (for simplicity, returning list with random object)
    @GetMapping("/blotter-refresh")
    public String refreshOrders(Model model) {
        orders.add(generator.nextObject(Order.class));
        model.addAttribute("orders", orders);
        return "fragments/order-table";  // Partial HTML fragment for HTMX
    }

    @GetMapping("/search")
    public String searchOrders(@RequestParam(value = "state", required = false) String state,
                               @RequestParam(value = "minAmount", required = false) Double minAmount,
                               @RequestParam(value = "maxAmount", required = false) Double maxAmount,
                               Model model) {
        List<Order> result = this.orders.stream().filter(order -> order.getState().equals(state)).toList();
        model.addAttribute("orders", result);
        return "fragments/order-table";  // Return partial table
    }
}
