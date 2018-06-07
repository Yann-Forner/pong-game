package fr.univ_amu.iut.exercice3_2;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class TriangleAreaTest {
    private TriangleArea triangleArea;
    private PrintStream out;

    @Before
    public void setUp() throws Exception {
        triangleArea = new TriangleArea();
        out = mock(PrintStream.class);
        System.setOut(out);
    }

    @Ignore
    @Test
    public void should_an_empty_triangle_have_a_zero_area() throws Exception {
        assertThat(triangleArea.getArea()).isZero();
    }

    @Ignore
    @Test
    public void should_an_unity_triangle_have_a_0_5_area() throws Exception {
        triangleArea.setP1(0, 0);
        triangleArea.setP2(1, 0);
        triangleArea.setP3(0, 1);
        assertThat(triangleArea.getArea()).isEqualTo(0.5);
    }

    @Ignore
    @Test
    public void should_triangle1_have_a_correct_area() throws Exception {
        triangleArea.setP1(0, 0);
        triangleArea.setP2(6, 0);
        triangleArea.setP3(4, 3);
        assertThat(triangleArea.getArea()).isEqualTo(9);
    }

    @Ignore
    @Test
    public void should_triangle2_have_a_correct_area() throws Exception {
        triangleArea.setP1(1, 0);
        triangleArea.setP2(2, 2);
        triangleArea.setP3(0, 1);
        assertThat(triangleArea.getArea()).isEqualTo(1.5);
    }

    @Ignore
    @Test
    public void printResult() throws Exception {
        triangleArea.setP1(0, 0);
        triangleArea.setP2(6, 0);
        triangleArea.setP3(4, 3);
        triangleArea.printResult();

        verify(out).println("For P1(0,0), P2(6,0), P3(4,3), the area of triangle ABC is 9,0");
    }
}