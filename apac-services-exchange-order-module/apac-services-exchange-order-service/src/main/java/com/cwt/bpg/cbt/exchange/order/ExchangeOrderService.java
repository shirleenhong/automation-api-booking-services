package com.cwt.bpg.cbt.exchange.order;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

	@CacheEvict(cacheNames = "room-types", allEntries = true)
	public RoomType save(RoomType roomType) {
		return roomTypeRepository.put(roomType);
	}

    @CacheEvict(cacheNames = "room-types", allEntries = true)
	public String delete(String code) {
		return roomTypeRepository.remove(code);
	}

	@Cacheable(cacheNames = "reason-codes")
	public List<VmpdReasonCode> getAllVmpdReasonCodes() {
		return reasonCodeRepository.getAll();
	}

	@CacheEvict(cacheNames = "reason-codes", allEntries = true)
	public VmpdReasonCode saveVmpdReasonCode(VmpdReasonCode reasonCode) {
		return reasonCodeRepository.put(reasonCode);
	}

    @CacheEvict(cacheNames = "reason-codes", allEntries = true)
    public String deleteVmpdReasonCode(String code) {
		return reasonCodeRepository.remove(code);
	}

	@Cacheable(cacheNames = "car-vendors")
	public List<CarVendor> getAllCarVendors() {
		return carVendorRepository.getAll();
	}

	@CacheEvict(cacheNames = "car-vendors", allEntries = true)
	public CarVendor saveCarVendor(CarVendor carVendor) {
		return carVendorRepository.put(carVendor);
	}

    @CacheEvict(cacheNames = "car-vendors", allEntries = true)
	public String deleteCarVendor(String code) {
		return carVendorRepository.remove(code);
	}
}
