package com.cwt.bpg.cbt.exchange.order.calculator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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
import com.cwt.bpg.cbt.exchange.order.model.Bank;
import com.cwt.bpg.cbt.exchange.order.model.Client;
import com.cwt.bpg.cbt.exchange.order.model.CreditCardVendor;
import com.cwt.bpg.cbt.exchange.order.model.FOPTypes;
import com.cwt.bpg.cbt.exchange.order.model.InMiscFeesInput;
import com.cwt.bpg.cbt.exchange.order.model.MiscFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.Product;
import com.cwt.bpg.cbt.exchange.order.model.ProductMerchantFee;

public class InMiscFeeCalculatorTest {

	@InjectMocks
	private InMiscFeeCalculator calculator = new InMiscFeeCalculator();
	
	@Mock
	private ScaleConfig scaleConfig;
	
	private Client client;
	
	private InMiscFeesInput input = new InMiscFeesInput();
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
				
		Mockito.when(scaleConfig.getScale(Mockito.eq("IN"))).thenReturn(2);
		
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
		
		Product product= new Product();
		product.setGst(6);
		product.setoT1(3);
		product.setoT2(5);
		product.setProductCode("01");
		input.setProduct(product);
	}
	
	@Test
	public void standardBank() {
		
		input.setProfileName("profileName");
		
		client = new Client();
		Bank bank = new Bank();
		bank.setCcNumberPrefix("1234");
		bank.setStandard(false);
		bank.setPercentage(2D);
		ProductMerchantFee pf = new ProductMerchantFee();
		pf.setSubjectToMf(false);
		pf.setProductCode("01");
		client.setProducts(Arrays.asList(pf));
		client.setBanks(Arrays.asList(bank));
		client.setApplyMfBank(false);
		client.setApplyMfCc(false);
		
		MiscFeesBreakdown result = calculator.calculate(input, client);
		
		assertEquals(round(new BigDecimal(26)), result.getCommission());
		assertEquals(round(new BigDecimal(176.36)), result.getGstAmount());
		assertEquals(round(new BigDecimal(0.00)), result.getMerchantFee());
		assertEquals(round(new BigDecimal(1335.28)), result.getSellingPriceInDi());
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
		client.setProducts(Arrays.asList(pf));
		client.setBanks(Arrays.asList(bank));
		client.setApplyMfBank(false);
		client.setApplyMfCc(false);
		
		MiscFeesBreakdown result = calculator.calculate(input, client);
		
		assertEquals(round(new BigDecimal(26)), result.getCommission());
		assertEquals(round(new BigDecimal(176.36)), result.getGstAmount());
		assertEquals(round(new BigDecimal(0.00)), result.getMerchantFee());
		assertEquals(round(new BigDecimal(1335.28)), result.getSellingPriceInDi());
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
		client.setProducts(Arrays.asList(pf));
		client.setBanks(Arrays.asList(bank));
		client.setApplyMfCc(true);
		client.setApplyMfBank(true);
		
		MiscFeesBreakdown result = calculator.calculate(input, client);		

		assertEquals(round(new BigDecimal(26)), result.getCommission());
		assertEquals(round(new BigDecimal(176.36)), result.getGstAmount());
		assertEquals(round(new BigDecimal(26.70564)), result.getMerchantFee());
		assertEquals(round(new BigDecimal(1361.98764)), result.getSellingPriceInDi());
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
		client.setProducts(Arrays.asList(pf));
		client.setBanks(Arrays.asList(bank));
		CreditCardVendor cc = new CreditCardVendor();
		cc.setPercentage(2D);
		cc.setStandard(false);
		cc.setVendorName("Visa");
		client.setVendors(Arrays.asList(cc));
		client.setApplyMfBank(false);
		client.setApplyMfCc(false);
		
		calculator.calculate(input, client);		

		MiscFeesBreakdown result = calculator.calculate(input, client);		

		assertEquals(round(new BigDecimal(26)), result.getCommission());
		assertEquals(round(new BigDecimal(176.36)), result.getGstAmount());
		assertEquals(round(new BigDecimal(0.00)), result.getMerchantFee());
		assertEquals(round(new BigDecimal(1335.28)), result.getSellingPriceInDi());
	}
	
	@Test
	public void standardCC() {
		input.setAcctType("Visa");
		input.setProfileName("profileName");
		
		client = new Client();
		ProductMerchantFee pf = new ProductMerchantFee();
		pf.setSubjectToMf(false);
		pf.setProductCode("01");
		client.setProducts(Arrays.asList(pf));
		
		CreditCardVendor cc = new CreditCardVendor();
		cc.setPercentage(2D);
		cc.setStandard(true);
		cc.setVendorName("Visa");
		client.setVendors(Arrays.asList(cc));
		client.setApplyMfBank(false);
		client.setApplyMfCc(true);
		
		MiscFeesBreakdown result = calculator.calculate(input, client);		

		assertEquals(round(new BigDecimal(26)), result.getCommission());
		assertEquals(round(new BigDecimal(176.36)), result.getGstAmount());
		assertEquals(round(new BigDecimal(26.71)), result.getMerchantFee());
		assertEquals(round(new BigDecimal(1361.99)), result.getSellingPriceInDi());
	}
	
	@Test
	public void nullInput() {
		
		MiscFeesBreakdown result = calculator.calculate(null, null);	

		assertEquals(BigDecimal.ZERO, result.getCommission());
		assertEquals(BigDecimal.ZERO, result.getGstAmount());
		assertEquals(BigDecimal.ZERO, result.getMerchantFee());
		assertEquals(BigDecimal.ZERO, result.getSellingPriceInDi());
		
		assertNotNull(calculator.calculate(new InMiscFeesInput(), null));
		assertNotNull(calculator.calculate(null, new Client()));
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
		
		client.setBanks(Arrays.asList(bank));
		client.setApplyMfBank(false);
		client.setApplyMfCc(false);
		
		calculator.calculate(input, client);
		
		MiscFeesBreakdown result = calculator.calculate(input, client);		

		assertNull(result.getCommission());
		assertEquals(round(new BigDecimal(182)), result.getGstAmount());
		assertEquals(round(new BigDecimal(0)), result.getMerchantFee());
		assertEquals(round(new BigDecimal(1378)), result.getSellingPriceInDi());
	}
	
	@Test
	public void calculateNocommisionAndDiscount() {
		
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
		client.setProducts(Arrays.asList(pf));
		client.setBanks(Arrays.asList(bank));
		client.setApplyMfBank(false);
		client.setApplyMfCc(false);
		
		calculator.calculate(input, client);
		
		MiscFeesBreakdown result = calculator.calculate(input, client);		

		assertNull(result.getCommission());
		assertEquals(round(new BigDecimal(182)), result.getGstAmount());
		assertEquals(round(new BigDecimal(0)), result.getMerchantFee());
		assertEquals(round(new BigDecimal(1378)), result.getSellingPriceInDi());
	}
	
	@Test
	public void btcFop() {
		input.setProduct(null);
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
		client.setProducts(Arrays.asList(pf));
		client.setBanks(Arrays.asList(bank));
		client.setApplyMfBank(false);
		client.setApplyMfCc(false);
		
		calculator.calculate(input, client);
		
		MiscFeesBreakdown result = calculator.calculate(input, client);		

		assertEquals(round(new BigDecimal(26)), result.getCommission());
		assertEquals(BigDecimal.ZERO, result.getGstAmount());
		assertEquals(round(new BigDecimal(0)), result.getMerchantFee());
		assertEquals(round(new BigDecimal(1259.70)), result.getSellingPriceInDi());
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
		client.setVendors(Arrays.asList(cc));
		client.setProducts(Arrays.asList(pf));
		client.setBanks(Arrays.asList(bank));
		client.setApplyMfCc(true);
		client.setApplyMfBank(true);
		
		calculator.calculate(input, client);
		
		MiscFeesBreakdown result = calculator.calculate(input, client);		

		assertEquals(round(new BigDecimal(26)), result.getCommission());
		assertEquals(round(new BigDecimal(176.36)), result.getGstAmount());
		assertEquals(round(new BigDecimal(40.06)), result.getMerchantFee());
		assertEquals(round(new BigDecimal(1375.34)), result.getSellingPriceInDi());
	}

	private BigDecimal round(BigDecimal value) {
		return value.setScale(2, RoundingMode.HALF_UP);
	}
}
