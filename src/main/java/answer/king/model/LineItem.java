package answer.king.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "T_LINEITEM")
public class LineItem {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)	
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_ID")
	private Item item;
	
	@ManyToOne 
	@JsonIgnore
	@JoinColumn(name="ORDER_ID", nullable=false)
	private Order order;

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	private BigDecimal price;
	
	private Long quantiy=1L;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
		this.setPrice(item.getPrice());
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Long getQuantiy() {
		return quantiy;
	}

	public void setQuantiy(Long quantiy) {
		this.quantiy = quantiy;
	}
	
	
	
}
