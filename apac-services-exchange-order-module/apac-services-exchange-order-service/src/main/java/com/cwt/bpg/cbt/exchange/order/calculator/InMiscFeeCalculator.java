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
import com.cwt.bpg.cbt.exchange.order.model.ProductMerchantFee;

public class InMiscFeeCalculator extends CommonCalculator {

	@Autowired
	private ScaleConfig scaleConfig;
	private static final int BTC_FOP_MODE = 3;

	public MiscFeesBreakdown calculate(InMiscFeesInput input, Client client) {

		MiscFeesBreakdown result = new MiscFeesBreakdown();

		BigDecimal commission = null;
		BigDecimal discount = null;
		Double merchantFeePercent = 0D;

		if (input == null) {
			return result;
		}

		if (input.getFopMode() != BTC_FOP_MODE) {
			merchantFeePercent = calculateMfPercent(input, client);
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
	
			gstAmount = safeValue(tax)
					.add(safeValue(round(calculatePercentage(grossSell,
							Double.valueOf(safeValue(input.getProduct().getoT1()))), scale)))
					.add(round(calculatePercentage(grossSell,
							Double.valueOf(safeValue(input.getProduct().getoT2()))), scale));
		}
		
		BigDecimal merchantFeeAmont = round(
				calculatePercentage(safeValue(grossSell).add(tax), merchantFeePercent),
				scale);

		BigDecimal totalSellAmount = round(safeValue(grossSell).add(tax).add(safeValue(merchantFeeAmont)),
				scale);

		result.setGstAmount(gstAmount);
		result.setMerchantFee(merchantFeeAmont);
		result.setSellingPriceInDi(totalSellAmount);
		result.setCommission(commission);

		return result;
	}

	private Double calculateMfPercent(InMiscFeesInput input, Client client) {

		Double mfPercent = client.getMerchantFee();

		ProductMerchantFee product = new ProductMerchantFee();
		if (product.isSubjectToMf()) {
			mfPercent = 0D;
		}
		else if (!client.isApplyMfBank()) {
			Bank bank = bank(client, input.getFopType(), !client.isApplyMfBank());
			mfPercent = bank != null ? bank.getPercentage() : 0D;
		}
		else if (client.isApplyMfBank()) {
			Bank bank = bank(client, input.getFopType(), client.isApplyMfBank());
			mfPercent = bank != null ? bank.getPercentage() : 0D;
		}
		else if (!client.isApplyMfCc() && StringUtils.isEmpty(input.getFopType())) {
			CreditCardVendor vendor = creditCard(client, input.getAcctType(), !client.isApplyMfCc());
			mfPercent = vendor != null ? vendor.getPercentage() : 0D;
		}
		else if (client.isApplyMfCc() && StringUtils.isEmpty(input.getFopType())) {
			CreditCardVendor vendor = creditCard(client, input.getAcctType(), client.isApplyMfCc());
			mfPercent = vendor != null ? vendor.getPercentage() : 0D;
		}

		return mfPercent;
	}

	private CreditCardVendor creditCard(Client client, String acctType, boolean isStandard) {

		if(client.getVendors() != null) {
			Optional<CreditCardVendor> vendor = client.getVendors().stream()
					.filter(item -> item.getVendorName().equals(acctType) && isStandard).findFirst();

			if (vendor.isPresent()) {
				return vendor.get();
			}	
		}
		
		return null;
	}

	private Bank bank(Client client, String fopNumber, boolean isStandard) {

		if(client.getBanks() != null) {
			Optional<Bank> bank = client.getBanks().stream()
					.filter(item -> fopNumber.startsWith(item.getCcNumberPrefix()) && isStandard).findFirst();

			if (bank.isPresent()) {
				return bank.get();
			}
	
		}
		
		return null;
	}
}
