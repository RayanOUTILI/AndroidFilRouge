package edu.iut.proftracker.controllers;

import android.content.Context;

public interface Clickable {
    void onClicItem(int itemIndex);
    Context getContext();
}