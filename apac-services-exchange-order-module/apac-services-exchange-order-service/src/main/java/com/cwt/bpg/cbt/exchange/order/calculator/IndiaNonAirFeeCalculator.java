package com.cwt.bpg.cbt.exchange.order.calculator;

import java.math.BigDecimal;
import java.util.Optional;

import com.cwt.bpg.cbt.calculator.model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.cwt.bpg.cbt.calculator.CommonCalculator;
import com.cwt.bpg.cbt.calculator.config.ScaleConfig;
import com.cwt.bpg.cbt.exchange.order.model.*;

@Component
public class InNonAirFeeCalculator extends CommonCalculator {

	@Autowired
	private ScaleConfig scaleConfig;
	private static final int BILL_TO_COMPANY = 3;

	public NonAirFeesBreakdown calculate(InNonAirFeesInput input, 
			Client client, 
			Client defaultClient) {

		NonAirFeesBreakdown result = new NonAirFeesBreakdown();

		if (input == null || client == null) {
			return result;
		}

		BigDecimal commission = safeValue(input.getCommission());
		BigDecimal discount = safeValue(input.getDiscount());
		Double mfPercent = 0D;

		if (input.getFopMode() != BILL_TO_COMPANY) {

			ProductMerchantFee product = getProduct(client, input.getProduct());
			if (product != null && product.isSubjectToMf()) {
				mfPercent = 0D;
			}
			else {
				mfPercent = calculateMfPercent(input, client, defaultClient);
			}
		}

		int scale = scaleConfig.getScale(Country.INDIA.getCode());

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

		BigDecimal grossSell = safeValue(input.getCostAmount()).add(safeValue(commission))
				.subtract(safeValue(discount));

		BigDecimal tax = round(
				calculatePercentage(grossSell, safeValue(input.getProduct().getGst())),
				scale);

		BigDecimal gstAmount = round(safeValue(tax)
				.add(safeValue(calculatePercentage(grossSell, safeValue(input.getProduct().getOt1()))))
				.add(calculatePercentage(grossSell, safeValue(input.getProduct().getOt2()))), scale);

		BigDecimal merchantFeeAmount = round(calculatePercentage(safeValue(grossSell).add(tax), mfPercent),
				scale);

		BigDecimal totalSellAmount = round(
				safeValue(grossSell).add(gstAmount).add(safeValue(merchantFeeAmount)),
				scale);

		result.setGstAmount(gstAmount);
		result.setMerchantFee(merchantFeeAmount);
		result.setTotalSellingPrice(totalSellAmount);
		result.setGrossSellingPrice(grossSell);

		return result;
	}

	private Double calculateMfPercent(InNonAirFeesInput input, 
			Client client, 
			Client defaultClient) {

		Double mfPercent = client.getMerchantFee();
	
		if(isSubjectToMF(client.isStandardMfProduct() 
				? defaultClient : client, 
				input.getProduct().getProductCode())) {
			mfPercent = 0D;
		}
		else {
			Bank bank = getBank(client, input.getFopNumber(), client.isApplyMfBank());
			if (bank != null && !StringUtils.isEmpty(bank.getCcNumberPrefix())) {
				mfPercent = bank.getPercentage();
			}
			else if (!StringUtils.isEmpty(input.getFopType())) {
				CreditCardVendor vendor = getCreditCard(client, input.getAcctType(), client.isApplyMfCc());
				mfPercent = vendor != null ? vendor.getPercentage() : 0D;
			}	
		}
		
		return mfPercent;
	}

	private boolean isSubjectToMF(Client client, String productCode) {

		if (client.getMfProducts() != null) {
			Optional<ProductMerchantFee> pmf = client.getMfProducts().stream()
					.filter(p -> p.getProductCode().equals(productCode)).findFirst();

			if (pmf.isPresent()) {
				return pmf.get().isSubjectToMf();
			}

		}

		return false;
	}

	private ProductMerchantFee getProduct(Client client, Product product) {
		if (client.getMfProducts() != null) {
			Optional<ProductMerchantFee> result = client.getMfProducts().stream()
					.filter(item -> item.getProductCode().equals(product.getProductCode())).findFirst();

			if (result.isPresent()) {
				return result.get();
			}
		}
		return null;
	}

	private CreditCardVendor getCreditCard(Client client, String acctType, boolean isStandard) {

		if (client.getMfCcs() != null) {

			Optional<CreditCardVendor> vendor = client.getMfCcs().stream()
					.filter(item -> item.getVendorName().equals(acctType) && item.isStandard() == isStandard).findFirst();

			if (vendor.isPresent()) {
				return vendor.get();
			}
		}

		return null;
	}

	private Bank getBank(Client client, String fopNumber, boolean isStandard) {

		if (client.getMfBanks() != null) {

			Optional<Bank> bank = client.getMfBanks().stream()
					.filter(item -> fopNumber.startsWith(item.getCcNumberPrefix()) && item.isStandard() == isStandard).findFirst();

			if (bank.isPresent()) {
				return bank.get();
			}

		}

		return null;
	}
}
