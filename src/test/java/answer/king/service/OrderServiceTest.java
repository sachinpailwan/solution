package answer.king.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import answer.king.exception.InsufficientFundPaymentException;
import answer.king.model.Item;
import answer.king.model.LineItem;
import answer.king.model.Order;
import answer.king.model.Receipt;
import answer.king.repo.ItemRepository;
import answer.king.repo.LineItemRepository;
import answer.king.repo.OrderRepository;
import answer.king.repo.ReceiptRepository;

@RunWith(SpringJUnit4ClassRunner.class)
public class OrderServiceTest {

	private final Long ORDER_ID=1L;
	private final Long ITEM_ID=1L;	
	private final Long LINEITEM_ID=1L;	
	private final Long LINEITEM_ID_2=2L;	

	@Mock
	private OrderRepository orderRepository;
	
	@Mock
	private ReceiptRepository receiptRepository;
	
	@Mock
	private ItemRepository itemRepository;
	
	
	
	private Order order;
	private Item item;
	private LineItem lineItem;
	
	@InjectMocks
	private OrderService orderService;
	
	@InjectMocks 
	private ItemService ItemService;

	@Before
	public void setup(){
		
		item = new Item();
		item.setId(ITEM_ID);
		item.setName("item1");
		item.setPrice(new BigDecimal(100));
		
		order = new Order();
		order.setId(ORDER_ID);
		order.setItems(new ArrayList<>());
		
	}
	
	@Test
	public void Pay() throws InsufficientFundPaymentException{
		lineItem = new LineItem();
		lineItem.setId(LINEITEM_ID);
		lineItem.setItem(item);
		List<LineItem> items = new ArrayList<>();
		items.add(lineItem);
		order.setItems(items);
		Mockito.when(orderRepository.findOne(order.getId())).thenReturn(order);
		
		Receipt receipt = orderService.pay(order.getId(), new BigDecimal(100));
		
		Mockito.verify(orderRepository).findOne(order.getId());
		Mockito.verify(receiptRepository).save(receipt);
		Assert.assertTrue(receipt.getOrder().getId()==order.getId());
		Assert.assertTrue(receipt.getOrder().getPaid());
		
	}
	
	@Test(expected=InsufficientFundPaymentException.class)
	public void PayWithInvalidPayment() throws InsufficientFundPaymentException{
		
		lineItem = new LineItem();
		lineItem.setId(LINEITEM_ID);
		lineItem.setItem(item);
		
		Item item2 = new Item();
		item2.setId(1L);
		item2.setName("item1");
		item2.setPrice(new BigDecimal(100));
		
		LineItem lineItem2 = new LineItem();
		lineItem2.setId(LINEITEM_ID);
		lineItem2.setItem(item2);
		
		List<LineItem> items = new ArrayList<>();
		items.add(lineItem);
		items.add(lineItem2);
		order.setItems(items);
		
		Mockito.when(orderRepository.findOne(ORDER_ID)).thenReturn(order);
		
		Receipt receipt = orderService.pay(ORDER_ID, new BigDecimal(100));
		
		Mockito.verify(orderRepository).findOne(ORDER_ID);
		Assert.assertTrue(receipt.getOrder().getId()==ORDER_ID);
		Assert.assertFalse(order.getPaid());
		
	}
	
	@Test
	public void PayWithPriceChange() throws InsufficientFundPaymentException{
		
		Mockito.when(orderRepository.findOne(ORDER_ID)).thenReturn(order);
		Mockito.when(itemRepository.findOne(ITEM_ID)).thenReturn(item);
		LineItem lineItem = new LineItem();
		lineItem.setId(LINEITEM_ID);
		lineItem.setItem(item);
		
		
		orderService.addItem(order.getId(), item.getId(),1l);
		ItemService.updatePrice(item.getId(), new BigDecimal(200));
		
		Receipt receipt = orderService.pay(order.getId(), new BigDecimal(100));
	}
	
	@Test(expected=InsufficientFundPaymentException.class)
	public void PayWithPriceChangeAndQuantityFailed() throws InsufficientFundPaymentException{
		
		Mockito.when(orderRepository.findOne(ORDER_ID)).thenReturn(order);
		Mockito.when(itemRepository.findOne(ITEM_ID)).thenReturn(item);
		LineItem lineItem = new LineItem();
		lineItem.setId(LINEITEM_ID);
		lineItem.setItem(item);
		
		
		orderService.addItem(order.getId(), item.getId(),2l);
		ItemService.updatePrice(item.getId(), new BigDecimal(200));
		
		Receipt receipt = orderService.pay(order.getId(), new BigDecimal(100));
	}
	
	@Test
	public void PayWithPriceChangeAndQuantitySuccess() throws InsufficientFundPaymentException{
		
		Mockito.when(orderRepository.findOne(ORDER_ID)).thenReturn(order);
		Mockito.when(itemRepository.findOne(ITEM_ID)).thenReturn(item);
		LineItem lineItem = new LineItem();
		lineItem.setId(LINEITEM_ID);
		lineItem.setItem(item);
		
		orderService.addItem(order.getId(), item.getId(),2l);
		ItemService.updatePrice(item.getId(), new BigDecimal(400));
		
		Receipt receipt = orderService.pay(order.getId(), new BigDecimal(200));
		Assert.assertTrue(receipt.getOrder().getPaid());
	}
	
	@Test
	public void addItemDuplicate(){
		
		Mockito.when(orderRepository.findOne(ORDER_ID)).thenReturn(order);
		Mockito.when(itemRepository.findOne(ITEM_ID)).thenReturn(item);
		
		orderService.addItem(ORDER_ID, ITEM_ID, 2L);
		orderService.addItem(ORDER_ID, ITEM_ID, 2L);
		Assert.assertTrue(order.getItems().get(0).getQuantiy() == 4L);
		
	}

}
