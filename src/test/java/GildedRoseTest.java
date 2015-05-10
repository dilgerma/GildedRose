import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GildedRoseTest {

    private static final String DEFAULT_ITEM_NAME = "item-1";

    @Test
    public void correctlyInitializesItems() {
        final GildedRose gildedRose = newGildedRose(new Item(DEFAULT_ITEM_NAME, 1, 1));
        assertEquals(1, gildedRose.getInventory().size());
    }

    @Test
    public void keepsItemsAfterDegrading() {
        final GildedRose gildedRose = newGildedRose(new Item(DEFAULT_ITEM_NAME, 1, 1));

        gildedRose.timePassed();
        assertEquals(1, gildedRose.getInventory().size());
    }

    @Test
    public void allowsToReadItemByName() {
        final Item item = new Item(DEFAULT_ITEM_NAME, 1, 1);
        final Item itemFromInventory = buildGildedRoseAndUpdateQuality(item);
        assertNotNull(itemFromInventory);
        assertEquals(item, itemFromInventory);
    }

    @Test
    public void degradesQualityIncreasesSellIn() {
        final int sellIn = 1;
        final int quality = 1;
        final Item defaultItem = new Item(DEFAULT_ITEM_NAME, sellIn, quality);
        final GildedRose gildedRose = newGildedRose(defaultItem);
        gildedRose.timePassed();
        Item itemFromInventory = gildedRose.getInventoryItem(DEFAULT_ITEM_NAME);
        assertEquals(quality - 1, itemFromInventory.getQuality());
        assertEquals(sellIn - 1, itemFromInventory.getSellIn());
    }

    @Test
    public void qualityNeverDegradesBelowZero() {
        GildedRose gildedRose = newGildedRose(new Item(DEFAULT_ITEM_NAME, 5, 1));
        gildedRose.timePassed();
        gildedRose.timePassed();

        Item itemFromInventory = gildedRose.getInventoryItem(DEFAULT_ITEM_NAME);
        assertEquals(0, itemFromInventory.getQuality());
    }


    @Test
    public void brieGetsBetterWithAge() {
        GildedRose gildedRose = new GildedRose(Arrays.asList(new Item(GildedRose.AGED_BRIE, 5, 5)));
        gildedRose.timePassed();
        ;
        final Item inventoryItem = gildedRose.getInventoryItem(GildedRose.AGED_BRIE);
        assertEquals(inventoryItem.getSellIn(), 4);
        assertEquals(inventoryItem.getQuality(), 6);
    }

    @Test
    public void maxQualityOfBrieIs50() {
        GildedRose gildedRose = new GildedRose(Arrays.asList(new Item(GildedRose.AGED_BRIE, 5, 49)));
        gildedRose.timePassed();
        ;
        gildedRose.timePassed();
        ;
        final Item inventoryItem = gildedRose.getInventoryItem(GildedRose.AGED_BRIE);
        assertEquals(inventoryItem.getSellIn(), 3);
        assertEquals(inventoryItem.getQuality(), 50);
    }

    @Test
    public void sulfurasNeverDecreasesInQuality() {
        final Item testItem = new Item(GildedRose.SULFURAS, 5, 5);
        final Item inventoryItem = buildGildedRoseAndUpdateQuality(testItem);
        assertEquals(5, inventoryItem.getQuality());
        assertEquals(5, inventoryItem.getSellIn());
    }

    @Test
    public void agedBrieQualityNeverIncreasesToMoreThan50() {
        final Item testItem = new Item(GildedRose.AGED_BRIE, 0, 50);
        final Item agedBrieInventory = buildGildedRoseAndUpdateQuality(testItem);
        assertEquals(-1, agedBrieInventory.getSellIn());
        assertEquals(50, agedBrieInventory.getQuality());
    }

    @Test
    public void agedBrieIncreasesTwiceAsFastInQualityEventWhenSellInDateReached() {
        final Item testItem = new Item(GildedRose.AGED_BRIE, 0, 1);
        final Item agedBrieInventory = buildGildedRoseAndUpdateQuality(testItem);
        assertEquals(3, agedBrieInventory.getQuality());
    }

    @Test
    public void qualityDegradesTwiceAsFastWhenSelloutDateReached() {
        final Item testItem = new Item(DEFAULT_ITEM_NAME, 0, 50);

        GildedRose gildedRose = newGildedRose(testItem);
        gildedRose.timePassed();
        final Item inventoryItem = gildedRose.getInventoryItem(testItem.getName());

        assertEquals(48, inventoryItem.getQuality());
        assertEquals(-1, inventoryItem.getSellIn());

        gildedRose.timePassed();
        assertEquals(46, inventoryItem.getQuality());
        assertEquals(-2, inventoryItem.getSellIn());
    }

    @Test
    public void sulfurasQualityDoesNotDegradeWithNegativeSellIn() {
        final Item testItem = new Item(GildedRose.SULFURAS, -1, 5);
        final Item inventoryItem = buildGildedRoseAndUpdateQuality(testItem);
        assertEquals(-1, inventoryItem.getSellIn());
        assertEquals(5, inventoryItem.getQuality());
    }

    @Test
    public void backstagePassesLoseQualityWhenSelloutDateReached() {
        final Item inventoryItem = buildGildedRoseAndUpdateQuality(new Item(GildedRose.BACKSTAGE_PASSES, 0, 50));
        assertEquals(0, inventoryItem.getQuality());
        assertEquals(-1, inventoryItem.getSellIn());
    }

    @Test
    public void backstagePassesIncreaseTwiceAsFastFrom11DaysBeforeConcert() {
        final Item inventoryItem = buildGildedRoseAndUpdateQuality(new Item(GildedRose.BACKSTAGE_PASSES, 10, 2));
        assertEquals(4, inventoryItem.getQuality());
        assertEquals(9, inventoryItem.getSellIn());
    }

    @Test
    public void backstagePassesIncreaseTrippleAsFastFrom6DaysBeforeConcert() {
        final Item inventoryItem = buildGildedRoseAndUpdateQuality(new Item(GildedRose.BACKSTAGE_PASSES, 5, 2));
        assertEquals(5, inventoryItem.getQuality());
        assertEquals(4, inventoryItem.getSellIn());
    }

    @Test
    public void conjuredItem() {
        final Item conjuredItem = buildGildedRoseAndUpdateQuality(new Item(GildedRose.CONJURED_ITEM, 5, 5));
        assertEquals(3, conjuredItem.getQuality());
        assertEquals(4, conjuredItem.getSellIn());
    }


    private GildedRose newGildedRose(Item... item) {
        List<Item> items = Arrays.asList(item);
        return new GildedRose(items);
    }

    private Item buildGildedRoseAndUpdateQuality(Item testItem) {
        GildedRose gildedRose = newGildedRose(testItem);
        gildedRose.timePassed();
        return gildedRose.getInventoryItem(testItem.getName());
    }


}
