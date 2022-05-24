export class Item {
  name: string;
  sellIn: number;
  quality: number;

  constructor(name, sellIn, quality) {
    this.name = name;
    this.sellIn = sellIn;
    this.quality = quality;
  }
}

function increaseBrieQuality(item: Item) {
  const qualityRate = item.sellIn <= 0 ? 2 : 1; 

  return Math.min(item.quality + qualityRate, 50);
}

function increaseBackstagePassQuality(item: Item): number {
  let qualityRate = (()=> {
    if (item.sellIn > 10) {
      return 1;
    }
    if (item.sellIn > 5) {
      return 2;
    }
    if (item.sellIn > 0) {
      return 3;
    }
    return 0;
  })();

  if(item.sellIn <= 0) {
    return 0;
  }

  return Math.min(item.quality + qualityRate, 50);
}

export class GildedRose {
  items: Array<Item>;

  constructor(items = [] as Array<Item>) {
    this.items = items;
  }

  updateQuality(): Item[] {
    for (let i = 0; i < this.items.length; i++) {
      const item = this.items[i];
      if (item.name == 'Sulfuras, Hand of Ragnaros') {
        continue;
      }

      if (item.name == 'Aged Brie') {
        item.quality = increaseBrieQuality(item);
        continue;
      }

      if (item.name == 'Backstage passes to a TAFKAL80ETC concert') {
        item.quality = increaseBackstagePassQuality(item);
        continue;
      }

      if (item.quality > 0) {
        item.quality = item.quality - 1
      }
      
      item.sellIn = item.sellIn - 1;
      if (item.sellIn < 0) {
        if (item.quality > 0) {
          item.quality = item.quality - 1
        }
      }
    }

    return this.items;
  }
}

