package answer.king.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import answer.king.model.Receipt;
import answer.king.service.ReceiptService;

@RestController
@RequestMapping("/receipt")
public class ReceiptController {

	@Autowired
	private ReceiptService receiptService;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Receipt getReceipt(@PathVariable("id") Long id){
		return receiptService.findone(id);
	}
}
