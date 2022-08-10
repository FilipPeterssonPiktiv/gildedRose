package com.gildedrose;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class GildedRoseTest {
    private static final String ITEM_1 = "foo";
    private static final String ITEM_2 = "goo";
    private static final String AGED_BRIE = "Aged Brie";
    private static final String SULFURAS = "Sulfuras";
    private static final String BACKSTAGE_PASS = "Backstage passes to a TAFKAL80ETC concert";

    @Test
    void foo() {
        Item[] items = new Item[] { new Item("foo", 0, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals("foo", app.items[0].name);
    }

    @ParameterizedTest
    @MethodSource(value = "itemArguments")
    void item_quality_can_never_be_negative(final Item[] items) {
        final GildedRose rose = runUpdateQualityForDays(items, 100);

        assertFalse(Arrays.stream(rose.items).anyMatch(item -> item.quality < 0));
    }

    @ParameterizedTest
    @MethodSource(value = "itemArguments")
    void item_quality_can_never_be_above_50_unless_sulfuras(final Item[] items) {
        final GildedRose rose = runUpdateQualityForDays(items, 100);

        assertFalse(Arrays.stream(rose.items).anyMatch(item -> item.quality > 50 && !Objects.equals(item.name, SULFURAS)));
    }

    private GildedRose runUpdateQualityForDays(final Item[] items, final int days) {
        GildedRose app = new GildedRose(items);

        for (int i = 0; i < days; i++) {
            app.updateQuality();
        }

        return app;
    }

    private static Stream<Arguments> itemArguments() {
        final Item[] items = new Item[] {
            new Item(ITEM_1, 20, 50),
            new Item(ITEM_2, 6, 25),
            new Item(AGED_BRIE, 25, 5),
            new Item(SULFURAS, 20, 80),
            new Item(BACKSTAGE_PASS, 25, 10)
        };

        return Stream.of(Arguments.of((Object) items));
    }
}
