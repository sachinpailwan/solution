package answer.king.controller;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import answer.king.model.Receipt;
import answer.king.service.ReceiptService;

@RunWith(SpringJUnit4ClassRunner.class)
public class ReceiptControllerTest {

	@Mock
	private ReceiptService receiptService;
	@Mock
	private Receipt receipt;
	
	@InjectMocks
	private ReceiptController receiptController;
	@Test
	public void getReceipt() {
		Mockito.when(receiptService.findone(1L)).thenReturn(receipt);
		
		receiptController.getReceipt(1L);
		
		Mockito.verify(receiptService).findone(1L);
	}

}
