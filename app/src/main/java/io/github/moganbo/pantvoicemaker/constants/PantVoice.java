package io.github.moganbo.pantvoicemaker.constants;

import com.annimon.stream.Stream;
import com.annimon.stream.function.Function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public enum PantVoice {
    UNDEFINED,
    MODERATE(0, "控えめ", DefaultPantVoiceList.MODERATE),
    ORDINARY(1, "普通", DefaultPantVoiceList.ORDINARY),
    EXTREME(2, "激しめ", DefaultPantVoiceList.EXTREME),
    HEART(3, "♡", DefaultPantVoiceList.HEART),
    ;

    private int index;
    private String jpName;
    private List<String> list;

    PantVoice() {
        this.index = -1;
        this.jpName = null;
        this.list = new ArrayList<>();
    }

    PantVoice(int index, String jpName, List<String> list) {
        this.index = index;
        this.jpName = jpName;
        this.list = list;
    }

    public int getIndex(){
        return this.index;
    }

    public String getJpName() {
        return this.jpName;
    }

    public List<String> getList() {
        return this.list;
    }

    public static PantVoice getTypeByName(String name) {
        for (PantVoice v : values()) {
            if (v.name().equals(name)) {
                return v;
            }
        }
        return UNDEFINED;
    }

    public static PantVoice getTypeByIndex(int index) {
        for (PantVoice v : values()) {
            if (v.index == index) {
                return v;
            }
        }
        return UNDEFINED;
    }

    public static List<String> getJpNameList() {
        List<String> result = new ArrayList<>();
        PantVoice[] pantVoices = values();
        for (PantVoice pv : pantVoices) {
            if (pv.jpName == null) {
                continue;
            }
            result.add(pv.jpName);
        }
        return result;
    }

}
