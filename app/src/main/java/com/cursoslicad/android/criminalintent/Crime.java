package com.cursoslicad.android.criminalintent;

import java.util.Date;
import java.util.UUID;

/**
 * Created by javier on 6/26/17.
 */

public class Crime {
    private UUID mId;
    private String mTitle;

    public Crime(){
        // Genera un identificador único
        mId = UUID.randomUUID();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

}
