package checkout.render;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaletteTest {

    @Test
    void waitColorRunsFromCalmToAngry() {
        assertEquals(Palette.CALM, Palette.waitColor(0));
        assertEquals(Palette.TENSE, Palette.waitColor(0.5f));
        assertEquals(Palette.ANGRY, Palette.waitColor(1));
    }

    @Test
    void waitColorIsClamped() {
        assertEquals(Palette.CALM, Palette.waitColor(-2));
        assertEquals(Palette.ANGRY, Palette.waitColor(3));
    }
}
