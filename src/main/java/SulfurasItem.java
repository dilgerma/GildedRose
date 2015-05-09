/**
 *
 */
public class SulfurasItem extends Item {

    public static final String SULFURAS = "Sulfuras, Hand of Ragnaros";

    public SulfurasItem() {
        super(SulfurasItem.SULFURAS, 0, 80);
    }

    @Override
    public void handleQuality() {
        //nothing.
    }

    @Override
    public void handleSellIn() {
        //nothing
    }
}
