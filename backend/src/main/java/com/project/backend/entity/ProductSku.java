package com.project.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products_skus")
public class ProductSku {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "size_attribute_id")
    private ProductAttribute sizeAttribute;

    @ManyToOne
    @JoinColumn(name = "color_attribute_id")
    private ProductAttribute colorAttribute;

    @Column(name = "sku")
    private String sku;

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Column(name = "deleted_at")
    private LocalDateTime deleted_at;

    public ProductSku() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public ProductAttribute getSizeAttribute() { return sizeAttribute; }
    public void setSizeAttribute(ProductAttribute sizeAttribute) { this.sizeAttribute = sizeAttribute; }

    public ProductAttribute getColorAttribute() { return colorAttribute; }
    public void setColorAttribute(ProductAttribute colorAttribute) { this.colorAttribute = colorAttribute; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public LocalDateTime getCreated_at() { return created_at; }
    public void setCreated_at(LocalDateTime created_at) { this.created_at = created_at; }

    public LocalDateTime getDeleted_at() { return deleted_at; }
    public void setDeleted_at(LocalDateTime deleted_at) { this.deleted_at = deleted_at; }
}

