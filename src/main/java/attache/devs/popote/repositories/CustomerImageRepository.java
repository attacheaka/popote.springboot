package attache.devs.popote.repositories;

import attache.devs.popote.models.CustomerImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerImageRepository extends JpaRepository<CustomerImage,Long> {
    CustomerImage findFirstByCustomerPhone(String customerPhone);

}