package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    @Test
    void should_store_item() {
        Item[] items = new Item[] { new Item("foo", 0, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals("foo", app.items[0].name);
    }

    @Test
    void should_store_items() {
        Item[] items = new Item[] {
            new Item("foo", 0, 0),
            new Item("bar", 0, 0)
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals("bar", app.items[1].name);
    }

    @Test
    void updateQuality_withSulfurasLegenderyItem_doesNotDegradeInQuality() {
        Item[] items = new Item[] {
            new Item("Sulfuras, Hand of Ragnaros", 0, 100)
        };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(100, app.items[0].quality);
    }

    @Test
    void updateQuality_withSulfurasLegenderyItem_doesNotNeedToBeSold() {
        Item[] items = new Item[] {
            new Item("Sulfuras, Hand of Ragnaros", 0, 0)
        };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(0, app.items[0].sellIn);
    }

}
