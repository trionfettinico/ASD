/**
 * Miniprogetto 3 di Algoritmi e Strutture Dati, Laboratorio Anno Accademico 2019/2020
 */
package it.unicam.cs.asdl1920.mp3;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Classe di unit test per il risolutore del problema di una più
 * lunga sottosequenza comune di due stringhe date.
 * 
 * @author Luca Tesei
 *
 */
class LCSSolverTest {

    @Test
    final void testHashCode() {
        LCSSolver s1 = new LCSSolver("pippo", "pluto");
        LCSSolver s2 = new LCSSolver("pluto", "pippo");
        LCSSolver s3 = new LCSSolver("pippo", "pluto");
        assertTrue(s1.hashCode() == s3.hashCode());
        assertTrue(s1.hashCode() == s2.hashCode());
    }

    @Test
    final void testLCSSolver() {
        assertThrows(NullPointerException.class,
                () -> new LCSSolver("pippo", null));
        assertThrows(NullPointerException.class,
                () -> new LCSSolver(null, "pluto"));
    }

    @Test
    final void testGetX() {
        LCSSolver s = new LCSSolver("", "pippo");
        assertTrue(s.getX().equals(""));
    }

    @Test
    final void testGetY() {
        LCSSolver s = new LCSSolver("", "pippo");
        assertTrue(s.getY().equals("pippo"));
    }

    @Test
    final void testEqualsObject() {
        LCSSolver s1 = new LCSSolver("pippo", "pluto");
        LCSSolver s2 = new LCSSolver("pluto", "pippo");
        LCSSolver s3 = new LCSSolver("pippo", "pluto");
        LCSSolver s4 = new LCSSolver("pippo", "topolino");
        assertTrue(s1.equals(s2));
        assertTrue(s1.equals(s3));
        assertTrue(s2.equals(s3));
        assertTrue(s2.equals(s1));
        assertTrue(s3.equals(s1));
        assertTrue(s3.equals(s2));
        assertFalse(s1.equals(s4));
        assertFalse(s4.equals(s1));
    }

    @Test
    final void testIsSolved() {
        LCSSolver s1 = new LCSSolver("pippo", "");
        assertFalse(s1.isSolved());
        s1.solve();
        assertTrue(s1.isSolved());
    }
    
    @Test
    final void testGetLengthOfSolution1() {
        LCSSolver s1 = new LCSSolver("ABCBDAB", "");
        s1.solve();
        assertTrue(s1.getLengthOfSolution() == 0);
        LCSSolver s2 = new LCSSolver("", "");
        s2.solve();
        assertTrue(s2.getLengthOfSolution() == 0);
        LCSSolver s3 = new LCSSolver("ABCBDAB", "BDCABA");
        s3.solve();
        assertTrue(s3.getLengthOfSolution() == 4);
    }

    @Test
    final void testGetOneSolution1() {
        LCSSolver s1 = new LCSSolver("ABCBDAB", "");
        s1.solve();
        assertTrue(s1.getOneSolution().equals(""));
        LCSSolver s2 = new LCSSolver("", "");
        s2.solve();
        assertTrue(s2.getOneSolution().equals(""));
        LCSSolver s3 = new LCSSolver("ABCBDAB", "BDCABA");
        s3.solve();
        assertTrue(s3.getOneSolution().equals("BCBA"));
    }
    
    @Test
    final void testGetLengthOfSolution2() {
        LCSSolver s1 = new LCSSolver("abcabcaa", "acbacba");
        s1.solve();
        assertTrue(s1.getLengthOfSolution() == 5);
        LCSSolver s2 = new LCSSolver("XMJYAUZ", "MZJAWXU");
        s2.solve();
        assertTrue(s2.getLengthOfSolution() == 4); 
        LCSSolver s3 = new LCSSolver("XYZ", "ABCDS");
        s3.solve();
        assertTrue(s3.getLengthOfSolution() == 0);
    }

    @Test
    final void testGetOneSolution2() {
        LCSSolver s1 = new LCSSolver("abcabcaa", "acbacba");
        s1.solve();
        assertTrue(s1.getOneSolution().equals("abcba"));
        LCSSolver s2 = new LCSSolver("XMJYAUZ", "MZJAWXU");
        s2.solve();
        assertTrue(s2.getOneSolution().equals("MJAU"));
        LCSSolver s3 = new LCSSolver("XYZ", "ABCDS");
        s3.solve();
        assertTrue(s3.getOneSolution().equals(""));
    }

    @Test
    final void testIsCommonSubsequence() {
        LCSSolver s1 = new LCSSolver("ABCBDAB", "BDCABA");
        assertThrows(NullPointerException.class,
                () -> s1.isCommonSubsequence(null));
        assertTrue(s1.isCommonSubsequence(""));
        assertTrue(s1.isCommonSubsequence("A"));
        assertFalse(s1.isCommonSubsequence("Z"));
        assertTrue(s1.isCommonSubsequence("AB"));
        assertFalse(s1.isCommonSubsequence("DC"));
        assertTrue(s1.isCommonSubsequence("ABA"));
        assertFalse(s1.isCommonSubsequence("ABC"));
        assertTrue(s1.isCommonSubsequence("BCBA"));
        assertTrue(s1.isCommonSubsequence("BCAB"));
        assertTrue(s1.isCommonSubsequence("BDAB"));
        assertFalse(s1.isCommonSubsequence("BCDA"));
        assertFalse(s1.isCommonSubsequence("ABCB"));
        assertFalse(s1.isCommonSubsequence("BDBA"));
        assertFalse(s1.isCommonSubsequence("BCABA"));
        assertFalse(s1.isCommonSubsequence("ABCBDAB"));
        assertFalse(s1.isCommonSubsequence("BDCABA"));
    }
}
