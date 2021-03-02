package o3bmp.cp.r2.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Entity
@Table(name="reservations", schema = "scheduling")
public class Reservations {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "r_id")
	private Integer reservationID;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "startinstance")
	private Date startInstance;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "endinstance")
	private Date endInstance;
	
	@Column(name = "status")
	private Character status; 
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cust_id", insertable=true, updatable=true, nullable=false)
	private Customer customer;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sat_id", insertable=true, updatable=true, nullable=false)
	private Satellite satellite;

}
