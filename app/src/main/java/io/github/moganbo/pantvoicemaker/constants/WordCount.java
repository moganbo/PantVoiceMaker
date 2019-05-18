package io.github.moganbo.pantvoicemaker.constants;

import java.util.ArrayList;
import java.util.List;

public enum WordCount {
    UNDEFINED,
    SHORT(0, "短い", 3),
    MEDIUM(1, "普通", 5),
    LONG(2, "長い",10),
    ;

    private int index;
    private String label;
    private int value;

    WordCount() {
        this.index = -1;
        this.label = null;
        this.value = 0;
    }

    WordCount(int index, String label, int value){
        this.index = index;
        this.label = label;
        this.value = value;
    }

    public int getIndex(){
        return this.index;
    }

    public int getValue(){
        return this.value;
    }

    public String getLabel(){
        return this.label;
    }

    public static WordCount getTypeByName(String name) {
        for (WordCount v : values()) {
            if (v.name().equals(name)) {
                return v;
            }
        }
        return UNDEFINED;
    }

    public static WordCount getTypeByIndex(int index) {
        for (WordCount v : values()) {
            if (v.index == index) {
                return v;
            }
        }
        return UNDEFINED;
    }

    public static List<String> getLabelList() {
        List<String> result = new ArrayList<>();
        WordCount[] wordCounts = values();
        for (WordCount wc : wordCounts) {
            if (wc.label == null) {
                continue;
            }
            result.add(wc.label);
        }
        return result;
    }
}
