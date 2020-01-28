package com.cwt.bpg.cbt.exchange.order.calculator;

import com.cwt.bpg.cbt.exchange.order.model.MerchantFee;

import static org.springframework.util.StringUtils.isEmpty;

public class MerchantFeeCalculatorUtils {

    protected static Double getMerchantFeeForVendorCode(MerchantFee merchantFee, String vendorCode)
    {
        Double merchantFeePercent = merchantFee.getMerchantFeePercent();

        if (!isEmpty(vendorCode)) {

            if (merchantFee.getMfCcs() != null
                    && !merchantFee.getMfCcs().isEmpty()) {

                merchantFeePercent = merchantFee.getMfCcs().stream()
                        .filter(creditCardVendor -> creditCardVendor.getVendorCode().equalsIgnoreCase(vendorCode))
                        .findFirst()
                        .map(creditCardVendor -> creditCardVendor.getPercentage())
                        .orElse(merchantFeePercent);
            }
        }

        return merchantFeePercent;
    }
}
