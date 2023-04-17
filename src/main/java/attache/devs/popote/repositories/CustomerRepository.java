package attache.devs.popote.repositories;

import attache.devs.popote.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    @Query("SELECT c, ci FROM Customer c LEFT JOIN CustomerImage ci ON c.phoneNumber = ci.customerPhone")
    List<Object[]> findAllCustomersWithImages();

    @Query("SELECT c, ci FROM Customer c LEFT JOIN CustomerImage ci ON c.phoneNumber = ci.customerPhone WHERE c.id = :customerId")
    List<Object[]> findCustomerWithImagesById(@Param("customerId") Long customerId);
}