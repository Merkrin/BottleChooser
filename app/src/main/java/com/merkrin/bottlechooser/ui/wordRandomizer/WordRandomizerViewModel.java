package com.merkrin.bottlechooser.ui.wordRandomizer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WordRandomizerViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public WordRandomizerViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}