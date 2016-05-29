package xyz.oguzcelik.hipoandroidchallenge;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import xyz.oguzcelik.hipoandroidchallenge.model.Datum;
import xyz.oguzcelik.hipoandroidchallenge.model.Images;

/**
 * Created by Cynapsis on 5/20/2016.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private List<Datum> images;
    private Context context;

    public ImageAdapter(Context context) {
        this.context = context;
    }

    public void setDataList(List<Datum> images) {
        this.images = images;
        this.notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Images image = images.get(position).getImages();
        Picasso.with(context).load(image.getLowResolutionUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public Images getSelectedImage(int position) { // maybe we won't need this
        return images.get(position).getImages();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
        }

        public ImageView getImageView() {
            return imageView;
        }
    }

}
