package o3bmp.cp.r2.crm.service;

import o3bmp.cp.r2.crm.model.CheckCapacityRequestModel;
import o3bmp.cp.r2.crm.model.ReserveSlotModel;

public interface IReservationService {
	
	int checkCapacity(CheckCapacityRequestModel checkCapacityRequestModel);
	boolean reserveSlot(ReserveSlotModel reserveSlotModel);	
}
