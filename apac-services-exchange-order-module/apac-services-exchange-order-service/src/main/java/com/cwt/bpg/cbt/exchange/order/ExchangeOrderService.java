package com.cwt.bpg.cbt.exchange.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.exchange.order.exception.ExchangeOrderNoContentException;
import com.cwt.bpg.cbt.exchange.order.model.*;

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
	

	public BaseExchangeOrder save(String countryCode, BaseExchangeOrder exchangeOrder)
			throws ExchangeOrderNoContentException {

		String eoNumber = exchangeOrder.getEoNumber();
		exchangeOrder.setCountryCode(countryCode);
		if (eoNumber == null) {
			return eoInsertService.insert(exchangeOrder);
		} else {
			return eoUpdateService.update(exchangeOrder);
		}
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
		return exchangeOrderRepo.updateFinance(exchangeOrder);
	}

	public BaseExchangeOrder get(String countryCode, String eoNumber) {
		return exchangeOrderRepo.getExchangeOrder(countryCode, eoNumber);
	}

	public List<RoomType> getAll() {
		return roomTypeRepository.getAll();
	}

	public RoomType save(RoomType roomType) {
		return roomTypeRepository.put(roomType);
	}

	public String delete(String code) {
		return roomTypeRepository.remove(code);
	}

	public List<VmpdReasonCode> getAllVmpdReasonCodes() {
		return reasonCodeRepository.getAll();
	}

	public VmpdReasonCode saveVmpdReasonCode(VmpdReasonCode reasonCode) {
		return reasonCodeRepository.put(reasonCode);
	}

	public String deleteVmpdReasonCode(String code) {
		return reasonCodeRepository.remove(code);
	}
	
	public List<CarVendor> getAllCarVendors() {
		return carVendorRepository.getAll();
	}

	public CarVendor saveCarVendor(CarVendor carVendor) {
		return carVendorRepository.put(carVendor);
	}

	public String deleteCarVendor(String code) {
		return carVendorRepository.remove(code);
	}
}
