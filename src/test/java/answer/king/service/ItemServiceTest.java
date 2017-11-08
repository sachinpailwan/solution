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

import answer.king.model.Item;
import answer.king.repo.ItemRepository;

@RunWith(SpringJUnit4ClassRunner.class)
public class ItemServiceTest {

	@InjectMocks
	private ItemService itemService;
	
	@Mock
	private ItemRepository itemRepository;
	
	private Item item;
	
	@Before
	public void setup(){
		item = new Item();
		item.setId(1l);
		item.setName("unitItem1");
		item.setPrice(new BigDecimal(100));
	}
	
	@Test
	public void getAll() {
		List<Item> list = new ArrayList<>();
		list.add(item);
		Mockito.when(itemRepository.findAll()).thenReturn(list);
		
		List<Item> itemList = itemService.getAll();
		
		Mockito.verify(itemRepository).findAll();
		Assert.assertTrue(itemList.size()==1);
	}
	
	public void save(){
		Mockito.when(itemRepository.save(item)).thenReturn(item);
		
		Item savedItem = itemService.save(item);
	
		Mockito.verify(itemRepository).save(item);
		Assert.assertTrue(savedItem.getId()==item.getId());
	}
	
	public void updatePrice(){
		Mockito.when(itemRepository.findOne(item.getId())).thenReturn(item);
		
		Item savedItem = itemService.updatePrice(item.getId(), new BigDecimal(200));
	
		Mockito.verify(itemRepository).findOne(item.getId());
		Assert.assertTrue(savedItem.getPrice().compareTo(new BigDecimal(200))==0);
	}

}
