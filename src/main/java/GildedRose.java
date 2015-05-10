import java.util.ArrayList;
import java.util.List;


public class GildedRose {

    private static GildedRose gildedRose;

    private List<Item> items;
    public static final String AGED_BRIE = "Aged Brie";
    public static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
    public static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    public static final String CONJURED_ITEM = "Conjured";

    /**
     * @param args
     */
    public static void main(String[] args) {

        System.out.println("OMGHAI!");
        List<Item> items = new ArrayList<Item>();
        items.add(new Item("+5 Dexterity Vest", 10, 20));
        items.add(new Item("Aged Brie", 2, 0));
        items.add(new Item("Elixir of the Mongoose", 5, 7));
        items.add(new Item("Sulfuras, Hand of Ragnaros", 0, 80));
        items.add(new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20));
        items.add(new Item("Conjured Mana Cake", 3, 6));

        gildedRose = new GildedRose(items);
        gildedRose.timePassed();
    }

    public GildedRose(List<Item> items) {
        this.items = items;
    }

    public void timePassed() {
        for (int i = 0; i < items.size(); i++) {
            final Item item = items.get(i);
            handleQuality(item);
            handleSellIn(item);
        }
    }

    private void handleItemOverdue(Item item) {
        if (!AGED_BRIE.equals(item.getName())) {
            if (!BACKSTAGE_PASSES.equals(item.getName())) {
                decreaseQuality(item);
            } else {
                resetQuality(item);
            }
        } else {
            increaseQuality(item);
        }
    }

    private void decreaseQuality(Item item) {
        int decrement = CONJURED_ITEM.equals(item.getName()) ? 2 : 1;
        if (item.getQuality() > 0) {
            if (!SULFURAS.equals(item.getName())) {
                item.setQuality(item.getQuality() - decrement);
            }
        }
    }

    private void increaseQuality(Item item) {
        if (item.getQuality() < 50) {
            item.setQuality(item.getQuality() + 1);
        }
    }

    private void resetQuality(Item item) {
        item.setQuality(item.getQuality() - item.getQuality());
    }

    private void handleSellIn(Item item) {
        if (!SULFURAS.equals(item.getName())) {
            item.setSellIn(item.getSellIn() - 1);
        }

        if (item.getSellIn() < 0) {
            handleItemOverdue(item);
        }
    }

    private void handleQuality(Item item) {
        if (defaultQualityHandling(item)) {
            decreaseQuality(item);
        } else {
            handleBrieAndBackstagePasses(item);
        }
    }

    private boolean defaultQualityHandling(Item brieOrBackstagePass) {
        return (!AGED_BRIE.equals(brieOrBackstagePass.getName())) && !BACKSTAGE_PASSES.equals(brieOrBackstagePass.getName());
    }

    private void handleBrieAndBackstagePasses(Item item) {
        increaseQuality(item);

        if (BACKSTAGE_PASSES.equals(item.getName())) {
            if (item.getSellIn() < 11) {
                increaseQuality(item);
            }

            if (item.getSellIn() < 6) {
                increaseQuality(item);
            }
        }
    }

    public Item getInventoryItem(String name) {
        return items.stream().filter(item -> item.getName().equals(name)).findFirst().get();
    }

    public List<Item> getInventory() {
        return items;
    }

}