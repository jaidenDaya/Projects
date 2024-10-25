package com.example.assignment3;

// Assignment 3
// Names: Nhu Nguyen and Jaiden Daya (Team 7)
// File Name: CardInfo

public class CardInfo {
    boolean isFocused = false;
    boolean isSelected = false;
    int drawableId;

    public CardInfo(boolean isFocused, boolean isSelected, int drawableId) {
        this.isFocused = isFocused;
        this.isSelected = isSelected;
        this.drawableId = drawableId;
    }

    public boolean getFocused() {
        return isFocused;
    }

    public void setFocused(boolean focused) {
        isFocused = focused;
    }

    public boolean getSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int id) {
        drawableId = id;
    }
}



