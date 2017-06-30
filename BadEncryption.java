/*
 COSC 236
 Jon S. Patton
 Description: Do some unsafe encryption.
 Filename: BadEncryption.java
 Date started: 6/12/17
 Modification history: 6/21/17
 Methods: main, fsMenu, fsEncryptCeasar, fvDecryptCaesar
 */

/* Let's be clear: There is nothing about this cypher that is remotely secure.
 * While you end up with a bunch of large numbers, they're all within a small
 * range, making it simple to crack. However, the bones of the program could be
 * used to implement a more secure system using multiple prime number keys and
 * modular arithmetic. But I've already spent a couple hours on this, so time to
 * move on.
 *
 * This isn't even really Caesar cypher -- Caesar wraps around to the
 * beginning of the alphabet (that's just mod 26) for any letter that would be
 * >z, and you end up with actual letters rather than numerical values.
 */

import java.util.*;
import java.io.*;
import java.nio.file.*;

public class BadEncryption
{
  public static void main (String args[])
  {
    String sUsage; //how we will use the program

    sUsage = fsMenu();

    if (sUsage.equals("1"))
    {
      System.out.println(fsEncryptCeasar());  //Gotta return something as part of the exercise

    }
    else if (sUsage.equals("2"))
    {
      fvDecryptCaesar();
    }
    else
    {
      System.out.println("Goodbye.");
    }

  }

  //I abstracted this out because it was distracting in main.
  public static String fsMenu()
  {
    String sUsage = "";
    Scanner console = new Scanner(System.in);

    while (!sUsage.equals("1") && !sUsage.equals("2") && !sUsage.equals("0") && !sUsage.equals("q"))
    {
      System.out.println("This program will");
      System.out.println("1. Take a series of messages from you and write them in a cryptographic scheme to a file; or");
      System.out.println("2. Take a text file that was created in such a manner and print unencrypted text to the console.");
      System.out.println("Please select an option (0 or q to quit) -> ");
      sUsage = console.next();

      if (!sUsage.equals("1") && !sUsage.equals("2") && !sUsage.equals("0") && !sUsage.equals("q"))
      {
        System.out.println("Invalid option.");
      }
    }
    return sUsage;
  }

  //A method that decrypts a file written in Caesar cypher, where the filename is
  //KEY.txt (where KEY is an integer). Works for any key value including a long.
  public static void fvDecryptCaesar()
  {
    long lKey; //this will never be anything other than an int, but I'm being consistent for clarity.
    String sFileName;
    String sPath; //file PATH, used only in the case where an actual filename (rather than key) is provided.

    Scanner console = new Scanner(System.in);

    lKey = 0;

    System.out.println("This program will print decrypted text of a file that was previously encrypted.");

    //Get the file name from the user, and parse the key out of it (by taking all but its last 4 characters).
    System.out.print("Please enter the FILENAME or KEY -> ");
    sFileName = console.next();

    try
    {
      if (sFileName.length() > 4)
      {
        if (sFileName.substring(sFileName.length() - 4, sFileName.length()).equalsIgnoreCase(".txt"))
        {
          //If they enter a file in a completely different location, path will strip out the file name alone for the key.
          sPath = Paths.get(sFileName).getFileName().toString();
          lKey = Integer.valueOf(sPath.substring(0, (sPath.length() - 4)));
        }
        else
        {
          lKey = Integer.valueOf(sFileName);
          sFileName = lKey + ".txt";
        }
      }
    }
    //Weird that this is the only exception to catch, but it works.
    catch(NumberFormatException sErr)
    {
      System.err.println("Invalid filename or key.");
      return; //break out of the method
    }

    System.out.println();

    try
    {
      File ifsInput = new File(sFileName);
      if (!ifsInput.canRead())
      {
        //Breaks out of method.
        throw new IOException();
      }
      else
      {
        Scanner fRead = new Scanner(ifsInput);

        while (fRead.hasNextInt())
        {
          System.out.print((char) (fRead.nextLong() - lKey));
        }
        System.out.println();
      }
    }
    catch(IOException sErr)
    {
      System.err.println("Error: File not found or invalid key.");
      return; //break out of the method (not really needed here unless more stuff were to be added after this point)
    }
  }

  //A method to encrypt user-input data into a Caesar cypher variant and write it to a file
  public static String fsEncryptCeasar()
  {
    String sInput; //user input for each line
    boolean bCont; //Stop entering
    long lKey; //randomly generated.
    /* Although we're only generating a random int, hypothetically,
     * though unlikely ... ly, we could generate an int within 128
     * of the integer ceiling.
     */
    int iIt; //loop iterator
    int iStringLength; //Length of the user's message
    
    //Initialize variables
    bCont = true; //Keep taking input
    lKey = new Random().nextInt(); //Generates a key for the simple cypher

    //Instantiations
    Scanner console = new Scanner(System.in);

    //Generate the output file.
    File ofOutput = null;
    try
    {

      ofOutput = new File(lKey + ".txt");
      ofOutput.createNewFile();

      PrintStream ofsOutFile = new PrintStream(ofOutput);

      System.out.println("You may type any number of lines for your message.");
      System.out.println("Two blank lines in a row will exit.");

      while (bCont)
      {
        System.out.print("-> ");
        sInput = console.nextLine() + "\n";

        if (sInput.equals("\n"))
        {
          System.out.print("-> ");
          sInput = console.nextLine() + "\n";
          if (sInput.equals("\n")) //escapes
          {
            bCont = false;
            break;
          }
          else
          {
            sInput = "\n" + sInput;  //extra hard return for a single blank line.
          }
        }

        //Iterate over the user's string, apply the key, and print to the file.
        iStringLength = sInput.length();
        for (iIt = 0; iIt < iStringLength; iIt++)
        {
          ofsOutFile.printf("%12d", (sInput.charAt(iIt) + lKey));
        }
      }

      //You will have to modify the file location to get this to work

      return ("Your encrypted text is in the file " + lKey + ".txt in your working directory.");
    }
    catch (IOException sErr)
    {
      //System.err.println("Could not create or write to output file.");
      return "Could not create or write to output file."; //better.
    }
  }
}
