package mylocarson.javadeveloperslagos.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mylocarson.javadeveloperslagos.R;
import mylocarson.javadeveloperslagos.misc.CircleTransform;

/**
 * Created by user on 15/09/2017.
 */

public class MyListAdapter extends BaseAdapter {

    private Context context;
    private List<String[]> values;

    public MyListAdapter(Context context, List<String[]> values) {
        this.context = context;
        this.values = values;
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public Object getItem(int position) {
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.user_item, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        holder.name.setText(values.get(position)[0]);
        Picasso.with(context)
                .load(values.get(position)[1])
                .resize(150, 150)
                .transform(new CircleTransform())
//                .placeholder(R.drawable.person)
//                .error(R.drawable.person)
                .into(holder.userImage);

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.username)
        TextView name;
        @BindView(R.id.userimage)
        ImageView userImage;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

