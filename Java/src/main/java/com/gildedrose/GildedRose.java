package com.gildedrose;

import com.sun.org.apache.bcel.internal.generic.BREAKPOINT;

class GildedRose {
    private static final String AGED_BRIE = "Aged Brie";
    private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    private static final String BACKSTAGE_PASS = "Backstage passes to a TAFKAL80ETC concert";

    private static final int MAX_DEFAULT_QUALITY = 50;
    private static final int SULFUR_QUALITY = 80;
    private static final int BACKSTAGE_PASS_HIGH_QUALITY_DAY = 5;
    private static final int DEFAULT_QUALITY_DECREASE = 1;
    private static final int DEFAULT_EXPIRED_QUALITY_DECREASE = DEFAULT_QUALITY_DECREASE * 2;

    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            updateItemQuality(item);
        }
    }

    private void updateItemQuality(Item item) {
        final boolean isExpired = item.sellIn < 0;

        if (!isSpecialItem(item) && item.quality > 0) {
            final int decreaseQualityAmount = isExpired ? DEFAULT_EXPIRED_QUALITY_DECREASE : DEFAULT_QUALITY_DECREASE;
            item.quality -= decreaseQualityAmount;
            return;
        }
        else if (item.quality < MAX_DEFAULT_QUALITY) {
            item.quality = item.quality + 1;

            if (isItem(item, BACKSTAGE_PASS)) {
                updateBackstagePass(item);
            }
        }

        if (!isItem(item, SULFURAS)) {
            item.sellIn = item.sellIn - 1;
        }

        if (item.sellIn >= 0) {
            return;
        }

        if (!isItem(item, AGED_BRIE)) {
            if (!isItem(item, BACKSTAGE_PASS) && item.quality > 0 && !isItem(item, SULFURAS)) {
                item.quality = item.quality - 1;
            } else {
                item.quality = 0;
            }
        } else if (item.quality < MAX_DEFAULT_QUALITY) {
            item.quality = item.quality + 1;
        }
    }

    private void updateBackstagePass(Item item) {
        if (item.sellIn < 11 && item.quality < MAX_DEFAULT_QUALITY) {
            item.quality = item.quality + 1;
        }

        if (item.sellIn <= BACKSTAGE_PASS_HIGH_QUALITY_DAY && item.quality < 50) {
            item.quality = item.quality + 1;
        }
    }

    private boolean isSpecialItem(final Item item) {
        return isItem(item, AGED_BRIE) || isItem(item, SULFURAS) || isItem(item, BACKSTAGE_PASS);
    }

    private boolean isItem(final Item item, final String itemName) {
        return item.name.equals(itemName);
    }
}
