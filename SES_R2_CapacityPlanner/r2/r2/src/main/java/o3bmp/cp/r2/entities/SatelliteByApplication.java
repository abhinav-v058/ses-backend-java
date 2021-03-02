package o3bmp.cp.r2.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="satellitebyapplication", schema = "scheduling")
public class SatelliteByApplication {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer ID;
	
	@Column(name="application")
	private String Application;
	
	@Column(name="sat_id")
	private Integer satelliteID;
}
