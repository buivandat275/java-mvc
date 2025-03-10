package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.service.OrderService;

@Controller
public class OrderController {

    public final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping("/admin/order")
    public String getDashboard(Model model,@RequestParam(value = "page",defaultValue = "1") int page){
        Pageable pageable = PageRequest.of(page - 1, 4);
        Page<Order> ors = this.orderService.fetchAllOrder(pageable);
        List<Order> orders = ors.getContent();
        model.addAttribute("orders", orders);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", ors.getTotalPages());
        return "admin/order/show";
    }
    @GetMapping("/admin/order/delete/{id}")
    public String getDeleteOrderById(Model model,@PathVariable long id){
        model.addAttribute("id", id);
        model.addAttribute("newOrder", new Order());
        return "admin/order/delete";
    }
    @PostMapping("/admin/order/delete")
    public String postDeleteOrderById(@ModelAttribute("newOrder") Order order){
        this.orderService.deleteOrderById(order.getId());
        return "redirect:/admin/order";
    }

    @GetMapping("/admin/order/{id}")
    public String getViewOrderDetail(Model model,@PathVariable long id){
       Order order = this.orderService.fetchOrderById(id).get();
       model.addAttribute("id", id);
       model.addAttribute("order", order);
       model.addAttribute("orderDetails", order.getOrderDetails());
        return "admin/order/detail";
    }

    @GetMapping("/admin/order/update/{id}")
    public String getUpdateOrder(Model model,@PathVariable long id){
        Optional<Order> currentOrder = this.orderService.fetchOrderById(id);
        model.addAttribute("newOrder", currentOrder.get());
        return "admin/order/update";
    }
    @PostMapping("/admin/order/update")
    public String postUpdateOrder(@ModelAttribute("newOrder") Order order){
        this.orderService.updateOrder(order);
        return "redirect:/admin/order";
    }



    
}
