package at.f1l2.command;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

@Component
public class PDFCommands implements CommandMarker {

    @CliCommand(value = "mfi", help = "merge pdf files")
    public String merge(@CliOption(key = { "files" }, mandatory = true, help = "Files to merge; Concatenated with ';' ") final String files) {

        merge("output.pdf", stringToPath(files));

        return "OK";
    }

    @CliCommand(value = "mfo", help = "merge pdf files")
    public String mergeWholeFolder(@CliOption(key = { "folder" }, mandatory = true, help = "Folder to merge; Alphabetically sorted.") final String folder) {

        Collection<Path> all = addTree(Paths.get(folder));
        imageToPDF(folder, all);

        all = addTree(Paths.get(folder));

        merge(folder + "/output.pdf", all);

        return "OK";

    }

    private static void merge(String output, Collection<Path> all) {

        try {
            Document document = new Document();

            PdfCopy copy = new PdfCopy(document, new FileOutputStream(output));
            document.open();

            PdfReader reader;
            int n;

            for (Path pathTemp : all) {

                if (pathTemp.toFile().getAbsolutePath().endsWith(".pdf")) {

                    reader = new PdfReader(pathTemp.toFile().getAbsolutePath());

                    n = reader.getNumberOfPages();
                    for (int page = 0; page < n;) {
                        copy.addPage(copy.getImportedPage(reader, ++page));
                    }
                    copy.freeReader(reader);
                    reader.close();
                }
            }
            document.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static Collection<Path> stringToPath(String files) {
        Collection<Path> all = new ArrayList<Path>();

        String[] filesArray = files.split(";");

        for (int i = 0; i < filesArray.length; i++) {
            all.add(Paths.get(filesArray[i]));
        }

        return all;

    }

    private static Collection<Path> addTree(Path path) {
        Collection<Path> all = new ArrayList<Path>();

        try {
            addTree(path, all);
        } catch (Exception e) {
            System.out.println(e);
        }
        return all;
    }

    private static void addTree(Path directory, Collection<Path> all) throws IOException {
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(directory)) {
            for (Path child : ds) {
                all.add(child);
                if (Files.isDirectory(child)) {
                    addTree(child, all);
                }
            }
        }
    }

    private static void imageToPDF(String folder, Collection<Path> all) {

        for (Path path : all) {

            Document document = new Document();

            String absolutePathInput = path.toFile().getAbsolutePath();
            String absolutePathOutput = folder + "/" + path.getFileName() + ".pdf";

            if (absolutePathInput.endsWith(".gif") ||
                    //
                    absolutePathInput.endsWith(".GIF") ||
                    //
                    absolutePathInput.endsWith(".jpg") ||
                    //
                    absolutePathInput.endsWith(".JPG") ||
                    //
                    absolutePathInput.endsWith(".png") ||
                    //
                    absolutePathInput.endsWith(".PNG")) {

                try {
                    FileOutputStream fos = new FileOutputStream(absolutePathOutput);
                    PdfWriter writer = PdfWriter.getInstance(document, fos);
                    writer.open();
                    document.open();

                    int indentation = 0;

                    Image image = Image.getInstance(absolutePathInput);
                    float scaler = ((document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin() - indentation) / image.getWidth()) * 100;

                    image.scalePercent(scaler);

                    document.add(image);
                    document.close();
                    writer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
