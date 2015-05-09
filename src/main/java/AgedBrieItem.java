/**
 *
 */
public class AgedBrieItem extends Item {
    public static final String AGED_BRIE = "Aged Brie";

    public AgedBrieItem(int sellIn, int quality) {
        super(AGED_BRIE, sellIn, quality);
    }

    @Override
    public void handleQuality() {
        int increment = this.isOverdue() ? defaultIncrement() * 2 : defaultIncrement();
        this.setQuality(this.getQuality() + increment);
    }


}
