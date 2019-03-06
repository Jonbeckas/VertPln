package net.ddns.tetraowl.vertpln;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DroppingView extends RecyclerView.Adapter<DroppingView.Holder> {

    private String[] mDataset;

    public DroppingView(String[] myDataset) {
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
        holder.textView.setText(mDataset[i]);
    }


    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    public class Holder extends RecyclerView.ViewHolder{
        public TextView textView;
        public Holder(@NonNull View view) {
            super(view);
            this.textView = view.findViewById(R.id.other);

        }
    }
}
