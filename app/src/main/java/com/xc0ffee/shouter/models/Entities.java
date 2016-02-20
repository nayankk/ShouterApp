package com.xc0ffee.shouter.models;

import java.util.ArrayList;
import java.util.List;

public class Entities {

    private List<Media> media;

    Entities() {
        media = new ArrayList<>();
    }

    public List<Media> getMedia() {
        return media;
    }
}
