package co.uk.henry.model;

public class Item {

    private final String code;
    private final String name;
    private final Unit unit;
    private final Double price;

    public Item(
            final String code, final String name, final Unit unit, final Double price
    ) {
        this.code = code;
        this.name = name;
        this.unit = unit;
        this.price = price;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Unit getUnit() {
        return unit;
    }

    public Double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;

        Item item = (Item) o;

        if (!code.equals(item.code)) return false;
        if (!name.equals(item.name)) return false;
        if (unit != item.unit) return false;
        return price.equals(item.price);
    }

    @Override
    public int hashCode() {
        int result = code.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + unit.hashCode();
        result = 31 * result + price.hashCode();
        return result;
    }
}
