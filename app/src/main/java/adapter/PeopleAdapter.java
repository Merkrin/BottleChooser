package adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.merkrin.bottlechooser.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.PersonViewHolder> {

    private List<String> peopleList = new ArrayList<>();

    private static Random random = new Random();
    private int checkedPosition = -1;

    public void setItems(Collection<String> people) {
        peopleList.addAll(people);
        notifyDataSetChanged();
    }

    public List<String> getPeopleList(){
        return peopleList;
    }

    public void addItem(String personName) {
        peopleList.add(personName);
        notifyDataSetChanged();
    }

    public void clearItems() {
        peopleList.clear();
        checkedPosition = -1;

        notifyDataSetChanged();
    }

    public void deleteSelected(int indexSelected) {
        if (indexSelected != -1) {
            peopleList.remove(indexSelected);
            checkedPosition = -1;

            notifyDataSetChanged();
        }
    }

    public String randomPair() {
        if(peopleList.size() < 2)
            return "No pair can be made!";

        List<String> peopleCopy = new ArrayList<>(peopleList);
        int index = random.nextInt(peopleCopy.size());

        StringBuilder result = new StringBuilder(peopleCopy.get(index));
        peopleCopy.remove(index);

        result.append(" -> ");

        index = random.nextInt(peopleCopy.size());
        result.append(peopleCopy.get(index));

        return result.toString();
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.person_item_view, parent, false);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position) {
        holder.bind(peopleList.get(position));
    }

    @Override
    public int getItemCount() {
        return peopleList.size();
    }

    class PersonViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private ImageView checkedImageView;

        PersonViewHolder(View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.person_name_text_view);
            checkedImageView = itemView.findViewById(R.id.checkedImageView);
        }

        void bind(CharSequence person) {
            if (checkedPosition == -1)
                checkedImageView.setVisibility(View.GONE);
            else {
                if (checkedPosition == getAdapterPosition())
                    checkedImageView.setVisibility(View.VISIBLE);
                else
                    checkedImageView.setVisibility(View.GONE);
            }

            nameTextView.setText(person);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkedImageView.setVisibility(View.VISIBLE);

                    if (checkedPosition != getAdapterPosition()) {
                        notifyItemChanged(checkedPosition);
                        checkedPosition = getAdapterPosition();
                    }
                }
            });
        }
    }

    public int getSelected() {
        return checkedPosition;
    }
}
