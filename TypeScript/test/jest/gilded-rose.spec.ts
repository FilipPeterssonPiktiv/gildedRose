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
  });
});
