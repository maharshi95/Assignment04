package com.eMart.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * Created by maharshigor on 08/07/16.
 */

@Entity
@Table(name = "Product")
public class Product {

	@Id
	@Column(name = "product_id")
	@JsonProperty(value = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long productID;

	@Column(name = "category_id")
	private long categoryID;

	@JsonProperty(value = "code")
	@Column(name = "product_code")
	private String productCode;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "quantity")
	private long quantity;

	@Column(name = "buy_price")
	private double buyPrice;

	@Column(name = "sell_price")
	private double sellPrice;

	@Column(name = "deleted", nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean deleted;

	public Product(long categoryID, String productCode, String name, String description, long quantity, double buyPrice, double sell_price) {
		this.categoryID = categoryID;
		this.productCode = productCode;
		this.name = name;
		this.description = description;
		this.quantity = quantity;
		this.buyPrice = buyPrice;
		this.sellPrice = sell_price;
	}

	public Product() { }

	public long getProductID() {
		return productID;
	}

	public void setProductID(long productID) {
		this.productID = productID;
	}

	public long getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(long categoryID) {
		this.categoryID = categoryID;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public double getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(double buyPrice) {
		this.buyPrice = buyPrice;
	}

	public double getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(double sellPrice) {
		this.sellPrice = sellPrice;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "Product{" +
				"productID=" + productID +
				", categoryID=" + categoryID +
				", productCode='" + productCode + '\'' +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				", quantity=" + quantity +
				", buyPrice=" + buyPrice +
				", sellPrice=" + sellPrice +
				'}';
	}
}
