package com.merkrin.bottlechooser.ui.wordRandomizer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.merkrin.bottlechooser.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

public class WordRandomizerFragment extends Fragment {

    private final static int DICTIONARY_LENGTH = 6801;
    private static Random random = new Random();

    private WordRandomizerViewModel wordRandomizerViewModel;
    private TextView wordTextView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        wordRandomizerViewModel =
                ViewModelProviders.of(this).get(WordRandomizerViewModel.class);
        View root = inflater.inflate(R.layout.fragment_word_randomizer, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.randomizeWordButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                randomizeWord();
            }
        });

        wordTextView = (TextView) view.findViewById(R.id.randomWordTextView);
    }

    private void randomizeWord() {
        int index = random.nextInt(DICTIONARY_LENGTH);
        String word = null;

        InputStream inputStream = getResources().openRawResource(R.raw.dictionary);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            int counter = 0;

            while (counter < index) {
                word = br.readLine();

                counter++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (word != null)
            wordTextView.setText(word + "JS");
    }
}