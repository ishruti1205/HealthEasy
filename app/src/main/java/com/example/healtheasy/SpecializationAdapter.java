package com.example.healtheasy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class SpecializationAdapter extends RecyclerView.Adapter<SpecializationAdapter.ViewHolder> {

    private List<Specialization> specializationList;
    private OnItemClickListener listener;

    public SpecializationAdapter(List<Specialization> specializationList) {
        this.specializationList = specializationList;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.specialization_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Specialization specialization = specializationList.get(position);
        holder.bind(specialization);
    }

    @Override
    public int getItemCount() {
        return specializationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView specializationImg;
        private TextView specializationName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            specializationImg = itemView.findViewById(R.id.specializationImg);
            specializationName = itemView.findViewById(R.id.specializationName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }

        public void bind(Specialization specialization) {
            specializationImg.setImageResource(specialization.getIconResId());
            specializationName.setText(specialization.getName());
        }
    }
}
