import java.io.*;
import java.util.*;
/**
 * A file untility class to load and save file.
 * 
 * @author Anu Datar 
 * @version 1.0
 */
public class FileUtil
{
    /**
     * Load file from file.
     * 
     * @param fileName the file name to load file from
     * @return an iterator of the Strings
     */
    public static Iterator < String > loadFile(String fileName)
    {
        try
        {
            Scanner in = new Scanner(new File(fileName));
            List < String > list = new ArrayList < String > ();
            while (in.hasNextLine())
                list.add(in.nextLine());
            in.close();
            return list.iterator();
        }
        catch (FileNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Save data to file.
     * 
     * @param fileName the file name to save to
     * @param data the data to save
     */
    public static void saveFile(String fileName, Iterator < String > data)
    {
        try
        {
            PrintWriter out = new PrintWriter(
                    new FileWriter(fileName), true);
            while (data.hasNext())
                out.println(data.next());
            out.close();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}