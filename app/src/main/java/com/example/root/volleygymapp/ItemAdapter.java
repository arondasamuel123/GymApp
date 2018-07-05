package com.example.root.volleygymapp;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private Context ctx;
    private List<Instructor> instructorList;




    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.custom_layout, null);
        return new ItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Instructor instructor = instructorList.get(position);

        Glide.with(ctx)
                .load("http://gympapp.000webhostapp.com/"+instructor.getImage())
                .into(holder.imageView);
        holder.tv_name.setText(instructor.getName());
        holder.tv_email.setText(instructor.getEmail());
        holder.tv_phonenumber.setText(instructor.getPhonenumber());
        holder.tv_gender.setText(instructor.getGender());

    }

    @Override
    public int getItemCount() {
        if (instructorList == null)
            android.util.Log.e("my response", "null list");
        else
            android.util.Log.e("my_array:","getItemCount: list DATA-----" + instructorList.size());

      //**Here I'm getting Null Pointer exception**
        return instructorList == null ? 0 : instructorList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name, tv_email,tv_gender,tv_phonenumber;
        ImageView imageView;
        public ItemViewHolder(View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.tv_name);
            tv_email = itemView.findViewById(R.id.tv_email);
            tv_gender = itemView.findViewById(R.id.tv_gender);
            tv_phonenumber = itemView.findViewById(R.id.tv_phonenumber);
            imageView = itemView.findViewById(R.id.imageView);

        }

    }
    public ItemAdapter(Context ctx, List<Instructor>instructorList){
        this.ctx= ctx;
        this.instructorList = instructorList;
    }
}
