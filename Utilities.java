import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.File;
import java.util.Iterator;
/**
 * class utilities that handles opening and saving image files.
 */
public final class Utilities
{
    /**
     * load image takes an image from the directory and creates an image object and fills it with the values in the pgm file.
     * @param pgmFile file to open and read
     * @return image object with filled in values from pgm file
     */
    public static Image<Short> loadImage(String pgmFile)
    {
        int width;
        int height;
        try{
            String userWord;
            File file = new File(pgmFile);
            Scanner scnr = new Scanner(file);
            userWord = scnr.next();
            if(userWord.equals("P2")){
                width = Integer.parseInt(scnr.next());
                height = Integer.parseInt(scnr.next());
                scnr.next();
                Image<Short> img = new Image<>(width,height);
                Iterator<Node<Short>> iter = img.iterator();
                img.getHead().setValue((short) Integer.parseInt(scnr.next()));
                while(iter.hasNext()){
                    iter.next().setValue((short) Integer.parseInt(scnr.next()));
                }
                return img;
            }
            scnr.close();
        }
        catch(RuntimeException | FileNotFoundException e){
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * takes image and save to pgm file.
     * @param image image with filled in values
     * @param pgmFile name of file to save to
     */
    public static void saveImage(Image<Short> image, String pgmFile)
    {
        try{
            File file = new File(pgmFile);
            boolean exists = file.exists();
            if(!exists){
                throw new RuntimeException();
            }
            PrintWriter writer = new PrintWriter(file);
            writer.print("P2\n" + image.getWidth() + " " + image.getHeight() + "\n255\n");
            Iterator<Node<Short>> iter = image.iterator();
            writer.print(image.getHead().getValue());
            while(iter.hasNext()){
                writer.print(" " + iter.next().getValue());
            }
            writer.close();
        }
        catch(RuntimeException | FileNotFoundException e){
            throw new RuntimeException(e);
        }
    }
}
