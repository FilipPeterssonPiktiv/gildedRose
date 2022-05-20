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
    void updateQuality_withSulfurasLegendaryItem_doesNotDegradeInQuality() {
        Item[] items = new Item[] {
            new Item("Sulfuras, Hand of Ragnaros", 0, 50)
        };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(50, app.items[0].quality);
    }

    @Test
    void updateQuality_withSulfurasLegendaryItem_doesNotNeedToBeSold() {
        Item[] items = new Item[] {
            new Item("Sulfuras, Hand of Ragnaros", 0, 50)
        };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(0, app.items[0].sellIn);
    }
    @Test
    void updateQuality_withSulfurasLegendaryItem_shouldUpdateOtherItems() {
        Item[] items = new Item[] {
            new Item("Sulfuras, Hand of Ragnaros", 0, 0),
            new Item("foo", 10, 100)
        };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(9, app.items[1].sellIn);
    }

    @Test
    void updateQuality_withBrie_shouldIncreaseInQualityAsItGetsOlder() {
        Item[] items = new Item[] {
            new Item("Aged Brie", 10, 20),
        };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(21, app.items[0].quality);
    }

    @Test
    void updateQuality_withBrie_shouldIncreaseInQualityTwiceAsFastWhenItsExpired() {
        Item[] items = new Item[] {
            new Item("Aged Brie", 0, 20),
            new Item("Aged Brie", 1, 20),
        };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(22, app.items[0].quality);
        assertEquals(21, app.items[1].quality);
    }

    @Test
    void updateQuality_withBrie_shouldBeCappedAt50Quality() {
        Item[] items = new Item[] {
            new Item("Aged Brie", 0, 50),
        };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(50, app.items[0].quality);
    }

    @Test
    void updateQuality_withBrie_shouldDecreaseSellIn() {
        Item[] items = new Item[] {
            new Item("Aged Brie", 4, 50),
        };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(3, app.items[0].sellIn);
    }

    @Test
    void updateQuality_withPass_shouldIncreaseInQuality() {
        Item[] items = new Item[] {
            new Item("Backstage passes to a TAFKAL80ETC concert", 20, 20),
        };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(21, app.items[0].quality);
    }

    @Test
    void updateQuality_withPass_shouldIncreaseInQualityCloseToEvent() {
        Item[] items = new Item[] {
            new Item("Backstage passes to a TAFKAL80ETC concert", 10, 20),
        };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(22, app.items[0].quality);
    }

    @Test
    void updateQuality_withPass_shouldIncreaseInQualityReallyCloseToEvent() {
        Item[] items = new Item[] {
            new Item("Backstage passes to a TAFKAL80ETC concert", 5, 20),
            new Item("Backstage passes to a TAFKAL80ETC concert", 1, 30),
        };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(23, app.items[0].quality);
        assertEquals(33, app.items[1].quality);
    }

    @Test
    void updateQuality_withPass_shouldSetQualityToZeroWhenExpired() {
        Item[] items = new Item[] {
            new Item("Backstage passes to a TAFKAL80ETC concert", 0, 20),
        };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(0, app.items[0].quality);
    }

    @Test
    void updateQuality_withPass_shouldBeCappedAt50Quality() {
        Item[] items = new Item[] {
            new Item("Backstage passes to a TAFKAL80ETC concert", 20, 50),
        };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(50, app.items[0].quality);
    }

    @Test
    void updateQuality_withRegularItem_shouldDecreaseQuality() {
        Item[] items = new Item[] {
            new Item("regular", 20, 20),
        };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(19, app.items[0].quality);
    }

    @Test
    void updateQuality_withExpiredRegularItem_shouldDegradeQualityAtTwiceTheRate() {
        Item[] items = new Item[] {
            new Item("regular", -1, 20),
        };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(18, app.items[0].quality);
    }

    @Test
    void updateQuality_withRegularItem_shouldNeverHaveNegativeQuality() {
        Item[] items = new Item[] {
            new Item("regular", 20, 0),
        };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(0, app.items[0].quality);
    }

    @Test
    void updateQuality_withConjuredItem_shouldDecreaseQualityTwiceAsFast() {
        Item[] items = new Item[] {
            new Item("Conjured", 20, 40),
        };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(38, app.items[0].quality);
    }

    @Test
    void updateQuality_withExpiredConjuredItem_shouldDecreaseQualityTwiceAsFast() {
        Item[] items = new Item[] {
            new Item("Conjured", 0, 40),
        };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(36, app.items[0].quality);
    }
}
