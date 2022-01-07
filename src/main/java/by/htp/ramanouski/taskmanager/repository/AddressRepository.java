package by.htp.ramanouski.taskmanager.repository;

import by.htp.ramanouski.taskmanager.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
    AddressEntity findByAddressId(String addressId);
}
