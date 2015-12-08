package at.f1l2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import at.f1l2.command.PDFCommands;

public class F1l2shellApplicationTests {

    @Test
    public void fileWritingPerfTest() throws IOException {
        int ITERATIONS = 4;
        final double MEG = (Math.pow(1024, 2));
        final int RECORD_COUNT = 4000000;
        final String RECORD = "Help I am trapped in a fortune cookie factory\n";
        final int RECSIZE = RECORD.getBytes().length;

        List<String> records = new ArrayList<String>(RECORD_COUNT);
        int size = 0;
        for (int i = 0; i < RECORD_COUNT; i++) {
            records.add(RECORD);
            size += RECSIZE;
        }
        System.out.println(records.size() + " 'records'");
        System.out.println(size / MEG + " MB");

        for (int i = 0; i < ITERATIONS; i++) {
            System.out.println("\nIteration " + i);
            writeBuffered(records, (int) MEG);
        }
    }

    private static void writeBuffered(List<String> records, int bufSize) throws IOException {
        File file = File.createTempFile("foo", ".txt");

        System.out.println(file.getAbsolutePath());

        try {
            FileWriter writer = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(writer, bufSize);

            System.out.print("Writing buffered (buffer size: " + bufSize + ")... ");
            write(records, bufferedWriter);
        } finally {
            // comment this out if you want to inspect the files afterward
            // file.delete();
        }
    }

    @Test
    public void mergeWholeFolder() {

        PDFCommands commands = new PDFCommands();

        commands.mergeWholeFolder("C:/Users/Manuel/Desktop/test");

    }

    private static void write(List<String> records, Writer writer) throws IOException {
        long start = System.currentTimeMillis();
        for (String record : records) {
            writer.write(record);
        }
        writer.flush();
        writer.close();
        long end = System.currentTimeMillis();
        System.out.println((end - start) / 1000f + " seconds");
    }

}
