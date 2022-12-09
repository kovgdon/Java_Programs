package org.example;

import java.util.LinkedList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public final class CSVReader {
    private char mseparator;
    private File mDFile = null;
    private Scanner mreader = null;

    public void setSeparatorTo(char c) {
        mseparator = c;
    }

    //returns null if the end of file is reached
    public LinkedList<String> NextLine() throws Exception {
        if(mDFile == null) throw new Exception("Файл не открыт");
        if(!mreader.hasNext()) return null;

        LinkedList<String> values = new LinkedList<String>();
        String line = mreader.nextLine();
        boolean notDone = true;
        int prevSeparatorIndex = 0;
        int currSeparatorIndex = 0;

        while(notDone) {
            currSeparatorIndex = line.indexOf(mseparator, prevSeparatorIndex);
            if(currSeparatorIndex == -1) {
                values.add(line.substring(prevSeparatorIndex));
                notDone = false;
            }
            else {
                values.add(line.substring(prevSeparatorIndex, currSeparatorIndex));
                prevSeparatorIndex = currSeparatorIndex+1;
            }
        }

        return values;
    }

    public boolean OpenFile(String filepath){
        mDFile = new File(filepath);
        if(!mDFile.canRead())
            return false;
        else {
            try {
                mreader = new Scanner(mDFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }


    public void CloseFile() {
        if(mDFile != null) {
            mreader.close();
            mDFile = null;
            mreader = null;
        }
    }

}