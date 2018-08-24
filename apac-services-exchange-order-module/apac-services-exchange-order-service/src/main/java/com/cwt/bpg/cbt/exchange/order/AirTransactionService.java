package com.cwt.bpg.cbt.exchange.order;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.cwt.bpg.cbt.exchange.order.exception.AirTransactionNoContentException;
import com.cwt.bpg.cbt.exchange.order.model.AirTransaction;
import com.cwt.bpg.cbt.exchange.order.model.AirTransactionInput;
import com.cwt.bpg.cbt.exchange.order.model.AirTransactionOutput;
import com.cwt.bpg.cbt.exchange.order.model.PassthroughType;

@Service
public class AirTransactionService {

	@Resource
	private AirTransactionService proxy;
	
	@Autowired
	private AirTransactionRepository airTransactionRepo;
	
	public AirTransactionOutput getAirTransaction(AirTransactionInput input)
			throws AirTransactionNoContentException {

		List<AirTransaction> airTransactionList = proxy.getAirTransactionList(input);
		checkEmptyList(airTransactionList);

		Optional<AirTransaction> passthroughCWT = airTransactionList.stream()
				.filter(p -> p.getPassthroughType().equals(PassthroughType.CWT)).findFirst();

		return passthroughCWT.isPresent() ? createPassthroughOutput(PassthroughType.CWT)
				: createPassthroughOutput(PassthroughType.AIRLINE);
	}

	private AirTransactionOutput createPassthroughOutput(PassthroughType type) {
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

	@Cacheable(cacheNames = "air-transactions", key = "#input.airlineCode.toString() + #input.clientAccountNumber.toString()")
	public List<AirTransaction> getAirTransactionList(AirTransactionInput input) {
		return airTransactionRepo.getAirTransactions(input);
	}

	public AirTransaction save(AirTransaction airTrans) {
		return airTransactionRepo.put(airTrans);
	}
	
	public String delete(String id) {
		return airTransactionRepo.remove(new ObjectId(id));
	}
}
