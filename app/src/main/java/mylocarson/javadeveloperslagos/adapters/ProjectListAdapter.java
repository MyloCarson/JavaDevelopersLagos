package mylocarson.javadeveloperslagos.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mylocarson.javadeveloperslagos.R;

/**
 * Created by user on 15/09/2017.
 */

public class ProjectListAdapter extends BaseAdapter {

    private Context context;
    private List<String[]> values;

    public ProjectListAdapter(Context context, List<String[]> values) {
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

            view = inflater.inflate(R.layout.project_list_item, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        holder.name.setText(values.get(position)[0]);
        holder.desc.setText(values.get(position)[2]);


        return view;
    }

    static class ViewHolder {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.desc)
        TextView desc;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

