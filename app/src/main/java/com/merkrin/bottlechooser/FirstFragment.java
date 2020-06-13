package com.merkrin.bottlechooser;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.Collection;

import adapter.PeopleAdapter;

public class FirstFragment extends Fragment {
    private RecyclerView listOfPeopleView;
    private PeopleAdapter peopleAdapter;

    private EditText personEditText;
    private TextView resultTextView;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initRecyclerView(view);
//        loadPeople();

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

        personEditText = view.findViewById(R.id.personNameEditText);
        resultTextView = view.findViewById(R.id.resultTextView);
    }

    private void addPerson() {
        String name = personEditText.getText().toString();

        if (!name.isEmpty()) {
            peopleAdapter.addItem(name);
        }

        personEditText.setText("");
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
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        display.getSize(size);
        params.height = size.y / 2;
        listOfPeopleView.setLayoutParams(params);

        listOfPeopleView.setHasFixedSize(true);
        listOfPeopleView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        listOfPeopleView.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL));

        peopleAdapter = new PeopleAdapter();
        listOfPeopleView.setAdapter(peopleAdapter);
    }

//    private void loadPeople() {
//        Collection<String> people = getPeople();
//        peopleAdapter.setItems(people);
//    }
//
//    private Collection<String> getPeople() {
//        return Arrays.asList(
//                "Leona Geralette Esqua van de Velde",
//                "Reienne de Rose",
//                "Chrystal Jasmine Allen",
//                "Julicse de Rose",
//                "Geralt of Rivia",
//                "Alex Berrows",
//                "Levi",
//                "Kek",
//                "Pek"
//        );
//    }
}