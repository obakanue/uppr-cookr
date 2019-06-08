package com.example.cookr;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class RecipeReader {
    private Context context;

    public RecipeReader(Context context) {
        this.context = context;
    }

    public ArrayList<Instruction> loadRecipeFromAssets(String path) throws IOException {
        ArrayList<Instruction> al = new ArrayList<Instruction>();
        InputStream is = context.getAssets().open(path);
        Scanner s = new Scanner(is);

        while(s.hasNextLine()) {
            String line = s.nextLine();
            String identifier = line.split(":")[0];

            switch(identifier) {
                case "title":
                    al.add((Instruction) new TitleInstruction(line));
                    break;
                case "step":
                    al.add((Instruction) new StepInstruction(line));
                    break;
                case "timer":
                    al.add((Instruction) new TimerInstruction(line));
                    break;
                default:
                    break;
            }
        }

        return al;
    }
}
