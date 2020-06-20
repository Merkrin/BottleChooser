package com.merkrin.bottlechooser.ui.pairs;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.merkrin.bottlechooser.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import adapter.PeopleAdapter;

public class PairsFragment extends Fragment {

    public static final String APP_PREFERENCES = "application_settings";
    public static final String APP_PREFERENCES_NAMES = "names_list";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private PairsViewModel pairsViewModel;

    private RecyclerView listOfPeopleView;
    private PeopleAdapter peopleAdapter;

    private EditText personEditText;
    private TextView resultTextView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        pairsViewModel =
                ViewModelProviders.of(this).get(PairsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_pair_maker, container, false);

        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initRecyclerView(view);
        sharedPreferences = Objects.requireNonNull(this.getActivity())
                .getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        if (sharedPreferences.contains(APP_PREFERENCES_NAMES)) {
            Set<String> set = sharedPreferences.getStringSet(APP_PREFERENCES_NAMES, null);
            if (set != null)
                peopleAdapter.setItems(new ArrayList<String>(set));
        }

        view.findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPerson();
            }
        });

        view.findViewById(R.id.deleteSelectedButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletePerson();
            }
        });

        view.findViewById(R.id.resetButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPeople();
            }
        });

        view.findViewById(R.id.randomizeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                randomizePair();
            }
        });

        view.findViewById(R.id.personNameEditText).setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    addPerson();
                    return true;
                }
                return false;
            }
        });

        personEditText = view.findViewById(R.id.personNameEditText);
        resultTextView = view.findViewById(R.id.resultTextView);
    }

    private void addPerson() {
        String name = personEditText.getText().toString();

        if (!name.isEmpty()) {
            peopleAdapter.addItem(name);
        }

        InputMethodManager mgr = (InputMethodManager) Objects.requireNonNull(getActivity())
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (mgr != null)
            mgr.hideSoftInputFromWindow(personEditText.getWindowToken(), 0);

        personEditText.setText("");

        editor.putStringSet(APP_PREFERENCES_NAMES, new HashSet(peopleAdapter.getPeopleList()));
        editor.commit();
    }

    private String getPeopleFromList() {
        StringBuilder stringBuilder = new StringBuilder();
        List<String> people = peopleAdapter.getPeopleList();

        for (String person : people) {
            stringBuilder.append(person);
            stringBuilder.append(";");
        }

        return stringBuilder.toString();
    }

    private void deletePerson() {
        peopleAdapter.deleteSelected(peopleAdapter.getSelected());
    }

    private void resetPeople() {
        peopleAdapter.clearItems();
    }

    private void randomizePair() {
        String pair = peopleAdapter.randomPair();

        resultTextView.setText(pair);
    }

    private void initRecyclerView(View view) {
        listOfPeopleView = view.findViewById(R.id.peopleRecyclerView);

        ViewGroup.LayoutParams params = listOfPeopleView.getLayoutParams();
        Point size = new Point();
        Display display = Objects.requireNonNull(getActivity()).getWindowManager().getDefaultDisplay();
        display.getSize(size);
        params.height = size.y / 3;
        listOfPeopleView.setLayoutParams(params);

        listOfPeopleView.setHasFixedSize(true);
        listOfPeopleView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        listOfPeopleView.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL));

        peopleAdapter = new PeopleAdapter();
        listOfPeopleView.setAdapter(peopleAdapter);
    }
}