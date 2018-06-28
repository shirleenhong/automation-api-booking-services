package com.cwt.bpg.cbt.exchange.order.calculator;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.cwt.bpg.cbt.calculator.config.ScaleConfig;
import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.exchange.order.model.*;

public class IndiaNonAirFeeCalculatorTest {

	@InjectMocks
	private IndiaNonAirFeeCalculator calculator = new IndiaNonAirFeeCalculator();

	@Mock
	private ScaleConfig scaleConfig;

	private Client client;

	private IndiaNonAirFeesInput input = new IndiaNonAirFeesInput();

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		Mockito.when(scaleConfig.getScale(Mockito.eq(Country.INDIA.getCode()))).thenReturn(2);

		ReflectionTestUtils.setField(calculator, "scaleConfig", scaleConfig);

		input.setCcType("VI");
		input.setCommissionByPercent(true);
		input.setCommissionPercent(2D);
		input.setCostAmount(round(new BigDecimal(1300)));
		input.setCountryCode(Country.INDIA.getCode());
		input.setDiscountByPercent(true);
		input.setDiscountPercent(5D);
		input.setFopMode(4);
		input.setFopNumber("1234");
		input.setFopType(FopTypes.CWT.getCode());

		IndiaNonAirProductInput product= new IndiaNonAirProductInput();
		product.setGstPercent(6D);
		product.setOt1Percent(3D);
		product.setOt2Percent(5D);
		product.setProductCode("01");
		input.setProduct(product);
	}

	@Test
	public void standardBank() {

		input.setClientAccountNumber("clientNumber");

		client = new Client();
		Bank bank = new Bank();
		bank.setCcNumberPrefix("12345");
		bank.setStandard(false);
		bank.setPercentage(2D);
		ProductMerchantFee pf = new ProductMerchantFee();
		pf.setSubjectToMf(false);
		pf.setProductCode("01");
		client.setMfProducts(singletonList(pf));
		client.setMfBanks(singletonList(bank));
		client.setApplyMfBank(false);
		client.setApplyMfCc(false);

		NonAirFeesBreakdown result = calculator.calculate(input, client, client);

		assertThat(result.getGstAmount().doubleValue(), is(equalTo(176.36D)));
		assertThat(result.getMerchantFee().doubleValue(), is(equalTo(0D)));
		assertThat(result.getTotalSellingPrice().doubleValue(), is(equalTo(1436.06D)));
		assertThat(result.getGrossSellingPrice().doubleValue(), is(equalTo(1259.70D)));
		assertThat(result.getCommission(), is(nullValue()));
		assertThat(result.getNettCostGst(), is(nullValue()));
	}

	@Test
	public void standardBank2() {

		input.setClientAccountNumber("clientNumber");

		client = new Client();
		Bank bank = new Bank();
		bank.setCcNumberPrefix("1234");
		bank.setStandard(false);
		bank.setPercentage(2D);
		ProductMerchantFee pf = new ProductMerchantFee();
		pf.setSubjectToMf(true);
		pf.setProductCode("01");
		client.setMfProducts(singletonList(pf));
		client.setMfBanks(singletonList(bank));
		client.setApplyMfBank(false);
		client.setApplyMfCc(false);

		NonAirFeesBreakdown result = calculator.calculate(input, client, client);

		assertThat(result.getGstAmount().doubleValue(), is(equalTo(176.36D)));
		assertThat(result.getMerchantFee().doubleValue(), is(equalTo(0D)));
		assertThat(result.getTotalSellingPrice().doubleValue(), is(equalTo(1436.06D)));
		assertThat(result.getGrossSellingPrice().doubleValue(), is(equalTo(1259.70D)));
		assertThat(result.getCommission(), is(nullValue()));
		assertThat(result.getNettCostGst(), is(nullValue()));
	}

	@Test
	public void nonStandardBank() {

		input.setClientAccountNumber("clientNumber");

		client = new Client();
		Bank bank = new Bank();
		bank.setCcNumberPrefix("1234");
		bank.setStandard(true);
		bank.setPercentage(2D);
		ProductMerchantFee pf = new ProductMerchantFee();
		pf.setSubjectToMf(false);
		pf.setProductCode("01");
		client.setMfProducts(singletonList(pf));
		client.setMfBanks(singletonList(bank));
		client.setApplyMfCc(true);
		client.setApplyMfBank(true);

		NonAirFeesBreakdown result = calculator.calculate(input, client, client);

		assertThat(result.getGstAmount().doubleValue(), is(equalTo(176.36d)));
		assertThat(result.getMerchantFee().doubleValue(), is(equalTo(28.72d)));
		assertThat(result.getTotalSellingPrice().doubleValue(), is(equalTo(1464.78d)));
		assertThat(result.getGrossSellingPrice().doubleValue(), is(equalTo(1259.70d)));
		assertThat(result.getCommission(), is(nullValue()));
		assertThat(result.getNettCostGst(), is(nullValue()));
	}

	@Test
	public void nonStandardCC() {

		input.setCcType("Visa");
		input.setClientAccountNumber("clientNumber");

		client = new Client();
		Bank bank = new Bank();
		bank.setCcNumberPrefix("1234");
		bank.setStandard(false);
		bank.setPercentage(2d);
		ProductMerchantFee pf = new ProductMerchantFee();
		pf.setSubjectToMf(false);
		pf.setProductCode("01");
		client.setMfProducts(singletonList(pf));
		client.setMfBanks(singletonList(bank));
		CreditCardVendor cc = new CreditCardVendor();
		cc.setPercentage(2d);
		cc.setStandard(false);
		cc.setVendorName("Visa");
		client.setMfCcs(singletonList(cc));
		client.setApplyMfBank(false);
		client.setApplyMfCc(false);

		calculator.calculate(input, client, client);

		NonAirFeesBreakdown result = calculator.calculate(input, client, client);

		assertThat(result.getGstAmount().doubleValue(), is(equalTo(176.36d)));
		assertThat(result.getMerchantFee().doubleValue(), is(equalTo(28.72d)));
		assertThat(result.getTotalSellingPrice().doubleValue(), is(equalTo(1464.78d)));
		assertThat(result.getGrossSellingPrice().doubleValue(), is(equalTo(1259.70d)));
		assertThat(result.getCommission(), is(nullValue()));
		assertThat(result.getNettCostGst(), is(nullValue()));
	}

	@Test
	public void standardCC() {
		input.setCcType("Visa");
		input.setClientAccountNumber("clientNumber");

		client = new Client();
		ProductMerchantFee pf = new ProductMerchantFee();
		pf.setSubjectToMf(false);
		pf.setProductCode("01");
		client.setMfProducts(singletonList(pf));

		CreditCardVendor cc = new CreditCardVendor();
		cc.setPercentage(2d);
		cc.setStandard(true);
		cc.setVendorName("Visa");
		client.setMfCcs(singletonList(cc));
		client.setApplyMfBank(false);
		client.setApplyMfCc(true);

		NonAirFeesBreakdown result = calculator.calculate(input, client, client);

		assertThat(result.getGstAmount().doubleValue(), is(equalTo(176.36d)));
		assertThat(result.getMerchantFee().doubleValue(), is(equalTo(28.72d)));
		assertThat(result.getTotalSellingPrice().doubleValue(), is(equalTo(1464.78d)));
		assertThat(result.getGrossSellingPrice().doubleValue(), is(equalTo(1259.70d)));
		assertThat(result.getCommission(), is(nullValue()));
		assertThat(result.getNettCostGst(), is(nullValue()));
	}

	@Test
	public void standardCCAcctTypeNotEqual() {
		input.setCcType("Visas");
		input.setClientAccountNumber("clientNumber");

		client = new Client();
		ProductMerchantFee pf = new ProductMerchantFee();
		pf.setSubjectToMf(false);
		pf.setProductCode("01");
		client.setMfProducts(singletonList(pf));

		CreditCardVendor cc = new CreditCardVendor();
		cc.setPercentage(2d);
		cc.setStandard(true);
		cc.setVendorName("Visa");
		client.setMfCcs(singletonList(cc));
		client.setApplyMfBank(false);
		client.setApplyMfCc(true);

		NonAirFeesBreakdown result = calculator.calculate(input, client, client);

		assertNotNull(result);
	}

	@Test
	public void nullInput() {

		NonAirFeesBreakdown result = calculator.calculate(null, null, null);

		assertThat(result.getGstAmount(), is(nullValue()));
		assertThat(result.getMerchantFee(), is(nullValue()));
		assertThat(result.getTotalSellingPrice(), is(nullValue()));
		assertThat(result.getGrossSellingPrice(), is(nullValue()));
		assertThat(result.getCommission(), is(nullValue()));
		assertThat(result.getNettCostGst(), is(nullValue()));

		assertNotNull(calculator.calculate(new IndiaNonAirFeesInput(), null, null));
		assertNotNull(calculator.calculate(null, new Client(), null));
	}

	@Test
	public void calculateNoProductMerchant() {

		input.setCommissionByPercent(false);
		input.setDiscountByPercent(false);
		input.setClientAccountNumber("clientNumber");

		client = new Client();
		Bank bank = new Bank();
		bank.setCcNumberPrefix("1234");
		bank.setStandard(false);
		bank.setPercentage(2d);

		client.setMfBanks(singletonList(bank));
		client.setApplyMfBank(false);
		client.setApplyMfCc(false);

		calculator.calculate(input, client, client);

		NonAirFeesBreakdown result = calculator.calculate(input, client, client);

		assertThat(result.getGstAmount().doubleValue(), is(equalTo(182d)));
		assertThat(result.getMerchantFee().doubleValue(), is(equalTo(29.64d)));
		assertThat(result.getTotalSellingPrice().doubleValue(), is(equalTo(1511.64d)));
		assertThat(result.getGrossSellingPrice().doubleValue(), is(equalTo(1300d)));
		assertThat(result.getCommission(), is(nullValue()));
		assertThat(result.getNettCostGst(), is(nullValue()));
	}

	@Test
	public void calculateNoCommissionAndDiscount() {

		input.setCommissionByPercent(false);
		input.setDiscountByPercent(false);
		input.setClientAccountNumber("clientNumber");

		client = new Client();
		Bank bank = new Bank();
		bank.setCcNumberPrefix("1234");
		bank.setStandard(false);
		bank.setPercentage(2d);
		ProductMerchantFee pf = new ProductMerchantFee();
		pf.setSubjectToMf(true);
		pf.setProductCode("01");
		client.setMfProducts(singletonList(pf));
		client.setMfBanks(singletonList(bank));
		client.setApplyMfBank(false);
		client.setApplyMfCc(false);

		calculator.calculate(input, client, client);

		NonAirFeesBreakdown result = calculator.calculate(input, client, client);

		assertThat(result.getGstAmount().doubleValue(), is(equalTo(182d)));
		assertThat(result.getMerchantFee().doubleValue(), is(equalTo(0d)));
		assertThat(result.getTotalSellingPrice().doubleValue(), is(equalTo(1482d)));
		assertThat(result.getGrossSellingPrice().doubleValue(), is(equalTo(1300d)));
		assertThat(result.getCommission(), is(nullValue()));
		assertThat(result.getNettCostGst(), is(nullValue()));
	}

	@Test
	public void btcFop() {

		input.setFopMode(3);
		input.setClientAccountNumber("clientNumber");

		client = new Client();
		Bank bank = new Bank();
		bank.setCcNumberPrefix("1234");
		bank.setStandard(false);
		bank.setPercentage(2d);
		ProductMerchantFee pf = new ProductMerchantFee();
		pf.setSubjectToMf(false);
		pf.setProductCode("01");
		client.setMfProducts(singletonList(pf));
		client.setMfBanks(singletonList(bank));
		client.setApplyMfBank(false);
		client.setApplyMfCc(false);

		calculator.calculate(input, client, client);

		NonAirFeesBreakdown result = calculator.calculate(input, client, client);

		assertThat(result.getMerchantFee().doubleValue(), is(equalTo(0d)));
		assertThat(result.getGstAmount().doubleValue(), is(equalTo(176.36d)));
		assertThat(result.getTotalSellingPrice().doubleValue(), is(equalTo(1436.06d)));
		assertThat(result.getGrossSellingPrice().doubleValue(), is(equalTo(1259.70d)));
		assertThat(result.getCommission(), is(nullValue()));
		assertThat(result.getNettCostGst(), is(nullValue()));
	}

	@Test
	public void nonPresentVendorBank() {
		input.setCcType("MasterCard");
		input.setFopType("");
		input.setClientAccountNumber("clientNumber");

		client = new Client();
		client.setMerchantFee(3d);

		Bank bank = new Bank();
		bank.setCcNumberPrefix("");
		bank.setStandard(false);
		bank.setPercentage(2d);
		ProductMerchantFee pf = new ProductMerchantFee();
		pf.setSubjectToMf(false);
		pf.setProductCode("02");
		CreditCardVendor cc = new CreditCardVendor();
		cc.setPercentage(2d);
		cc.setStandard(true);
		cc.setVendorName("Visa");
		client.setMfCcs(singletonList(cc));
		client.setMfProducts(singletonList(pf));
		client.setMfBanks(singletonList(bank));
		client.setApplyMfCc(true);
		client.setApplyMfBank(true);

		calculator.calculate(input, client, client);

		NonAirFeesBreakdown result = calculator.calculate(input, client, client);

		assertThat(result.getMerchantFee().doubleValue(), is(equalTo(43.08d)));
		assertThat(result.getGstAmount().doubleValue(), is(equalTo(176.36d)));
		assertThat(result.getTotalSellingPrice().doubleValue(), is(equalTo(1479.14d)));
		assertThat(result.getGrossSellingPrice().doubleValue(), is(equalTo(1259.70d)));
		assertThat(result.getCommission(), is(nullValue()));
		assertThat(result.getNettCostGst(), is(nullValue()));
	}

	private BigDecimal round(BigDecimal value) {
		return value.setScale(2, RoundingMode.HALF_UP);
	}

	@Test
	public void standardMfProduct() {
		input.setClientAccountNumber("clientNumber");

		client = new Client();
		Bank bank = new Bank();
		bank.setCcNumberPrefix("12345");
		bank.setStandard(false);
		bank.setPercentage(2d);
		ProductMerchantFee pf = new ProductMerchantFee();
		pf.setSubjectToMf(false);
		pf.setProductCode("01");
		client.setStandardMfProduct(true);
		client.setMfProducts(singletonList(pf));
		client.setMfBanks(singletonList(bank));
		client.setApplyMfBank(false);
		client.setApplyMfCc(false);

		NonAirFeesBreakdown result = calculator.calculate(input, client, client);

		assertThat(result.getGstAmount().doubleValue(), is(equalTo(176.36d)));
		assertThat(result.getMerchantFee().doubleValue(), is(equalTo(0d)));
		assertThat(result.getTotalSellingPrice().doubleValue(), is(equalTo(1436.06d)));
		assertThat(result.getGrossSellingPrice().doubleValue(), is(equalTo(1259.70d)));
		assertThat(result.getCommission(), is(nullValue()));
		assertThat(result.getNettCostGst(), is(nullValue()));
	}

	@Test
	public void standardMfProductDefaultClient() {
		input.setClientAccountNumber("clientNumber");
		Client defaultClient = new Client();
		client = new Client();
		Bank bank = new Bank();
		bank.setCcNumberPrefix("12345");
		bank.setStandard(false);
		bank.setPercentage(2d);
		ProductMerchantFee pf = new ProductMerchantFee();
		pf.setSubjectToMf(false);
		pf.setProductCode("01");
		client.setStandardMfProduct(false);
		client.setMfProducts(singletonList(pf));
		client.setMfBanks(singletonList(bank));
		client.setApplyMfBank(false);
		client.setApplyMfCc(false);

		ProductMerchantFee pf2 = new ProductMerchantFee();
		pf2.setSubjectToMf(true);
		pf2.setProductCode("01");
		defaultClient.setMfProducts(singletonList(pf2));
		NonAirFeesBreakdown result = calculator.calculate(input, client, defaultClient);

		assertThat(result.getGstAmount().doubleValue(), is(equalTo(176.36d)));
		assertThat(result.getMerchantFee().doubleValue(), is(equalTo(0d)));
		assertThat(result.getTotalSellingPrice().doubleValue(), is(equalTo(1436.06d)));
		assertThat(result.getGrossSellingPrice().doubleValue(), is(equalTo(1259.70d)));
		assertThat(result.getCommission(), is(nullValue()));
		assertThat(result.getNettCostGst(), is(nullValue()));
	}

}
