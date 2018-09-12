package com.cwt.bpg.cbt.exchange.order;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.cwt.bpg.cbt.exchange.order.exception.ExchangeOrderNoContentException;
import com.cwt.bpg.cbt.exchange.order.model.*;

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

        service.saveExchangeOrder("sg",eo);

        verify(insertService, times(1)).insert(eo);
    }

    @Test
    public void shouldCallUpdateWhenEoNumberIsNotNull() throws ExchangeOrderNoContentException {
        ExchangeOrder eo = new ExchangeOrder();
        eo.setEoNumber("1234567890");

        service.saveExchangeOrder("sg",eo);

        verify(updateService, times(1)).update(eo);
    }

    @Test
    public void shouldCallGetExchangeOrder() {
        String eoNumber = "1806100005";
        service.getExchangeOrder(eoNumber);
        verify(repository, times(1)).getExchangeOrder(eoNumber);
    }

    @Test
    public void shouldCallSearch() {
        service.search(new ExchangeOrderSearchParam());
        verify(repository, times(1)).search(Mockito.any(ExchangeOrderSearchParam.class));
    }

    @Test
    public void shouldCallUpdate() {
        service.update(new ExchangeOrder());
        verify(repository, times(1)).updateFinance(Mockito.any(ExchangeOrder.class));
    }

    @Test
    public void shouldCallUpdateStatus() {
        String eoNumber = "1809100011";
        EoStatus status = EoStatus.PENDING;

        service.updateStatus(eoNumber, status);

        verify(repository, times(1)).updateStatus(eoNumber, status);
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
        service.getExchangeOrder("IN",recorLocator);
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
        service.getAllVMPDReasonCodes();
        verify(reasonCodeRepository, times(1)).getAll();
    }
    
    @Test
    public void shouldCallSaveVmpdReasonCode() {
    	service.saveVMPDReasonCode(new VmpdReasonCode());
        verify(reasonCodeRepository, times(1)).put(Mockito.any(VmpdReasonCode.class));
    }
    
    @Test
    public void shouldCallDeleteVmpd() {
    	String code = "A";
        service.deleteVmpdReasonCode(code);
        verify(reasonCodeRepository, times(1)).remove(code);
    }

}

