package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.cwt.bpg.cbt.exchange.order.exception.ExchangeOrderNoContentException;
import com.cwt.bpg.cbt.exchange.order.model.CarVendor;
import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrder;
import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrderSearchParam;
import com.cwt.bpg.cbt.exchange.order.model.RoomType;
import com.cwt.bpg.cbt.exchange.order.model.VmpdReasonCode;
import com.cwt.bpg.cbt.exchange.order.model.india.AirMiscInfo;

public class ExchangeOrderServiceTest {

    @InjectMocks
    private ExchangeOrderService service;

    @Mock
    private ExchangeOrderRepository repository;
    
    @Mock
    private RoomTypeRepository roomTypeRepository;
    
    @Mock
    private VmpdReasonCodesRepository reasonCodeRepository;
    
    @Mock
    private AirMiscInfoRepository airMiscInfoRepository;
    
    @Mock
    private CarVendorRepository carVendorRepository;

    @Mock
    private ExchangeOrderInsertService insertService;

    @Mock
    private ExchangeOrderUpdateService updateService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCallInsertWhenEoNumberIsNull() throws ExchangeOrderNoContentException {
        ExchangeOrder eo = new ExchangeOrder();

        service.save("sg",eo);

        verify(insertService, times(1)).insert(eo);
    }

    @Test
    public void shouldCallUpdateWhenEoNumberIsNotNull() throws ExchangeOrderNoContentException {
        ExchangeOrder eo = new ExchangeOrder();
        eo.setEoNumber("1234567890");

        service.save("sg",eo);

        verify(updateService, times(1)).update(eo);
    }

    @Test
    public void shouldCallGetExchangeOrder() {
        String eoNumber = "1806100005";
        service.get(eoNumber);
        verify(repository, times(1)).getExchangeOrder(eoNumber);
    }

    @Test
    public void shouldCallSearch() {
        service.search(new ExchangeOrderSearchParam());
        verify(repository, times(1)).search(Mockito.any(ExchangeOrderSearchParam.class));
    }

    @Test
    public void shouldCallUpdate() {
        ExchangeOrder exchangeOrder = new ExchangeOrder();
        exchangeOrder.setEoNumber("123456");
        service.update(exchangeOrder);
        verify(repository, times(1)).updateFinance(Mockito.any(ExchangeOrder.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateShouldReturnErrorWhenEoNumberIsNull() {
        service.update(new ExchangeOrder());
        verify(repository, times(1)).updateFinance(Mockito.any(ExchangeOrder.class));
    }

    @Test
    public void shouldCallGetExchangeOrderByRecordLocator() {
        String recordLocator = "X1X1X1";
        service.getExchangeOrderByRecordLocator("IN",recordLocator);
        verify(repository, times(1)).getExchangeOrderByRecordLocator("IN",recordLocator);
    }

    @Test
    public void shouldCallNewGetExchangeOrder() {
        String recorLocator = "X1X1X1";
        service.get("IN",recorLocator);
        verify(repository, times(1)).getExchangeOrder("IN",recorLocator);
    }
    
    @Test
    public void shouldCallGetAll() {
        service.getAll();
        verify(roomTypeRepository, times(1)).getAll();
    }
    
    @Test
    public void shouldCallSaveRoomType() {
    	service.save(new RoomType());
        verify(roomTypeRepository, times(1)).put(Mockito.any(RoomType.class));
    }
    
    @Test
    public void shouldCallDelete() {
    	String code = "A";
        service.delete(code);
        verify(roomTypeRepository, times(1)).remove(code);
    }
    
    @Test
    public void shouldCallGetAllVmpdReasonCodes() {
        service.getAllVmpdReasonCodes();
        verify(reasonCodeRepository, times(1)).getAll();
    }
    
    @Test
    public void shouldCallSaveVmpdReasonCode() {
    	service.saveVmpdReasonCode(new VmpdReasonCode());
        verify(reasonCodeRepository, times(1)).put(Mockito.any(VmpdReasonCode.class));
    }
    
    @Test
    public void shouldCallDeleteVmpd() {
    	String code = "A";
        service.deleteVmpdReasonCode(code);
        verify(reasonCodeRepository, times(1)).remove(code);
    }
    
    @Test
    public void shouldCallGetAllCarVendors() {
        service.getAllCarVendors();
        verify(carVendorRepository, times(1)).getAll();
    }
    
    @Test
    public void shouldCallSaveCarVendor() {
    	service.saveCarVendor(new CarVendor());
        verify(carVendorRepository, times(1)).put(Mockito.any(CarVendor.class));
    }
    
    @Test
    public void shouldCallDeleteCarVendor() {
    	String code = "AB";
        service.deleteCarVendor(code);
        verify(carVendorRepository, times(1)).remove(code);
    }
    
	@Test
	public void shouldCallGetAirMiscInfo() {
		String clientAccountNumber = "12345";
		String reportingFieldTypeId = "5";

		service.getAirMiscInfo(clientAccountNumber);
		verify(airMiscInfoRepository, times(1)).getAirMiscInfo(clientAccountNumber,
				reportingFieldTypeId);
	}

	@Test
	public void shouldCallSaveAirMiscInfo() {
		service.saveAirMiscInfo(new AirMiscInfo());
		verify(airMiscInfoRepository, times(1)).put(Mockito.any(AirMiscInfo.class));
	}

	@Test
	public void shouldCallDeleteAirMiscInfo() {
		String id = new ObjectId().toString();
		when(airMiscInfoRepository.remove(any(ObjectId.class))).thenReturn(id);

		String result = service.deleteAirMiscInfo(id);

		assertEquals(id, result);
		verify(airMiscInfoRepository, times(1)).remove(any(ObjectId.class));
	}

}

