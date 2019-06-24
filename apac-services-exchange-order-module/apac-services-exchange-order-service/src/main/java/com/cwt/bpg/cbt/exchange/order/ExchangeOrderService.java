package com.cwt.bpg.cbt.exchange.order;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.exchange.order.exception.ExchangeOrderNoContentException;
import com.cwt.bpg.cbt.exchange.order.model.BaseExchangeOrder;
import com.cwt.bpg.cbt.exchange.order.model.CarVendor;
import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrder;
import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrderSearchParam;
import com.cwt.bpg.cbt.exchange.order.model.RoomType;
import com.cwt.bpg.cbt.exchange.order.model.VmpdReasonCode;
import com.cwt.bpg.cbt.exchange.order.model.india.AirMiscInfo;

@Service
public class ExchangeOrderService {

	@Autowired
	private ExchangeOrderRepository exchangeOrderRepo;

	@Autowired
	private RoomTypeRepository roomTypeRepository;

	@Autowired
	private ExchangeOrderInsertService eoInsertService;

	@Autowired
	private ExchangeOrderUpdateService eoUpdateService;

	@Autowired
	private VmpdReasonCodesRepository reasonCodeRepository;
	
	@Autowired
	private CarVendorRepository carVendorRepository;
	
	@Autowired
	private AirMiscInfoRepository airMiscInfoRepository;
	

	public List<BaseExchangeOrder> save(String countryCode, List<? extends BaseExchangeOrder> exchangeOrders)
			throws ExchangeOrderNoContentException {

		List<BaseExchangeOrder> savedExchangeOrders = new ArrayList<>();
		for (BaseExchangeOrder exchangeOrder : exchangeOrders) {
			exchangeOrder.setCountryCode(countryCode.toUpperCase());
			
			if (exchangeOrder.getEoNumber() == null) {
				BaseExchangeOrder insertResult = eoInsertService.insert(exchangeOrder);
				if (insertResult != null) {
					savedExchangeOrders.add(insertResult);
				}
			} else {
				BaseExchangeOrder updateResult = eoUpdateService.update(exchangeOrder);
				if (updateResult != null) {
					savedExchangeOrders.add(updateResult);
				}
			}
		}
		return savedExchangeOrders;
	}

	public ExchangeOrder get(String eoNumber) {
		return exchangeOrderRepo.getExchangeOrder(eoNumber);
	}

	List<? extends BaseExchangeOrder> getExchangeOrderByRecordLocator(String countryCode, String recordLocator) {
		return exchangeOrderRepo.getExchangeOrderByRecordLocator(countryCode, recordLocator);
	}

	List<ExchangeOrder> search(final ExchangeOrderSearchParam param) {
		return exchangeOrderRepo.search(param);
	}

	boolean update(ExchangeOrder exchangeOrder) {
        if (StringUtils.isEmpty(exchangeOrder.getEoNumber())) {
            throw new IllegalArgumentException("Exchange order number is empty.");
        }
		return exchangeOrderRepo.updateFinance(exchangeOrder);
	}

	public BaseExchangeOrder get(String countryCode, String eoNumber) {
		return exchangeOrderRepo.getExchangeOrder(countryCode, eoNumber);
	}

	@Cacheable(cacheNames = "room-types")
	public List<RoomType> getAll() {
		return roomTypeRepository.getAll();
	}

	public RoomType save(RoomType roomType) {
		return roomTypeRepository.put(roomType);
	}

	public String delete(String code) {
		return roomTypeRepository.remove(code);
	}

	@Cacheable(cacheNames = "reason-codes")
	public List<VmpdReasonCode> getAllVmpdReasonCodes() {
		return reasonCodeRepository.getAll();
	}

	public VmpdReasonCode saveVmpdReasonCode(VmpdReasonCode reasonCode) {
		return reasonCodeRepository.put(reasonCode);
	}

    public String deleteVmpdReasonCode(String code) {
		return reasonCodeRepository.remove(code);
	}

	@Cacheable(cacheNames = "car-vendors")
	public List<CarVendor> getAllCarVendors() {
		return carVendorRepository.getAll();
	}

	public CarVendor saveCarVendor(CarVendor carVendor) {
		return carVendorRepository.put(carVendor);
	}

	public String deleteCarVendor(String code) {
		return carVendorRepository.remove(code);
	}
    
    @Cacheable(cacheNames = "air-misc-info", key = "#clientAccountNumber + #reportingFieldTypeIds")
    public List<AirMiscInfo> getAirMiscInfos(String clientAccountNumber, List<String> reportingFieldTypeIds) {
    	return airMiscInfoRepository.getAirMiscInfos(clientAccountNumber, reportingFieldTypeIds);
    }
    
	public AirMiscInfo saveAirMiscInfo(AirMiscInfo airMiscInfo) {
		return airMiscInfoRepository.put(airMiscInfo);
	}
    
	public String deleteAirMiscInfo(String id) {
		return airMiscInfoRepository.remove(new ObjectId(id));
	}

}
