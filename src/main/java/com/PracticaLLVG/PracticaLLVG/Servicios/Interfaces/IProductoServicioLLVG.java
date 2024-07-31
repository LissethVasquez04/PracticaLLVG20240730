package com.PracticaLLVG.PracticaLLVG.Servicios.Interfaces;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.PracticaLLVG.PracticaLLVG.Modelos.ProductoLLVG;
import java.util.List;
import java.util.Optional;

public interface IProductoServicioLLVG {
    Page<ProductoLLVG> buscarTodosPaginados(Pageable pageable);

    List<ProductoLLVG> obtenerTodos();

    Optional<ProductoLLVG> buscarPorId(Integer id);

    ProductoLLVG crearOEditar(ProductoLLVG producto);

    void eliminarPorId(Integer id);

}
