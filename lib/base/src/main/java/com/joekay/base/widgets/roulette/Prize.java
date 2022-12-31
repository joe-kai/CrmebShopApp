package com.joekay.base.widgets.roulette;

import androidx.annotation.ColorLong;

public class Prize {
    public String prizeName;
    public int prizeIcon;

    public int spanColor;

    public Prize(String prizeName, int prizeIcon, int spanColor) {
        this.prizeName = prizeName;
        this.prizeIcon = prizeIcon;
        this.spanColor = spanColor;
    }
}
