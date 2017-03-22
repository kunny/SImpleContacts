package com.androidhuman.example.simplecontacts;

import com.androidhuman.example.simplecontacts.model.Person;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.PersonHolder> {

    private List<Person> people = null;

    @Override
    public PersonHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PersonHolder(parent);
    }

    @Override
    public void onBindViewHolder(PersonHolder holder, int position) {
        Person person = people.get(position);

        holder.name.setText(person.getName());
        holder.address.setText(
                !TextUtils.isEmpty(person.getAddress())
                        ? person.getAddress() : "(No address)");
    }

    @Override
    public long getItemId(int position) {
        return people.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return null != people ? people.size() : 0;
    }

    void setPeople(@Nullable List<Person> people) {
        this.people = people;
    }

    class PersonHolder extends RecyclerView.ViewHolder {

        TextView name;

        TextView address;

        PersonHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_person, parent, false));

            name = (TextView) itemView.findViewById(R.id.tv_item_person_name);
            address = (TextView) itemView.findViewById(R.id.tv_item_person_address);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: Start an activity to edit an item.
                }
            });
        }
    }

}
