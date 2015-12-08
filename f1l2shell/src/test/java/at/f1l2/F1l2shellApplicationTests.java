package at.f1l2;

import org.junit.Test;

import at.f1l2.command.PDFCommands;

public class F1l2shellApplicationTests {

    @Test
    public void mergeWholeFolder() {

        PDFCommands commands = new PDFCommands();

        commands.mergeWholeFolder("C:/Users/Manuel/Desktop/test");

    }

}
