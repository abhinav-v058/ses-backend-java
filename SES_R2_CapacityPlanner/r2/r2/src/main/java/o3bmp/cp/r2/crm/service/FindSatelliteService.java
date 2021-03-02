package o3bmp.cp.r2.crm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import o3bmp.cp.r2.entities.Satellite;
import o3bmp.cp.r2.repositories.SatelliteByApplicationRepository;
import o3bmp.cp.r2.repositories.SatelliteRepository;

@Service
public class FindSatelliteService implements IFindSatelliteService {	

	@Autowired
	SatelliteByApplicationRepository sByARepository;
	
	@Autowired
	SatelliteRepository satelliteRepository;

	@Override
	public List<Integer> getSatellite(String application) {		
		return this.sByARepository.getByApplication(application);
	}

	@Override
	public Satellite getSatellite(int id) {	
		Optional<Satellite> sat = satelliteRepository.findById(id);
		if(sat.isPresent())
			return sat.get();
		
		return null;
	}

}
