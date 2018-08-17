package com.cwt.bpg.cbt.exchange.order;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.cwt.bpg.cbt.exchange.order.exception.ExchangeOrderNoContentException;
import com.cwt.bpg.cbt.exchange.order.model.Passthrough;
import com.cwt.bpg.cbt.exchange.order.model.PassthroughInput;
import com.cwt.bpg.cbt.exchange.order.model.PassthroughOutput;

@Service
public class PassthroughService {

	@Autowired
	private PassthroughRepository passthroughRepo;
	
	private static final String CWT = "CWT";
	private static final String AIRLINE = "Airline";
	
	public PassthroughOutput getPassThroughType(PassthroughInput basePassthrough)
			throws ExchangeOrderNoContentException {

		List<Passthrough> passthroughList = getPassthrough(basePassthrough);
		checkEmptyList(passthroughList);

		Optional<Passthrough> passthroughCWT = passthroughList.stream()
				.filter(p -> p.getPassthroughType().equals(CWT)).findFirst();

		return passthroughCWT.isPresent() ? setCwtPassthrough() : setAirlinePassthrough();
	}

	private PassthroughOutput setAirlinePassthrough() {
		PassthroughOutput passthroughAirlineOutput = new PassthroughOutput();
		passthroughAirlineOutput.setPassthroughType(AIRLINE);
		
		return passthroughAirlineOutput;
	}

	private PassthroughOutput setCwtPassthrough() {
		PassthroughOutput passthroughCwtOutput = new PassthroughOutput();
		passthroughCwtOutput.setPassthroughType(CWT);
		
		return passthroughCwtOutput;
	}
	private void checkEmptyList(List<Passthrough> passthroughList)
			throws ExchangeOrderNoContentException {
		
		if (ObjectUtils.isEmpty(passthroughList)) {
			throw new ExchangeOrderNoContentException("Passthrough data not found.");
		}
	}
	
	private List<Passthrough> getPassthrough(PassthroughInput param) {
		return passthroughRepo.getPassthrough(param);
	}
}
