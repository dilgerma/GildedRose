
public class Item {
    public String name;
    public int sellIn;
    public int quality;

    public Item(String name, int sellIn, int quality) {
        this.name = name;
        this.sellIn = sellIn;
        this.quality = quality;
    }

    /* Generated getter and setter code */
    public String getName() {
        return name;
    }

    public int getSellIn() {
        return sellIn;
    }

    public int getQuality() {
        return quality;
    }

    final void setQuality(int quality) {
        if (quality <= 50)
            this.quality = quality;
    }

    public void handleSellIn() {
        this.sellIn--;
    }

    public void handleQuality() {
        if (quality > 0) {
            int decrement = this.isOverdue() ? defaultIncrement() * 2 : defaultIncrement();
            this.setQuality(this.getQuality() - decrement);
        }
    }

    public boolean isOverdue() {
        return this.sellIn <= 0;
    }

    public void timePassed() {
        this.handleQuality();
        this.handleSellIn();
    }

    protected int defaultIncrement() {
        return 1;
    }
}
