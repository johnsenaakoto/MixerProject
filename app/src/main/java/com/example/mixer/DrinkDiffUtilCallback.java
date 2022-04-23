package com.example.mixer;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class DrinkDiffUtilCallback extends DiffUtil.Callback {

    private List<Drink> oldList;
    private List<Drink> newList;

    public DrinkDiffUtilCallback(List<Drink> oldList, List<Drink> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldItemPosition == newItemPosition;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition) == newList.get(newItemPosition);
    }
}
