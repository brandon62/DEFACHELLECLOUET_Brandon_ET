package com.example.brandon.defachelleclouetbrandon_2916832_assignement2;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Brandon on 23/03/2016.
 */
public class MineView extends View
{
    Integer nbBombAround;
    boolean isBomb;
    public MineView(Context context)
    {
        super(context);
        isBomb = false;
        nbBombAround = 0;
    }
}
