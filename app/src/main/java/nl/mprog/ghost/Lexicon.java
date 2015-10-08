// Corine Jacobs
// 10001326
// Corine_J@MSN.com
// Minor Programmeren 2015/2016 - Universiteit van Amsterdam

package nl.mprog.ghost;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class Lexicon{

    HashSet<String> fulldict;
    HashSet<String> filtereddict;
    String langg;
    int count = 0;


    // lexicon constructor, constructs both a fulldict and a copy of it that can/will be filtered later
    public Lexicon(Context context, String language){

        fulldict = new HashSet<>();
        filtereddict = new HashSet<>();
        langg = language;

        try {
            Log.d("READ", "Start to read file and load in words");
            AssetManager am = context.getAssets();
            InputStream inputstream;

            // use dictionary based on provided argument
            if (Objects.equals(langg, "English")){
                inputstream = am.open("english.txt");
            }
            else if (Objects.equals(langg, "Dutch")){
                inputstream = am.open("dutch.txt");
            }
            else{
                inputstream = am.open("test.txt");
            }

            // read the data from the dictionary text file and put this in hashset 'fulldict'
            InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
            BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
            String word;
            while((word = bufferedreader.readLine()) != null){
                fulldict.add(word);
            }
            Log.d("READ", "Load in complete");
        }

        // catch exception that may occur when trying to read the file
        catch (FileNotFoundException e) {
            Log.e("READ", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("READ", "Cannot read file: " + e.toString());
        }

        // copy content of fulldict into filtereddict
        filtereddict.addAll(fulldict);

    }


    // filters dictionary hashset based on prefix provided as argument
    // all words that do not start with this prefix will be removed from filtered dictionary
    public void filter(String prefix){

        Log.d("READ", "Filtering...");

        Iterator<String> iterator = filtereddict.iterator();
        while (iterator.hasNext()){
            String word = iterator.next();
            if (!word.startsWith(prefix)){
                iterator.remove();
            }
        }

        Log.d("READ", "Done filtering: " + filtereddict.toString());
    }


    // returns amount of items left in the hashset filtereddict
    public int count(){
        count = filtereddict.size();
        Log.d("READ", "# words in filtered left: " + Integer.toString(count));
        return count;
    }


    // returns the last word in the filtered dict
    public String result(){

        String lastword = "";

        // error checking if there really is only one word left
        if (count == 1) {
            lastword = filtereddict.iterator().next();
            return lastword;
        }
        else{
            Log.e("READ", "There was more than one word left in the filtereddict");
            return null;
        }
    }

    // restore filtereddict to contain fulldict again
    public void reset(){

        filtereddict.addAll(fulldict);

    }

}