import { Item, GildedRose } from '@/gilded-rose';

describe('Gilded Rose', () => {
  it('should contain items', () => {
    const expectedItems = [
      new Item('foo', 0, 0),
      new Item('bar', 0, 0),
    ];
    const gildedRose = new GildedRose(expectedItems);

    const items = gildedRose.updateQuality();

    expect(items[0].name).toBe('foo');
    expect(items[1].name).toBe('bar');
  });

  describe('with legendary item', () => {
    it('should not degrade quality', () => {
      const expectedItems = [
        new Item('Sulfuras, Hand of Ragnaros', 5, 30),
      ];
      const gildedRose = new GildedRose(expectedItems);

      const items = gildedRose.updateQuality();

      expect(items[0].quality).toBe(30);
    });

    it('should not decrease time until sell', () => {
      const expectedItems = [
        new Item('Sulfuras, Hand of Ragnaros', 5, 30),
      ];
      const gildedRose = new GildedRose(expectedItems);

      const items = gildedRose.updateQuality();

      expect(items[0].sellIn).toBe(5);
    });

    it('should update items after a legendary item', () => {
      const expectedItems = [
        new Item('Sulfuras, Hand of Ragnaros', 5, 30),
        new Item('A Regular Item', 6, 20)
      ];
      const gildedRose = new GildedRose(expectedItems);

      const items = gildedRose.updateQuality();

      expect(items[1].sellIn).toBe(5);
    });
  });
  describe('with aged brie', () => {
    it('should increase in quality as time passes', () => {
      const expectedItems = [
        new Item('Aged Brie', 5, 10),
      ];
      const gildedRose = new GildedRose(expectedItems);

      const items = gildedRose.updateQuality();

      expect(items[0].quality).toBe(11);
    });

    it('should not increase quality above 50', () => {
      const expectedItems = [
        new Item('Aged Brie', 5, 50),
      ];
      const gildedRose = new GildedRose(expectedItems);

      const items = gildedRose.updateQuality();

      expect(items[0].quality).toBe(50);
    });

    it('should increase in quality with double the rate when expired', () => {
      const expectedItems = [
        new Item('Aged Brie', 0, 20),
        new Item('Aged Brie', 1, 20),
      ];
      const gildedRose = new GildedRose(expectedItems);

      const items = gildedRose.updateQuality();

      expect(items[0].quality).toBe(22);
      expect(items[1].quality).toBe(21);
    });
  });

});
