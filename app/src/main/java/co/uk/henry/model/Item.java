package co.uk.henry.model;

public class Item {

    private final String code;
    private final String name;
    private final Unit unit;
    private final double price;

    public Item(
            final String code, final String name, final Unit unit, final double price
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

    public double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;

        Item item = (Item) o;

        if (Double.compare(item.price, price) != 0) return false;
        if (!code.equals(item.code)) return false;
        if (!name.equals(item.name)) return false;
        return unit == item.unit;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = code.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + unit.hashCode();
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
