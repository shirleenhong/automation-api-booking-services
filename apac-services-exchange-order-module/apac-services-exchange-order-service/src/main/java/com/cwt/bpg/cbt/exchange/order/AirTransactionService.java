package com.cwt.bpg.cbt.exchange.order;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.cwt.bpg.cbt.exchange.order.exception.AirTransactionNoContentException;
import com.cwt.bpg.cbt.exchange.order.model.AirTransaction;
import com.cwt.bpg.cbt.exchange.order.model.AirTransactionInput;
import com.cwt.bpg.cbt.exchange.order.model.AirTransactionOutput;

@Service
public class AirTransactionService {

	@Autowired
	private AirTransactionRepository airTransactionRepo;
	
	private static final String CWT = "CWT";
	private static final String AIRLINE = "Airline";

	public AirTransactionOutput getAirTransaction(AirTransactionInput input)
			throws AirTransactionNoContentException {

		List<AirTransaction> airTransactionList = getAirTransactionList(input);
		checkEmptyList(airTransactionList);

		Optional<AirTransaction> passthroughCWT = airTransactionList.stream()
				.filter(p -> p.getPassthroughType().equals(CWT)).findFirst();

		return passthroughCWT.isPresent() ? createPassthroughOutput(CWT)
				: createPassthroughOutput(AIRLINE);
	}

	private AirTransactionOutput createPassthroughOutput(String type) {
		AirTransactionOutput output = new AirTransactionOutput();
		output.setPassthroughType(type);

		return output;
	}

	private void checkEmptyList(List<AirTransaction> airTransactionList)
			throws AirTransactionNoContentException {

		if (ObjectUtils.isEmpty(airTransactionList)) {
			throw new AirTransactionNoContentException("AirTransaction data not found.");
		}
	}

	public List<AirTransaction> getAirTransactionList(AirTransactionInput param) {
		return airTransactionRepo.getAirTransactions(param);
	}
	
	public AirTransaction getAirTransactionById(String id) {
		return airTransactionRepo.getAirTransactionById(id);
	}

	public AirTransaction save(AirTransaction airTrans) {
		String airTransId = airTransactionRepo.save(airTrans);
		return airTransactionRepo.getAirTransactionById(airTransId);
	}
}
