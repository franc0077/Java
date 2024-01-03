package hr.javafx.model.remenar7.modell;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Predstavlja entitet za artikl
 */
public abstract class Item extends NamedEntity implements Serializable {

    private Category category;
    private BigDecimal width;
    private BigDecimal height;
    private BigDecimal length;
    private BigDecimal productionCost;
    private BigDecimal sellingPrice;
    private BigDecimal discountAmount;


    /**
     * Inicijalizira sljedeće podatke
     * @param id id
     * @param name ime artikla
     * @param category kategoriju artikla
     * @param width širinu artikla
     * @param height visinu artikla
     * @param length dužinu artikla
     * @param productionCost cijenu proizvodnje artikla
     * @param sellingPrice prodajnu cijenu artikla
     * @param discountAmount iznos popusta
     */
    public Item(long id, String name, Category category, BigDecimal width, BigDecimal height, BigDecimal length, BigDecimal productionCost, BigDecimal sellingPrice, BigDecimal discountAmount) {
        super(id, name);
        this.category = category;
        this.width = width;
        this.height = height;
        this.length = length;
        this.productionCost = productionCost;
        this.sellingPrice = sellingPrice;
        this.discountAmount = discountAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(getCategory(), item.getCategory()) && Objects.equals(getWidth(), item.getWidth()) && Objects.equals(getHeight(), item.getHeight()) && Objects.equals(getLength(), item.getLength()) && Objects.equals(getProductionCost(), item.getProductionCost()) && Objects.equals(getSellingPrice(), item.getSellingPrice()) && Objects.equals(getDiscountAmount(), item.getDiscountAmount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCategory(), getWidth(), getHeight(), getLength(), getProductionCost(), getSellingPrice(), getDiscountAmount());
    }

    @Override
    public String toString() {
        return "Item{" +
                "name = " + getName() +
                '}';
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    public BigDecimal getProductionCost() {
        return productionCost;
    }

    public void setProductionCost(BigDecimal productionCost) {
        this.productionCost = productionCost;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }
}
