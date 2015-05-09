import java.util.ArrayList;
import java.util.List;


public class GildedRose {

    private static GildedRose gildedRose;

    private List<Item> items;

    /**
     * @param args
     */
    public static void main(String[] args) {

        System.out.println("OMGHAI!");
        List<Item> items = new ArrayList<Item>();
        items.add(new Item("+5 Dexterity Vest", 10, 20));
        items.add(new AgedBrieItem(2, 0));
        items.add(new Item("Elixir of the Mongoose", 5, 7));
        items.add(new SulfurasItem());
        items.add(new BackstagePassesItem("Backstage passes to a TAFKAL80ETC concert",15, 20));
        items.add(new Item("Conjured Mana Cake", 3, 6));

        gildedRose = new GildedRose(items);
        gildedRose.timePassed();
    }

    public GildedRose(List<Item> items) {
        this.items = items;
    }

    public void timePassed() {
        items.forEach(item -> item.timePassed());
    }

    public Item getInventoryItem(String name) {
        return items.stream().filter(item -> item.getName().equals(name)).findFirst().get();
    }

    public List<Item> getInventory() {
        return items;
    }

}