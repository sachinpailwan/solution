package answer.king.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import answer.king.exception.InsufficientFundPaymentException;
import answer.king.exception.InvalidIItemException;
import answer.king.model.Item;
import answer.king.model.Receipt;
import answer.king.service.ItemService;

@RestController
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;
	private Map<String, BigDecimal> itemPriceMap = new HashMap<>();

	@RequestMapping(method = RequestMethod.GET)
	public List<Item> getAll() {
		return itemService.getAll();
	}

	@RequestMapping(method = RequestMethod.POST)
	public Item create(@RequestBody Item item) throws InvalidIItemException {
		return itemService.save(validate(item));
	}
	
	@RequestMapping(value = "/{id}/updateprice", method = RequestMethod.POST)
	public Item updatePrice(@PathVariable("id") Long id, @RequestBody BigDecimal price){
		return itemService.updatePrice(id, price);
	}

	private Item validate(Item item) throws InvalidIItemException {
		if (itemPriceMap.containsKey(item.getName()) && itemPriceMap.get(item.getName()).compareTo(item.getPrice())!=0)
			throw new InvalidIItemException(
					String.format(InvalidIItemException.INVALID_ITEM, item.getName(), item.getPrice()));
		return item;
	}
}
