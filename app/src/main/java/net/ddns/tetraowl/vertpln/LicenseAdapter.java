package net.ddns.tetraowl.vertpln;

import android.content.res.Resources;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class LicenseAdapter extends RecyclerView.Adapter<LicenseAdapter.Holder> {
    private final MainActivity mainActivity;

    public LicenseAdapter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.licenceitem, viewGroup, false);
        LicenseAdapter.Holder vh = new LicenseAdapter.Holder(inflate);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        Resources res = this.mainActivity.getResources();
        String[] licencename = res.getStringArray(R.array.licencesname);
        String[] licencetext = res.getStringArray(R.array.licencestext);
        holder.title.setText(licencename[i]);
        holder.licence.setText(licencetext[i]);
    }

    @Override
    public int getItemCount() {
        Resources res = this.mainActivity.getResources();
        String[] licencename = res.getStringArray(R.array.licencesname);
        return licencename.length;
    }

    public class Holder extends RecyclerView.ViewHolder {
        private TextView title;

        public TextView getTitle() {
            return title;
        }

        public TextView getLicence() {
            return licence;
        }

        private TextView licence;
        public Holder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.title);
            this.licence = itemView.findViewById(R.id.licence);
        }
    }
}
