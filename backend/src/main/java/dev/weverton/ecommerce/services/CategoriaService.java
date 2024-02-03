package dev.weverton.ecommerce.services;

import dev.weverton.ecommerce.domain.Categoria;
import dev.weverton.ecommerce.dto.CategoriaDTO;
import dev.weverton.ecommerce.exceptions.DataIntegrityException;
import dev.weverton.ecommerce.repositories.CategoriaRepository;
import dev.weverton.ecommerce.exceptions.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    private CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<CategoriaDTO> findAll(){
        List<Categoria> listaCategorias = categoriaRepository.findAll();
        List<CategoriaDTO> categorias = listaCategorias.stream().map( obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
        return categorias;
    }

    public Categoria find(Long id){
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        return categoria.orElseThrow(
                () -> new EntityNotFoundException("Categoria(" + id +") não encontrada"));
   }

   public Categoria store(Categoria categoriaObj){
        categoriaObj.setId(null);
        return categoriaRepository.save(categoriaObj);
   }

   public Categoria update(Categoria categoriaObj){
        Categoria categoria = find(categoriaObj.getId());
        categoria.setNome(categoriaObj.getNome());
        return categoriaRepository.save(categoriaObj);
   }

   public void delete(Long id){
        find(id);
        try{
            categoriaRepository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Não é possível deletar categorias que possui produtos.");
        }
   }

    public Page<Categoria> search(Integer page, Integer rows, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, rows, Direction.valueOf(direction), orderBy);
        return categoriaRepository.findAll(pageRequest);
    }
}
