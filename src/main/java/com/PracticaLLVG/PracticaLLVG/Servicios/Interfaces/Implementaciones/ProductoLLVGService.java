package com.PracticaLLVG.PracticaLLVG.Servicios.Interfaces.Implementaciones;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.PracticaLLVG.PracticaLLVG.Modelos.ProductoLLVG;
import com.PracticaLLVG.PracticaLLVG.Repositorios.IProductoRepositoryLLVG;
import com.PracticaLLVG.PracticaLLVG.Servicios.Interfaces.IProductoServicioLLVG;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductoLLVGService  implements IProductoServicioLLVG{
    @Autowired
    private IProductoRepositoryLLVG productoRepository;

    @Override
    public Page<ProductoLLVG> buscarTodosPaginados(Pageable pageable) {
        return productoRepository.findAll(pageable);
    }

    @Override
    public List<ProductoLLVG> obtenerTodos() {
        return productoRepository.findAll();
    }

    @Override
    public Optional<ProductoLLVG> buscarPorId(Integer id) {
        return productoRepository.findById(id);
    }

    @Override
    public ProductoLLVG crearOEditar(ProductoLLVG producto) {
        return productoRepository.save(producto);
    }

    @Override
    public void eliminarPorId(Integer id) {
        productoRepository.deleteById(id);
    }

}
