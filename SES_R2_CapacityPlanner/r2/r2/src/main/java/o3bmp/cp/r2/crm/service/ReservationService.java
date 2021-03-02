package o3bmp.cp.r2.crm.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import o3bmp.cp.r2.crm.model.CheckCapacityRequestModel;
import o3bmp.cp.r2.crm.model.ReserveSlotModel;
import o3bmp.cp.r2.entities.Customer;
import o3bmp.cp.r2.entities.Reservations;
import o3bmp.cp.r2.entities.Satellite;
import o3bmp.cp.r2.entities.SatelliteByApplication;
import o3bmp.cp.r2.repositories.ReservationsRepository;
import o3bmp.cp.r2.repositories.SatelliteByApplicationRepository;
import o3bmp.cp.r2.repositories.CustomerRepository;;

@Service
public class ReservationService implements IReservationService {
	
	@Autowired
	ReservationsRepository reservationsRepository;
	
	@Autowired
	IFindSatelliteService findSatelliteService;
	
	@Autowired
	SatelliteByApplicationRepository sbyA;
	
	@Autowired
	CustomerRepository CustomerRepository;
	
	Logger logger = LoggerFactory.getLogger(ReservationService.class);

	@Override
	public int checkCapacity(CheckCapacityRequestModel checkCapacityRequestModel) {
		try 
		{
			logger.debug("In 'checkCapacity' function and received the following request:");
			if(checkCapacityRequestModel!=null)
				logger.debug(checkCapacityRequestModel.toString());
			else
				logger.debug("Request is null.");
			
			String application = checkCapacityRequestModel.getApplication();
			if(application.isEmpty())
				return -1;
			
			Date startD = processDates(checkCapacityRequestModel.getFromInstance());
			Date endD = processDates(checkCapacityRequestModel.getToInstance());
			
			application = application.toLowerCase();
			List<Integer> satellites = findSatelliteService.getSatellite(application);
			List<Reservations> reservations = reservationsRepository.checkSatelliteCapacity(new Timestamp(startD.getTime()) , new Timestamp(endD.getTime()), satellites);
			if(reservations.size()<satellites.size() && satellites.size()>0) {
				// randomly assigning the first available satellite
				int assignedSatelliteId = satellites.get(0);
				HashSet<Integer> reservedSatId = new HashSet<Integer>();
				if(reservations.size()>0) {
					for(int i =0;i<reservations.size();i++) {
						reservedSatId.add(reservations.get(i).getSatellite().getSatelliteID());
					}
					
					for(int i =0;i<satellites.size();i++) {
						if(!reservedSatId.contains(satellites.get(i))) {
							assignedSatelliteId = satellites.get(i);
							break;
						}
					}
				}
				
				 Reservations newReservation = new Reservations();
				 
				 newReservation.setEndInstance(endD);
				 newReservation.setStartInstance(startD);
				 newReservation.setStatus('H');
				 
				 Satellite sat = findSatelliteService.getSatellite(assignedSatelliteId);
				 if(sat==null)
					 throw new Exception("Satellite cannot be assigned.");
				 
				 Customer c = CustomerRepository.getCustomer(checkCapacityRequestModel.getCustomer().getName().toLowerCase());			 
				 if(c == null) {
					 logger.debug("Creating a new customer.");
					 c = new Customer();
					 c.setName(checkCapacityRequestModel.getCustomer().getName().toLowerCase());
					 logger.debug(c.toString());
					 CustomerRepository.save(c);
				 }
				 
				 newReservation.setCustomer(c);
				 newReservation.setSatellite(sat);
				 newReservation = reservationsRepository.save(newReservation);
				 return newReservation.getReservationID();
				 
			}

		}
		catch(Exception e) {
			logger.error(e.getMessage(),e);
			return -1;
		}
		
		return -1;		
	}

	@Override
	public boolean reserveSlot(ReserveSlotModel reserveSlotModel) {
		try {
			logger.debug("In 'reserveSlot' function and received a request.");
			if(reserveSlotModel!=null)
				logger.debug(reserveSlotModel.toString());
			else
				logger.debug("Request is null.");
			
			Optional<Reservations> r = reservationsRepository.findById(reserveSlotModel.getReservationId());
			if(r.isPresent()) {
				r.get().setStatus('F');
				reservationsRepository.save(r.get());				
			}
			
			logger.debug("Request was successful.");
			return true;
		}
		catch(Exception e) {
			logger.error(e.getMessage(),e);
			return false;
		}
		
	}

	private Date processDates(String date) {
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 Date d = new Date();
		try {
			d = formatter.parse(date);
			
			if(d!=null)
			logger.debug(d.toString());
			else
				logger.debug("Date is null");
			
		} catch (ParseException e) {			
			logger.error(e.getMessage(),e);
		}
		
		 return d;
	}
}
