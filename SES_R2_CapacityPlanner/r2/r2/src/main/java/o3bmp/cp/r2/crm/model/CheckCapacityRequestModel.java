package o3bmp.cp.r2.crm.model;

import lombok.Data;

@Data
public class CheckCapacityRequestModel {	
	String fromInstance;	
	String toInstance;
	String application;
	CustomerRequestModel customer;
}
