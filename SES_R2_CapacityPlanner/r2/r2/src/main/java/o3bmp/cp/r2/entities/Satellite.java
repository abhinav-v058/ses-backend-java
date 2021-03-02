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
@Table(name="satellite", schema = "scheduling")
public class Satellite {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "sat_id")
	private int satelliteID;
	
	@Column(name="sname")
	private String Name;
}
