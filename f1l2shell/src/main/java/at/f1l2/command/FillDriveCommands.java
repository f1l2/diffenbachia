package at.f1l2.command;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class FillDriveCommands implements CommandMarker {

    @CliCommand(value = "fill", help = "write txt file to drive")
    public String fileWritingPerfTest(@CliOption(key = { "directory" }, mandatory = false, help = "directory, where txt files are stored (default value: current location)") final String strDirectory,
            //
            @CliOption(key = { "iterations" }, mandatory = false, help = "define number of iterations (default value: 4)") final String strIterations,
            //
            @CliOption(key = { "records" }, mandatory = false, help = "define record count per file (default value: 40000000)") final String strRecordCount) throws IOException {

        final double MEG = (Math.pow(1024, 2));
        final String RECORD = "Help I am trapped in a fortune cookie factory\n";
        final int RECSIZE = RECORD.getBytes().length;

        int iterations = 4;
        try {
            if (!StringUtils.isEmpty(strIterations)) {
                iterations = Integer.parseInt(strIterations);
            }
        } catch (Exception e) {
        }
        System.out.println("Iterations: " + iterations);

        int recordCount = 4000000;
        try {
            if (StringUtils.isEmpty(strRecordCount)) {
                recordCount = Integer.parseInt(strRecordCount);
            }
        } catch (Exception e) {
        }
        System.out.println("Records: " + recordCount);

        String directory;
        if (StringUtils.isEmpty(strDirectory)) {
            directory = "";
        } else {
            directory = strDirectory;
            if (!directory.endsWith("/")) {
                directory = directory.concat("/");
            }
        }
        System.out.println("Directory: " + directory);

        List<String> records = new ArrayList<String>(recordCount);
        int size = 0;
        for (int i = 0; i < recordCount; i++) {
            records.add(RECORD);
            size += RECSIZE;
        }
        System.out.println(records.size() + " 'records'");
        System.out.println(size / MEG + " MB");

        for (int i = 0; i < iterations; i++) {
            System.out.println("\nIteration " + i);
            writeBuffered(records, (int) MEG, directory);
        }

        return "OK";

    }

    private static void writeBuffered(List<String> records, int bufSize, String directory) throws IOException {

        File file = new File(directory + "foo" + new Date().getTime() + ".txt");

        System.out.println(file.getAbsolutePath());

        try {
            FileWriter writer = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(writer, bufSize);

            System.out.print("Writing buffered (buffer size: " + bufSize + ")... ");
            write(records, bufferedWriter);
        } catch (Exception e) {
            System.out.println(e);
        } finally {

        }
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
