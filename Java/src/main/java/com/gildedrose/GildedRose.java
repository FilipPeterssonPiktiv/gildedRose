package com.gildedrose;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            final boolean expired = item.sellIn <= 0;
            switch (item.name) {
                case "Sulfuras, Hand of Ragnaros":
                    continue;
                case "Aged Brie":
                    if (item.quality < 50) {
                        item.quality += expired ? 2 : 1;
                    }
                    break;
                case "Backstage passes to a TAFKAL80ETC concert":
                    if (expired) {
                        item.quality = 0;
                    } else {
                        if (item.sellIn <= 5) {
                            item.quality += 3;
                        } else if (item.sellIn <= 10) {
                            item.quality += 2;
                        } else {
                            item.quality += 1;
                        }

                        if (item.quality > 50) {
                            item.quality = 50;
                        }
                    }
                    break;
                default:
                    final int rate = expired ? 2 : 1;

                    item.quality = Math.max(item.quality - rate, 0);
                    break;
            }

            item.sellIn = item.sellIn - 1;
        }
    }
}
