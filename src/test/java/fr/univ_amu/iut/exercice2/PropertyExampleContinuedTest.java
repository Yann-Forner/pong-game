package fr.univ_amu.iut.exercice2;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.PrintStream;

import static org.mockito.Mockito.*;

public class PropertyExampleContinuedTest {
    private PrintStream out;
    private PropertyExampleContinued propertyExample;

    @Before
    public void setUp() throws Exception {
        propertyExample = new PropertyExampleContinued();
        propertyExample.setAnInt(1024);
        out = mock(PrintStream.class);
        System.setOut(out);
    }

    @Ignore
    @Test
    public void bindAndUnbindOnePropertyToAnother() throws Exception {
        propertyExample.bindAndUnbindOnePropertyToAnother();
        verify(out).println();
        verify(out).println("otherProperty.get() = 0");
        verify(out).println("Binding otherProperty to anIntProperty.");
        verify(out).println("otherProperty.get() = 1024");
        verify(out).println("Calling anIntProperty.set(7168).");
        verify(out, times(3)).println("otherProperty.get() = 7168");
        verify(out).println("Unbinding otherProperty from anIntProperty.");
        verify(out).println("Calling anIntProperty.set(8192).");
    }
}