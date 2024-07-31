package com.PracticaLLVG.PracticaLLVG.Controladores;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.PracticaLLVG.PracticaLLVG.Modelos.ProductoLLVG;
import com.PracticaLLVG.PracticaLLVG.Servicios.Interfaces.IProductoServicioLLVG;

@Controller
@RequestMapping("/productos")
public class ProductoLLVGController {
    @Autowired
    private IProductoServicioLLVG productoService;

    @GetMapping("/index")
    public String index(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1) - 1; // si no está seteado se asigna 0
        int pageSize = size.orElse(5); // tamaño de la página, se asigna 5
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<ProductoLLVG> productos = productoService.buscarTodosPaginados(pageable);
        model.addAttribute("productos", productos);

        int totalPages = productos.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "producto/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("producto", new ProductoLLVG());
        return "producto/create";
    }

    @PostMapping("/save")
    public String create(@ModelAttribute("producto") ProductoLLVG producto, BindingResult result, Model model, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            model.addAttribute("producto", producto);
            attributes.addFlashAttribute("error", "No se pudo guardar debido a un error.");
            return "producto/create";
        }

        productoService.crearOEditar(producto);
        attributes.addFlashAttribute("msg", "Producto creado correctamente");
        return "redirect:/productos/index";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model) {
        ProductoLLVG producto = productoService.buscarPorId(id).orElse(null);
        model.addAttribute("producto", producto);
        return "producto/details";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        ProductoLLVG producto = productoService.buscarPorId(id).orElse(null);
        model.addAttribute("producto", producto);
        return "producto/edit";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer id, Model model) {
        ProductoLLVG producto = productoService.buscarPorId(id).orElse(null);
        model.addAttribute("producto", producto);
        return "producto/delete";
    }

    @PostMapping("/delete")
    public String delete(ProductoLLVG producto, RedirectAttributes attributes) {
        productoService.eliminarPorId(producto.getId());
        attributes.addFlashAttribute("msg", "Producto eliminado correctamente");
        return "redirect:/productos";
    }
}
