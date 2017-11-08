package answer.king.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import answer.king.exception.InsufficientFundPaymentException;
import answer.king.model.Item;
import answer.king.model.LineItem;
import answer.king.model.Order;
import answer.king.model.Receipt;
import answer.king.repo.ItemRepository;
import answer.king.repo.LineItemRepository;
import answer.king.repo.OrderRepository;
import answer.king.repo.ReceiptRepository;

@Service
@Transactional(noRollbackFor = Exception.class)
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ItemRepository itemRepository;


	@Autowired
	private ReceiptRepository receiptRepository;

	public List<Order> getAll() {
		return orderRepository.findAll();
	}

	public Order save(Order order) {
		return orderRepository.save(order);
	}

	public Order addItem(Long id, Long itemId, Long qty) {
		Order order = orderRepository.findOne(id);
		Item item = itemRepository.findOne(itemId);
		addLineItem(qty, order, item);
		return order;
	}

	private void addLineItem(Long qty, Order order, Item item) {

		LineItem exitLineItem = order.getItems().stream().filter(x -> x.getItem().equals(item)).findAny().orElse(null);
		if (exitLineItem != null) {
			exitLineItem.setQuantiy(exitLineItem.getQuantiy() + qty);
		} else {
			LineItem lineItem = new LineItem();
			lineItem.setItem(item);
			lineItem.setPrice(item.getPrice());
			lineItem.setQuantiy(qty);
			lineItem.setOrder(order);
			order.getItems().add(lineItem);
			orderRepository.save(order);
		}
	}

	public Receipt pay(Long id, BigDecimal payment) throws InsufficientFundPaymentException {
		Order order = orderRepository.findOne(id);
		validatePayment(order, payment);
		Receipt receipt = new Receipt();
		receipt.setPayment(payment);
		receipt.setOrder(order);
		order.setPaid(true);
		receiptRepository.save(receipt);
		return receipt;
	}

	private void validatePayment(Order order, BigDecimal payment) throws InsufficientFundPaymentException {

		BigDecimal total = order.getItems().stream().map(x -> x.getPrice().multiply(new BigDecimal(x.getQuantiy())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		if (total.compareTo(payment) != 0)
			throw new InsufficientFundPaymentException(
					String.format(InsufficientFundPaymentException.INSUFFICIENT_PAYMENT_ERROR, total, payment));
	}
}
