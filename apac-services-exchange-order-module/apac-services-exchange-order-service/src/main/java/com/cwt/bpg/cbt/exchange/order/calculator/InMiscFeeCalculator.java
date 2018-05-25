package com.cwt.bpg.cbt.exchange.order.calculator;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.cwt.bpg.cbt.calculator.CommonCalculator;
import com.cwt.bpg.cbt.calculator.config.ScaleConfig;
import com.cwt.bpg.cbt.exchange.order.model.Bank;
import com.cwt.bpg.cbt.exchange.order.model.Client;
import com.cwt.bpg.cbt.exchange.order.model.CreditCardVendor;
import com.cwt.bpg.cbt.exchange.order.model.InMiscFeesInput;
import com.cwt.bpg.cbt.exchange.order.model.MiscFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.Product;
import com.cwt.bpg.cbt.exchange.order.model.ProductMerchantFee;

public class InMiscFeeCalculator extends CommonCalculator {

	@Autowired
	private ScaleConfig scaleConfig;
	private static final int BTC_FOP_MODE = 3;

	public MiscFeesBreakdown calculate(InMiscFeesInput input, Client client) {

		MiscFeesBreakdown result = new MiscFeesBreakdown();

		BigDecimal commission = null;
		BigDecimal discount = null;
		Double mfPercent = 0D;

		if (input == null || client == null) {
			return result;
		}

		if (input.getFopMode() != BTC_FOP_MODE) {

			ProductMerchantFee product = getProduct(client, input.getProduct());
			if (product != null && product.isSubjectToMf()) {
				mfPercent = 0D;
			}
			else { 
				mfPercent = calculateMfPercent(input, client);
			}
		}

		int scale = scaleConfig.getScale(input.getCountryCode());

		if (input.isCommissionByPercent()) {
			commission = round(calculatePercentage(input.getCostAmount(), input.getCommissionPercent()),
					scale);
		}

		if (input.isDiscountByPercent()) {
			discount = round(
					calculatePercentage(safeValue(input.getCostAmount()).add(safeValue(commission)),
							input.getDiscountPercent()),
					scale);
		}

		BigDecimal grossSell = safeValue(
				input.getCostAmount()).add(safeValue(commission)).subtract(safeValue(discount));
		
		BigDecimal tax = BigDecimal.ZERO;
		BigDecimal gstAmount = BigDecimal.ZERO;
		
		if(input.getProduct() != null) {			
		
			tax = round(calculatePercentage(grossSell, Double.valueOf(input.getProduct().getGst())),
					scale);
	
			gstAmount = round(safeValue(tax)
					.add(safeValue(calculatePercentage(grossSell,
							Double.valueOf(safeValue(input.getProduct().getOt1())))))
					.add(calculatePercentage(grossSell,
							Double.valueOf(safeValue(input.getProduct().getOt2())))), scale);
		}
		
		BigDecimal merchantFeeAmount = round(
				calculatePercentage(safeValue(grossSell).add(tax), mfPercent),
				scale);

		BigDecimal totalSellAmount = round(safeValue(grossSell).add(tax).add(safeValue(merchantFeeAmount)),
				scale);

		result.setGstAmount(gstAmount);
		result.setMerchantFee(merchantFeeAmount);
		result.setSellingPriceInDi(totalSellAmount);
		result.setCommission(commission);

		return result;
	}

	private Double calculateMfPercent(InMiscFeesInput input, Client client) {

		Double mfPercent = client.getMerchantFee();
		
		Bank bank = bank(client, input.getFopNumber(), client.isApplyMfBank());
		if(bank != null && !StringUtils.isEmpty(bank.getCcNumberPrefix())) {
			mfPercent = bank.getPercentage();
		}
		else if(!StringUtils.isEmpty(input.getFopType())) {
			CreditCardVendor vendor = creditCard(client, input.getAcctType(), client.isApplyMfCc());
			mfPercent = vendor != null ? vendor.getPercentage() : 0D;
		}
		
		return mfPercent;
	}

	private ProductMerchantFee getProduct(Client client, Product product) {
		if(client.getMfProducts() != null) {
			Optional<ProductMerchantFee> result = client.getMfProducts().stream()
					.filter(item -> item.getProductCode().equals(product.getProductCode())).findFirst();

			if (result.isPresent()) {
				return result.get();
			}
		}
		return null;
	}

	private CreditCardVendor creditCard(Client client, String acctType, boolean isStandard) {

		if(client.getMfCcs() != null) {
			
			Optional<CreditCardVendor> vendor = client.getMfCcs().stream()
					.filter(item -> item.getVendorName().equals(acctType) && isStandard).findFirst();

			if (vendor.isPresent()) {
				return vendor.get();
			}	
		}
		
		return null;
	}

	private Bank bank(Client client, String fopNumber, boolean isStandard) {

		if(client.getMfBanks() != null) {
			
			Optional<Bank> bank = client.getMfBanks().stream()
					.filter(item -> fopNumber.startsWith(item.getCcNumberPrefix()) 
							&& isStandard).findFirst();

			if (bank.isPresent()) {
				return bank.get();
			}
	
		}
		
		return null;
	}
}
