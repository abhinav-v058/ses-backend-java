package o3bmp.cp.r2.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import o3bmp.cp.r2.crm.model.CapacityResponseModel;
import o3bmp.cp.r2.crm.model.CheckCapacityRequestModel;
import o3bmp.cp.r2.crm.service.IReservationService;

@RestController
@RequestMapping("/capacity")
public class RequestController {

	@Autowired
	IReservationService reservationService;	
	
	
	@PostMapping(path="/", produces="application/json")
	public ResponseEntity<CapacityResponseModel> requestBandwidth(@RequestBody CheckCapacityRequestModel checkCapacityModel){
		CapacityResponseModel response = new CapacityResponseModel();
		try {			
			int reservationId = reservationService.checkCapacity(checkCapacityModel);			
			if(reservationId == -1) {
				response.setMessage("This request was processed but the capacity couldn't be determined. "
						+ "Please contact the administrator.");			
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}
			
			response.setReservationID(reservationId);
			response.setMessage("Please use this reservation ID for finalizing the available slot.");
			return ResponseEntity.status(HttpStatus.OK).body(response);
			
			
		}
		catch (Exception e) {	
			response.setMessage("It looks like you caught us at a bad time. "
					+ "This is a server Error. Please contact the administrator.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}		
	}
}
