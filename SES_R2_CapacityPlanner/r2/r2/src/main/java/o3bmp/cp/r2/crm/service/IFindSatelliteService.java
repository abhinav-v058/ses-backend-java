package o3bmp.cp.r2.crm.service;

import java.util.List;

import o3bmp.cp.r2.entities.Satellite;

public interface IFindSatelliteService {

	List<Integer> getSatellite(String application);
	
	Satellite getSatellite(int id);
}
