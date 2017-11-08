package answer.king.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import answer.king.model.Receipt;
import answer.king.repo.ReceiptRepository;

@RunWith(SpringJUnit4ClassRunner.class)
public class ReceiptServiceTest {

	@Mock
	private Receipt receipt;
	
	@Mock
	private ReceiptRepository receiptRepository;
	
	@InjectMocks
	private ReceiptService receiptService;
	
	@Test
	public void getReceipt() {
		Mockito.when(receiptRepository.findOne(1L)).thenReturn(receipt);
		
		receiptService.findone(1L);
		
		Mockito.verify(receiptRepository).findOne(1L);
	}

}
