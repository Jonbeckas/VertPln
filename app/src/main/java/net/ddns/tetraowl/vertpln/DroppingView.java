package net.ddns.tetraowl.vertpln;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class DroppingView extends RecyclerView.Adapter {

    private List<VertObject> mDataset;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_HEADER = 3;
    private static final int TYPE_LOADING = 0;

    public DroppingView(List<VertObject> myDataset) {
        this.mDataset = myDataset;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int typ ) {
        if (typ == TYPE_HEADER ) {
            View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.info_card, viewGroup, false);
            HeaderHolder vh = new HeaderHolder(inflate);
            return vh;
        } else if (typ == TYPE_LOADING) {
            View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.waiting_card, viewGroup, false);
            LoadingHolder vh = new LoadingHolder(inflate);
            return vh;
        } else {
            View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.plancards, viewGroup, false);
            Holder vh = new Holder(inflate);
            return vh;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof Holder) {
            Holder tmp = (Holder) holder;
            tmp.hour.setText(mDataset.get(position).getStunde());
            tmp.teacher.setText(mDataset.get(position).getLehrer());
            tmp.vertreten.setText(mDataset.get(position).getWer());
            tmp.room.setText(mDataset.get(position).getRaum());
            tmp.lesson.setText(mDataset.get(position).getFach());
            tmp.art.setText(mDataset.get(position).getArt());
            tmp.other.setText(mDataset.get(position).getBemerkung());
        } else if (holder instanceof HeaderHolder) {
            ((HeaderHolder) holder).date.setText(this.mDataset.get(position).getDate());
        } else if (holder instanceof LoadingHolder) {

        }

    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (this.mDataset.get(position).isLoading()) {
            return TYPE_LOADING;
        } else if (this.mDataset.get(position).getDate()!=null) {
                return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }
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

    private class HeaderHolder extends RecyclerView.ViewHolder {
        public final TextView date;
        public HeaderHolder(@NonNull View itemView) {
            super(itemView);
            this.date =itemView.findViewById(R.id.date);
        }
    }

    private class LoadingHolder extends RecyclerView.ViewHolder {

        public LoadingHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
