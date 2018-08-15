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

		Mockito.when(scaleConfig.getScale(Mockito.eq(Country.INDIA.getCode()))).thenReturn(0);

		ReflectionTestUtils.setField(calculator, "scaleConfig", scaleConfig);

		input.setCcType("VI");
		input.setCommissionByPercent(true);
		input.setCommissionPercent(2D);
		input.setCostAmount(round(new BigDecimal(1300)));
		input.setDiscountByPercent(true);
		input.setDiscountPercent(5D);
		input.setFopMode(4);
		input.setFopNumber("1234");
		input.setFopType(FopTypes.CWT);

		IndiaNonAirProductInput product= new IndiaNonAirProductInput();
		product.setGstPercent(6D);
		product.setOt1Percent(3D);
		product.setOt2Percent(5D);
		product.setProductCode("01");
		input.setProduct(product);
	}

	@Test
	public void standardBank() {

		input.setClientAccountNumber("1234");

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

		IndiaNonAirFeesBreakdown result = calculator.calculate(input, client, client);

		assertThat(result.getGstAmount(), is(equalTo(new BigDecimal(177))));
		assertThat(result.getMerchantFee(), is(equalTo(BigDecimal.ZERO)));
		assertThat(result.getTotalSellingPrice(), is(equalTo(new BigDecimal(1437))));
		assertThat(result.getGrossSellingPrice(), is(equalTo(new BigDecimal(1260))));
		assertThat(result.getCommission(), is(equalTo(new BigDecimal(26))));
		assertThat(result.getNettCostGst(), is(nullValue()));
	}

	@Test
	public void standardBank2() {

		input.setClientAccountNumber("1234");

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

		IndiaNonAirFeesBreakdown result = calculator.calculate(input, client, client);

		assertThat(result.getGstAmount(), is(equalTo(new BigDecimal(177))));
		assertThat(result.getMerchantFee(), is(equalTo(BigDecimal.ZERO)));
		assertThat(result.getTotalSellingPrice(), is(equalTo(new BigDecimal(1437))));
		assertThat(result.getGrossSellingPrice(), is(equalTo(new BigDecimal(1260))));
		assertThat(result.getCommission(), is(equalTo(new BigDecimal(26))));
		assertThat(result.getNettCostGst(), is(nullValue()));
	}

	@Test
	public void nonStandardBank() {

		input.setClientAccountNumber("1234");

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

		IndiaNonAirFeesBreakdown result = calculator.calculate(input, client, client);

		assertThat(result.getGstAmount(), is(equalTo(new BigDecimal(177))));
		assertThat(result.getMerchantFee(), is(equalTo(new BigDecimal(29))));
		assertThat(result.getTotalSellingPrice(), is(equalTo(new BigDecimal(1466))));
		assertThat(result.getGrossSellingPrice(), is(equalTo(new BigDecimal(1260))));
		assertThat(result.getCommission(), is(equalTo(new BigDecimal(26))));
		assertThat(result.getNettCostGst(), is(nullValue()));
	}

	@Test
	public void nonStandardCC() {

		input.setCcType("Visa");
		input.setClientAccountNumber("1234");

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

		IndiaNonAirFeesBreakdown result = calculator.calculate(input, client, client);

		assertThat(result.getGstAmount(), is(equalTo(new BigDecimal(177))));
		assertThat(result.getMerchantFee(), is(equalTo(new BigDecimal(29))));
		assertThat(result.getTotalSellingPrice(), is(equalTo(new BigDecimal(1466))));
		assertThat(result.getGrossSellingPrice(), is(equalTo(new BigDecimal(1260))));
		assertThat(result.getCommission(), is(equalTo(new BigDecimal(26))));
		assertThat(result.getNettCostGst(), is(nullValue()));
	}

	@Test
	public void standardCC() {
		input.setCcType("Visa");
		input.setClientAccountNumber("1234");

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

		IndiaNonAirFeesBreakdown result = calculator.calculate(input, client, client);

		assertThat(result.getGstAmount(), is(equalTo(new BigDecimal(177))));
		assertThat(result.getMerchantFee(), is(equalTo(new BigDecimal(29))));
		assertThat(result.getTotalSellingPrice(), is(equalTo(new BigDecimal(1466))));
		assertThat(result.getGrossSellingPrice(), is(equalTo(new BigDecimal(1260))));
		assertThat(result.getCommission(), is(equalTo(new BigDecimal(26))));
		assertThat(result.getNettCostGst(), is(nullValue()));
	}

	@Test
	public void standardCCAcctTypeNotEqual() {
		input.setCcType("Visas");
		input.setClientAccountNumber("1234");

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

		IndiaNonAirFeesBreakdown result = calculator.calculate(null, null, null);

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
		input.setClientAccountNumber("1234");

		client = new Client();
		Bank bank = new Bank();
		bank.setCcNumberPrefix("1234");
		bank.setStandard(false);
		bank.setPercentage(2d);

		client.setMfBanks(singletonList(bank));
		client.setApplyMfBank(false);
		client.setApplyMfCc(false);

		calculator.calculate(input, client, client);

		IndiaNonAirFeesBreakdown result = calculator.calculate(input, client, client);

		assertThat(result.getGstAmount(), is(equalTo(new BigDecimal(182))));
		assertThat(result.getMerchantFee(), is(equalTo(new BigDecimal(30))));
		assertThat(result.getTotalSellingPrice(), is(equalTo(new BigDecimal(1512))));
		assertThat(result.getGrossSellingPrice(), is(equalTo(new BigDecimal(1300))));
		assertThat(result.getCommission(), is(equalTo(BigDecimal.ZERO)));
		assertThat(result.getNettCostGst(), is(nullValue()));
	}

	@Test
	public void calculateNoCommissionAndDiscount() {

		input.setCommissionByPercent(false);
		input.setDiscountByPercent(false);
		input.setClientAccountNumber("1234");

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

		IndiaNonAirFeesBreakdown result = calculator.calculate(input, client, client);

		assertThat(result.getGstAmount(), is(equalTo(new BigDecimal(182))));
		assertThat(result.getMerchantFee(), is(equalTo(BigDecimal.ZERO)));
		assertThat(result.getTotalSellingPrice(), is(equalTo(new BigDecimal(1482))));
		assertThat(result.getGrossSellingPrice(), is(equalTo(new BigDecimal(1300))));
		assertThat(result.getCommission(), is(equalTo(BigDecimal.ZERO)));
		assertThat(result.getNettCostGst(), is(nullValue()));
	}

	@Test
	public void btcFop() {

		input.setFopMode(3);
		input.setClientAccountNumber("1234");

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

		IndiaNonAirFeesBreakdown result = calculator.calculate(input, client, client);

		assertThat(result.getMerchantFee(), is(equalTo(BigDecimal.ZERO)));
		assertThat(result.getGstAmount(), is(equalTo(new BigDecimal(177))));
		assertThat(result.getTotalSellingPrice(), is(equalTo(new BigDecimal(1437))));
		assertThat(result.getGrossSellingPrice(), is(equalTo(new BigDecimal(1260))));
		assertThat(result.getCommission(), is(equalTo(new BigDecimal(26))));
		assertThat(result.getNettCostGst(), is(nullValue()));
	}

	@Test
	public void nonPresentVendorBank() {
		input.setCcType("MasterCard");
		input.setFopType(null);
		input.setClientAccountNumber("1234");

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

		IndiaNonAirFeesBreakdown result = calculator.calculate(input, client, client);

		assertThat(result.getMerchantFee(), is(equalTo(new BigDecimal(43))));
		assertThat(result.getGstAmount(), is(equalTo(new BigDecimal(177))));
		assertThat(result.getTotalSellingPrice(), is(equalTo(new BigDecimal(1480))));
		assertThat(result.getGrossSellingPrice(), is(equalTo(new BigDecimal(1260))));
		assertThat(result.getCommission(), is(equalTo(new BigDecimal(26))));
		assertThat(result.getNettCostGst(), is(nullValue()));
	}

	private BigDecimal round(BigDecimal value) {
		return value.setScale(2, RoundingMode.HALF_UP);
	}

	@Test
	public void standardMfProduct() {
		input.setClientAccountNumber("1234");

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

		IndiaNonAirFeesBreakdown result = calculator.calculate(input, client, client);

		assertThat(result.getGstAmount(), is(equalTo(new BigDecimal(177))));
		assertThat(result.getMerchantFee(), is(equalTo(BigDecimal.ZERO)));
		assertThat(result.getTotalSellingPrice(), is(equalTo(new BigDecimal(1437))));
		assertThat(result.getGrossSellingPrice(), is(equalTo(new BigDecimal(1260))));
		assertThat(result.getCommission(), is(equalTo(new BigDecimal(26))));
		assertThat(result.getNettCostGst(), is(nullValue()));
	}

	@Test
	public void standardMfProductDefaultClient() {
		input.setClientAccountNumber("1234");
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
		IndiaNonAirFeesBreakdown result = calculator.calculate(input, client, defaultClient);

		assertThat(result.getGstAmount(), is(equalTo(new BigDecimal(177))));
		assertThat(result.getMerchantFee(), is(equalTo(BigDecimal.ZERO)));
		assertThat(result.getTotalSellingPrice(), is(equalTo(new BigDecimal(1437))));
		assertThat(result.getGrossSellingPrice(), is(equalTo(new BigDecimal(1260))));
		assertThat(result.getCommission(), is(equalTo(new BigDecimal(26))));
		assertThat(result.getNettCostGst(), is(nullValue()));
	}

}
