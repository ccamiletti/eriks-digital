package nl.eriksdigital.repository;

import nl.eriksdigital.entity.OrderEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<OrderEntity, Long> {

    Optional<OrderEntity> findById(Long id);
}
