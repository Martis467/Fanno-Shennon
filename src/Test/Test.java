package Test;

import main.models.Decode;
import main.models.Encode;
import sun.nio.ch.IOStatus;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

public class Test {

    private Encode encoder;
    private Decode decoder;
    private File file = new File("testFile.txt");
    private File encodedFile = new File("testFile.txt.encoded");
    private File decodedFile = new File("testFileDecoded.txt");

    public Test()
    {
     encoder = new Encode(8);
     decoder = new Decode();
    }

    public void compareFilesWithGivenContent(String fileContent) throws IOException {
            compareFilesWithGivenContent(fileContent, 2, 24);
        }

        public void compareFilesWithGivenContent(String fileContent, int wordLenFrom, int wordLenTo) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file.getName()));
        writer.write(fileContent);
        writer.close();

        for(int i = wordLenFrom; i <= wordLenTo; ++i)
        {
            encoder.setWordLength(i);
            encoder.encode(file.toURL());
            decoder.decode(encodedFile.toURL(), decodedFile.toString());
            byte[] f1 = Files.readAllBytes(file.toPath());
            byte[] f2 = Files.readAllBytes(decodedFile.toPath());

            if(!Arrays.equals(f1, f2))
            {
              System.out.println("failed wordlen: " + i);
             // throw new IOException();
            }
            //assert (Arrays.equals(f1, f2));
        }

    }

    public void runAllTests(){
        try {
            compareFilesWithGivenContent("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab");
            compareFilesWithGivenContent("THIS IS TEST STRING agajgnadjkfnejwafgeg THIS IS TEST STRING agajgnadjkfnejwafgeg THIS IS TEST STRING agajgnadjkfnejwafgeg THIS IS TEST STRING agajgnadjkfnejwafgeg");
            compareFilesWithGivenContent("a");
            compareFilesWithGivenContent("csdbtnyjukjgtrfasdefgtry");
            compareFilesWithGivenContent("add your test case here");
            compareFilesWithGivenContent("ab");
            compareFilesWithGivenContent("THIS IS TEST STRING THIS IS TEST STRING THIS IS TEST STRING THIS IS TEST STRING THIS IS TEST STRING ");

            int len = 10000;
            StringBuilder longString = new StringBuilder(len);
            for(int i = 0; i < len; i++)
                longString.append((char)i);

            compareFilesWithGivenContent(longString.toString());
            //get rid of that
            file.delete();
            encodedFile.delete();
            decodedFile.delete();

        }catch (IOException e)
        {

        }
    }

}
