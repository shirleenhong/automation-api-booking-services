package com.cwt.bpg.cbt.exchange.order.calculator;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

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

	private InNonAirFeesInput input = new InNonAirFeesInput();

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		Mockito.when(scaleConfig.getScale(Mockito.eq(Country.INDIA.getCode()))).thenReturn(2);

		ReflectionTestUtils.setField(calculator, "scaleConfig", scaleConfig);

		input.setAcctType("acctType");
		input.setCommissionByPercent(true);
		input.setCommissionPercent(2D);
		input.setCostAmount(round(new BigDecimal(1300)));
		input.setCountryCode(Country.INDIA.getCode());
		input.setDiscountByPercent(true);
		input.setDiscountPercent(5D);
		input.setFopMode(4);
		input.setFopNumber("1234");
		input.setFopType(FOPTypes.CWT.getCode());
		
		InProduct product= new InProduct();
		product.setGst(6D);
		product.setOt1(3D);
		product.setOt2(5D);
		product.setProductCode("01");
		input.setProduct(product);
	}

	@Test
	public void standardBank() {

		input.setProfileName("profileName");

		client = new Client();
		Bank bank = new Bank();
		bank.setCcNumberPrefix("12345");
		bank.setStandard(false);
		bank.setPercentage(2D);
		ProductMerchantFee pf = new ProductMerchantFee();
		pf.setSubjectToMf(false);
		pf.setProductCode("01");
		client.setMfProducts(Arrays.asList(pf));
		client.setMfBanks(Arrays.asList(bank));
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

		input.setProfileName("profileName");

		client = new Client();
		Bank bank = new Bank();
		bank.setCcNumberPrefix("1234");
		bank.setStandard(false);
		bank.setPercentage(2D);
		ProductMerchantFee pf = new ProductMerchantFee();
		pf.setSubjectToMf(true);
		pf.setProductCode("01");
		client.setMfProducts(Arrays.asList(pf));
		client.setMfBanks(Arrays.asList(bank));
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

		input.setProfileName("profileName");

		client = new Client();
		Bank bank = new Bank();
		bank.setCcNumberPrefix("1234");
		bank.setStandard(true);
		bank.setPercentage(2D);
		ProductMerchantFee pf = new ProductMerchantFee();
		pf.setSubjectToMf(false);
		pf.setProductCode("01");
		client.setMfProducts(Arrays.asList(pf));
		client.setMfBanks(Arrays.asList(bank));
		client.setApplyMfCc(true);
		client.setApplyMfBank(true);

		NonAirFeesBreakdown result = calculator.calculate(input, client, client);

		assertThat(result.getGstAmount().doubleValue(), is(equalTo(176.36D)));
		assertThat(result.getMerchantFee().doubleValue(), is(equalTo(26.71D)));
		assertThat(result.getTotalSellingPrice().doubleValue(), is(equalTo(1462.77D)));
		assertThat(result.getGrossSellingPrice().doubleValue(), is(equalTo(1259.70D)));
		assertThat(result.getCommission(), is(nullValue()));
		assertThat(result.getNettCostGst(), is(nullValue()));
	}

	@Test
	public void nonStandardCC() {

		input.setAcctType("Visa");
		input.setProfileName("profileName");

		client = new Client();
		Bank bank = new Bank();
		bank.setCcNumberPrefix("1234");
		bank.setStandard(false);
		bank.setPercentage(2D);
		ProductMerchantFee pf = new ProductMerchantFee();
		pf.setSubjectToMf(false);
		pf.setProductCode("01");
		client.setMfProducts(Arrays.asList(pf));
		client.setMfBanks(Arrays.asList(bank));
		CreditCardVendor cc = new CreditCardVendor();
		cc.setPercentage(2D);
		cc.setStandard(false);
		cc.setVendorName("Visa");
		client.setMfCcs(Arrays.asList(cc));
		client.setApplyMfBank(false);
		client.setApplyMfCc(false);

		calculator.calculate(input, client, client);

		NonAirFeesBreakdown result = calculator.calculate(input, client, client);

		assertThat(result.getGstAmount().doubleValue(), is(equalTo(176.36D)));
		assertThat(result.getMerchantFee().doubleValue(), is(equalTo(26.71D)));
		assertThat(result.getTotalSellingPrice().doubleValue(), is(equalTo(1462.77D)));
		assertThat(result.getGrossSellingPrice().doubleValue(), is(equalTo(1259.70D)));
		assertThat(result.getCommission(), is(nullValue()));
		assertThat(result.getNettCostGst(), is(nullValue()));
	}

	@Test
	public void standardCC() {
		input.setAcctType("Visa");
		input.setProfileName("profileName");

		client = new Client();
		ProductMerchantFee pf = new ProductMerchantFee();
		pf.setSubjectToMf(false);
		pf.setProductCode("01");
		client.setMfProducts(Arrays.asList(pf));

		CreditCardVendor cc = new CreditCardVendor();
		cc.setPercentage(2D);
		cc.setStandard(true);
		cc.setVendorName("Visa");
		client.setMfCcs(Arrays.asList(cc));
		client.setApplyMfBank(false);
		client.setApplyMfCc(true);

		NonAirFeesBreakdown result = calculator.calculate(input, client, client);

		assertThat(result.getGstAmount().doubleValue(), is(equalTo(176.36D)));
		assertThat(result.getMerchantFee().doubleValue(), is(equalTo(26.71D)));
		assertThat(result.getTotalSellingPrice().doubleValue(), is(equalTo(1462.77D)));
		assertThat(result.getGrossSellingPrice().doubleValue(), is(equalTo(1259.70D)));
		assertThat(result.getCommission(), is(nullValue()));
		assertThat(result.getNettCostGst(), is(nullValue()));
	}

	@Test
	public void standardCCAcctTypeNotEqual() {
		input.setAcctType("Visas");
		input.setProfileName("profileName");

		client = new Client();
		ProductMerchantFee pf = new ProductMerchantFee();
		pf.setSubjectToMf(false);
		pf.setProductCode("01");
		client.setMfProducts(Arrays.asList(pf));

		CreditCardVendor cc = new CreditCardVendor();
		cc.setPercentage(2D);
		cc.setStandard(true);
		cc.setVendorName("Visa");
		client.setMfCcs(Arrays.asList(cc));
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

		assertNotNull(calculator.calculate(new InNonAirFeesInput(), null, null));
		assertNotNull(calculator.calculate(null, new Client(), null));
	}

	@Test
	public void calculateNoProductMerchant() {

		input.setCommissionByPercent(false);
		input.setDiscountByPercent(false);
		input.setProfileName("profileName");

		client = new Client();
		Bank bank = new Bank();
		bank.setCcNumberPrefix("1234");
		bank.setStandard(false);
		bank.setPercentage(2D);

		client.setMfBanks(Arrays.asList(bank));
		client.setApplyMfBank(false);
		client.setApplyMfCc(false);

		calculator.calculate(input, client, client);

		NonAirFeesBreakdown result = calculator.calculate(input, client, client);

		assertThat(result.getGstAmount().doubleValue(), is(equalTo(182D)));
		assertThat(result.getMerchantFee().doubleValue(), is(equalTo(27.56D)));
		assertThat(result.getTotalSellingPrice().doubleValue(), is(equalTo(1509.56D)));
		assertThat(result.getGrossSellingPrice().doubleValue(), is(equalTo(1300D)));
		assertThat(result.getCommission(), is(nullValue()));
		assertThat(result.getNettCostGst(), is(nullValue()));
	}

	@Test
	public void calculateNoCommissionAndDiscount() {

		input.setCommissionByPercent(false);
		input.setDiscountByPercent(false);
		input.setProfileName("profileName");

		client = new Client();
		Bank bank = new Bank();
		bank.setCcNumberPrefix("1234");
		bank.setStandard(false);
		bank.setPercentage(2D);
		ProductMerchantFee pf = new ProductMerchantFee();
		pf.setSubjectToMf(true);
		pf.setProductCode("01");
		client.setMfProducts(Arrays.asList(pf));
		client.setMfBanks(Arrays.asList(bank));
		client.setApplyMfBank(false);
		client.setApplyMfCc(false);

		calculator.calculate(input, client, client);

		NonAirFeesBreakdown result = calculator.calculate(input, client, client);

		assertThat(result.getGstAmount().doubleValue(), is(equalTo(182D)));
		assertThat(result.getMerchantFee().doubleValue(), is(equalTo(0D)));
		assertThat(result.getTotalSellingPrice().doubleValue(), is(equalTo(1482D)));
		assertThat(result.getGrossSellingPrice().doubleValue(), is(equalTo(1300D)));
		assertThat(result.getCommission(), is(nullValue()));
		assertThat(result.getNettCostGst(), is(nullValue()));
	}

	@Test
	public void btcFop() {
		
		input.setFopMode(3);
		input.setProfileName("profileName");

		client = new Client();
		Bank bank = new Bank();
		bank.setCcNumberPrefix("1234");
		bank.setStandard(false);
		bank.setPercentage(2D);
		ProductMerchantFee pf = new ProductMerchantFee();
		pf.setSubjectToMf(false);
		pf.setProductCode("01");
		client.setMfProducts(Arrays.asList(pf));
		client.setMfBanks(Arrays.asList(bank));
		client.setApplyMfBank(false);
		client.setApplyMfCc(false);

		calculator.calculate(input, client, client);

		NonAirFeesBreakdown result = calculator.calculate(input, client, client);

		assertThat(result.getMerchantFee().doubleValue(), is(equalTo(0D)));
		assertThat(result.getGstAmount().doubleValue(), is(equalTo(176.36D)));
		assertThat(result.getTotalSellingPrice().doubleValue(), is(equalTo(1436.06D)));
		assertThat(result.getGrossSellingPrice().doubleValue(), is(equalTo(1259.70D)));
		assertThat(result.getCommission(), is(nullValue()));
		assertThat(result.getNettCostGst(), is(nullValue()));
	}

	@Test
	public void nonPresentVendorBank() {
		input.setAcctType("MasterCard");
		input.setFopType("");
		input.setProfileName("profileName");

		client = new Client();
		client.setMerchantFee(3D);

		Bank bank = new Bank();
		bank.setCcNumberPrefix("");
		bank.setStandard(false);
		bank.setPercentage(2D);
		ProductMerchantFee pf = new ProductMerchantFee();
		pf.setSubjectToMf(false);
		pf.setProductCode("02");
		CreditCardVendor cc = new CreditCardVendor();
		cc.setPercentage(2D);
		cc.setStandard(true);
		cc.setVendorName("Visa");
		client.setMfCcs(Arrays.asList(cc));
		client.setMfProducts(Arrays.asList(pf));
		client.setMfBanks(Arrays.asList(bank));
		client.setApplyMfCc(true);
		client.setApplyMfBank(true);

		calculator.calculate(input, client, client);

		NonAirFeesBreakdown result = calculator.calculate(input, client, client);

		assertThat(result.getMerchantFee().doubleValue(), is(equalTo(40.06D)));
		assertThat(result.getGstAmount().doubleValue(), is(equalTo(176.36D)));
		assertThat(result.getTotalSellingPrice().doubleValue(), is(equalTo(1476.12D)));
		assertThat(result.getGrossSellingPrice().doubleValue(), is(equalTo(1259.70D)));
		assertThat(result.getCommission(), is(nullValue()));
		assertThat(result.getNettCostGst(), is(nullValue()));
	}

	private BigDecimal round(BigDecimal value) {
		return value.setScale(2, RoundingMode.HALF_UP);
	}
	
	@Test
	public void standardMfProduct() {
		input.setProfileName("profileName");
		
		client = new Client();
		Bank bank = new Bank();
		bank.setCcNumberPrefix("12345");
		bank.setStandard(false);
		bank.setPercentage(2D);
		ProductMerchantFee pf = new ProductMerchantFee();
		pf.setSubjectToMf(false);
		pf.setProductCode("01");
		client.setStandardMfProduct(true);
		client.setMfProducts(Arrays.asList(pf));
		client.setMfBanks(Arrays.asList(bank));
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
	public void standardMfProductDefaultClient() {
		input.setProfileName("profileName");
		Client defaultClient = new Client();
		client = new Client();
		Bank bank = new Bank();
		bank.setCcNumberPrefix("12345");
		bank.setStandard(false);
		bank.setPercentage(2D);
		ProductMerchantFee pf = new ProductMerchantFee();
		pf.setSubjectToMf(false);
		pf.setProductCode("01");
		client.setStandardMfProduct(false);
		client.setMfProducts(Arrays.asList(pf));
		client.setMfBanks(Arrays.asList(bank));
		client.setApplyMfBank(false);
		client.setApplyMfCc(false);
		
		ProductMerchantFee pf2 = new ProductMerchantFee();
		pf2.setSubjectToMf(true);
		pf2.setProductCode("01");
		defaultClient.setMfProducts(Arrays.asList(pf2));
		NonAirFeesBreakdown result = calculator.calculate(input, client, defaultClient);

		assertThat(result.getGstAmount().doubleValue(), is(equalTo(176.36D)));
		assertThat(result.getMerchantFee().doubleValue(), is(equalTo(0D)));
		assertThat(result.getTotalSellingPrice().doubleValue(), is(equalTo(1436.06D)));
		assertThat(result.getGrossSellingPrice().doubleValue(), is(equalTo(1259.70D)));
		assertThat(result.getCommission(), is(nullValue()));
		assertThat(result.getNettCostGst(), is(nullValue()));
	}
	
}
