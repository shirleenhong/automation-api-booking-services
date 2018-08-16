package com.cwt.bpg.cbt.exchange.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.cwt.bpg.cbt.exchange.order.exception.ExchangeOrderNoContentException;
import com.cwt.bpg.cbt.exchange.order.model.BasePassthrough;
import com.cwt.bpg.cbt.exchange.order.model.Passthrough;

@Service
public class PassthroughService {

	@Autowired
	private PassThroughRepository passthroughRepo;
	
	private static final String CWT = "CWT";
	private static final String AIRLINE = "Airline";
	
	public String getPassThroughType(BasePassthrough basePassthrough) throws ExchangeOrderNoContentException {
		
		String passthroughType = null;
		StringBuilder sb = new StringBuilder();
		List<Passthrough> passthroughList = getPassthrough(basePassthrough);
	        
		checkEmptyList(passthroughList);
		
		if(passthroughList.size() == 1) {
			return passthroughList.get(0).getPassthroughType();
		}
		
		for(Passthrough passthrough : passthroughList) {
			if(passthrough.getPassthroughType().equals(CWT)) {
				sb.append(CWT);
				passthroughType = CWT;
			}
			else if(passthrough.getPassthroughType().equals(AIRLINE)) {
				sb.append(AIRLINE);
				passthroughType = AIRLINE;
			}
		}
		
		if(sb.toString().contains(CWT) && sb.toString().contains(AIRLINE)) {
			passthroughType = CWT;
		}
		
		return passthroughType;
	}

	private void checkEmptyList(List<Passthrough> passthroughList)
			throws ExchangeOrderNoContentException {
		
		if (ObjectUtils.isEmpty(passthroughList)) {
			throw new ExchangeOrderNoContentException("Passthrough data not found");
		}
	}
	
	private List<Passthrough> getPassthrough(BasePassthrough param) {
		return passthroughRepo.getPassthrough(param);
	}
}
