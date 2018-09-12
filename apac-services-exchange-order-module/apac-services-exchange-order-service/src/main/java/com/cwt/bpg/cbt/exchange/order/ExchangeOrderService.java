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

	public BaseExchangeOrder saveExchangeOrder(String countryCode, BaseExchangeOrder exchangeOrder)
			throws ExchangeOrderNoContentException {

		String eoNumber = exchangeOrder.getEoNumber();
		exchangeOrder.setCountryCode(countryCode);
		if (eoNumber == null) {
			return eoInsertService.insert(exchangeOrder);
		} else {
			return eoUpdateService.update(exchangeOrder);
		}
	}

	public ExchangeOrder getExchangeOrder(String eoNumber) {
		return exchangeOrderRepo.getExchangeOrder(eoNumber);
	}

	List<? extends BaseExchangeOrder> getExchangeOrderByRecordLocator(String countryCode, String recordLocator) {
		return exchangeOrderRepo.getExchangeOrderByRecordLocator(countryCode, recordLocator);
	}

	List<ExchangeOrder> search(final ExchangeOrderSearchParam param) {
		return exchangeOrderRepo.search(param);
	}

    boolean updateStatus(String eoNumber, EoStatus status) {
        return exchangeOrderRepo.updateStatus(eoNumber, status);
    }

	boolean update(ExchangeOrder param) {
		return exchangeOrderRepo.updateFinance(param);
	}

	public BaseExchangeOrder getExchangeOrder(String countryCode, String eoNumber) {
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

	public List<VmpdReasonCode> getAllVMPDReasonCodes() {
		return reasonCodeRepository.getAll();
	}

	public VmpdReasonCode saveVMPDReasonCode(VmpdReasonCode reasonCode) {
		return reasonCodeRepository.put(reasonCode);
	}

	public String deleteVmpdReasonCode(String code) {
		return reasonCodeRepository.remove(code);
	}
}
