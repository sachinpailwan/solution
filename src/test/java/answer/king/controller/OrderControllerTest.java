package answer.king.controller;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import answer.king.exception.InsufficientFundPaymentException;
import answer.king.model.Receipt;
import answer.king.service.OrderService;



@RunWith(SpringJUnit4ClassRunner.class)
public class OrderControllerTest {

	private static final BigDecimal PAYMENT = new BigDecimal(100);
	private static final long ORDER_ID = 1L;
	@Mock
	private OrderService orderService;
	@Mock
	private Receipt receipt;	
	@InjectMocks
	private OrderController orderController;
	
	@Test
	public void Pay() throws InsufficientFundPaymentException{
		
		Mockito.when(orderService.pay(ORDER_ID, PAYMENT)).thenReturn(receipt);
		
		Receipt receipt = orderController.pay(1L, PAYMENT);
		
		Mockito.verify(orderService).pay(1L, PAYMENT);
	}

}
