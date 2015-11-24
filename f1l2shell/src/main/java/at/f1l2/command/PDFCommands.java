package at.f1l2.command;

import java.io.FileOutputStream;

import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;

@Component
public class PDFCommands implements CommandMarker {

    @CliCommand(value = "merge", help = "merge pdf files")
    public String merge(@CliOption(key = { "files" }, mandatory = true, help = "Files to merge, concatenated with ';' ") final String files) {

        try {

            String[] filesArray = files.split(";");

            Document document = new Document();

            PdfCopy copy = new PdfCopy(document, new FileOutputStream("output.pdf"));

            // step 3
            document.open();
            // step 4
            PdfReader reader;
            int n;
            // loop over the documents you want to concatenate
            for (int i = 0; i < filesArray.length; i++) {
                reader = new PdfReader(filesArray[i]);
                // loop over the pages in that document
                n = reader.getNumberOfPages();
                for (int page = 0; page < n;) {
                    copy.addPage(copy.getImportedPage(reader, ++page));
                }
                copy.freeReader(reader);
                reader.close();
            }

            document.close();

        } catch (Exception e) {

        }

        return "OK";
    }

}
