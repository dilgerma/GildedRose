/**
 *
 */
public class ConjuredItem extends Item {
    public ConjuredItem(String name, int sellIn, int quality) {
        super(name, sellIn, quality);
    }

    @Override
    protected int defaultIncrement() {
        return super.defaultIncrement() * 2;
    }
}
