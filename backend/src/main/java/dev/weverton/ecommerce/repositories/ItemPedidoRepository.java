package dev.weverton.ecommerce.repositories;

import dev.weverton.ecommerce.domain.Categoria;
import dev.weverton.ecommerce.domain.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
}
