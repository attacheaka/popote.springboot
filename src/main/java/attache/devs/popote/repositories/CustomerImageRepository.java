package attache.devs.popote.repositories;

import attache.devs.popote.models.CustomerImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerImageRepository extends JpaRepository<CustomerImage,Long> {
}