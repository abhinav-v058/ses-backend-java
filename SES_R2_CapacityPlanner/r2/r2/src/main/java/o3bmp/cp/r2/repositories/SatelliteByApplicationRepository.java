package o3bmp.cp.r2.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import o3bmp.cp.r2.entities.SatelliteByApplication;

public interface SatelliteByApplicationRepository extends JpaRepository<SatelliteByApplication, Integer> {
	
	@Query(value="select sat_id from scheduling.satellitebyapplication where application = :application", nativeQuery=true)
	List<Integer> getByApplication(@Param("application") String application);	
}
