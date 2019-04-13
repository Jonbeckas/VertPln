package net.ddns.tetraowl.vertpln;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class DroppingView extends RecyclerView.Adapter<DroppingView.Holder> {

    private List<VertObject> mDataset;

    public DroppingView(List<VertObject> myDataset) {
        this.mDataset = myDataset;
    }

    @NonNull
    @Override
    public DroppingView.Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.plancards, viewGroup, false);
        Holder vh = new Holder(inflate);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull DroppingView.Holder holder, int i) {
        holder.hour.setText(mDataset.get(i).getStunde());
        holder.teacher.setText(mDataset.get(i).getLehrer());
        holder.vertreten.setText(mDataset.get(i).getWer());
        holder.room.setText(mDataset.get(i).getRaum());
        holder.lesson.setText(mDataset.get(i).getFach());
        holder.art.setText(mDataset.get(i).getArt());
        holder.other.setText(mDataset.get(i).getBemerkung());
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        public TextView hour;
        public TextView teacher;
        public TextView vertreten;
        public TextView room;
        public TextView lesson;
        public TextView art;
        public TextView other;
        public Holder(@NonNull View view) {
            super(view);
            this.hour = view.findViewById(R.id.hour);
            this.teacher = view.findViewById(R.id.teacher);
            this.vertreten = view.findViewById(R.id.vertreten);
            this.room = view.findViewById(R.id.room);
            this.lesson = view.findViewById(R.id.lesson);
            this.art = view.findViewById(R.id.art);
            this.other = view.findViewById(R.id.other);

        }
    }
}
