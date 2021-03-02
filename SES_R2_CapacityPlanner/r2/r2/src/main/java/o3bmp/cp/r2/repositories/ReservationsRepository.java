package o3bmp.cp.r2.repositories;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import o3bmp.cp.r2.entities.Reservations;

public interface ReservationsRepository extends JpaRepository<Reservations, Integer> {
	
	@Query(value ="select * from scheduling.reservations where "
			+ "(startinstance <= cast(:startDate AS timestamp) and endinstance >= cast(:startDate AS timestamp))"
			+ " or (startinstance <= cast(:endDate AS timestamp) and endinstance >= cast(:endDate AS timestamp))"
			+ " or (startinstance >= cast(:startDate AS timestamp) and endinstance <= cast(:endDate AS timestamp))"
			+ " and sat_id in (:satellites) and status = 'F'", nativeQuery = true)
	List<Reservations> checkSatelliteCapacity(@Param("startDate") Timestamp startDate, @Param("endDate")  Timestamp endDate, @Param("satellites")  List<Integer> satellites);

}
