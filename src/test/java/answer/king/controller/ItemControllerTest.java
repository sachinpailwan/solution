package answer.king.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import answer.king.exception.InvalidIItemException;
import answer.king.model.Item;
import answer.king.service.ItemService;

@RunWith(SpringJUnit4ClassRunner.class)
public class ItemControllerTest {

	@InjectMocks
	private ItemController itemController;
	
	@Mock
	private ItemService itemService;
	
	
	private Item item;
	private Map<String, BigDecimal> itemPriceMap;
	
	
	@Before
	public void setup(){
		
		item = new Item();
		item.setId(1L);
		item.setName("item1");
		item.setPrice(new BigDecimal(100));
		itemPriceMap = new HashMap<>();
		itemPriceMap.put("item1", new BigDecimal(100));
		ReflectionTestUtils.setField(itemController, "itemPriceMap", itemPriceMap);
	}
	
	@Test
	public void getAll() {
		
		itemController.getAll();
		
		Mockito.verify(itemService).getAll();
	}
	
	@Test
	public void create() throws InvalidIItemException{
		
		itemController.create(item);
		
		Mockito.verify(itemService).save(item);
	}
	
	@Test
	public void createWithValidItem() throws InvalidIItemException{
		
		itemController.create(item);
		
		Mockito.verify(itemService).save(item);
	}
	
	@Test(expected=InvalidIItemException.class)
	public void createWithInValidItem() throws InvalidIItemException{
		
		item = new Item();
		item.setId(1L);
		item.setName("item1");
		item.setPrice(new BigDecimal(-1));
		
		itemController.create(item);		
	}
	
	@Test
	public void updatePrice() {
		BigDecimal price = new BigDecimal(200);
		Item item2 = itemController.updatePrice(item.getId(),price);
		
		Mockito.verify(itemService).updatePrice(item.getId(), price);
		
	}

}
