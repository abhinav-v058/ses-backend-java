package o3bmp.cp.r2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import o3bmp.cp.r2.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{
	
	@Query( value = "select * from scheduling.customer where cname = :name", nativeQuery = true)
	Customer getCustomer(@Param("name") String name);

}
