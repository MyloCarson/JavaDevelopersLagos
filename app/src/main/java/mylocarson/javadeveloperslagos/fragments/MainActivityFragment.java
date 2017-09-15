package mylocarson.javadeveloperslagos.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.rey.material.widget.ProgressView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mylocarson.javadeveloperslagos.R;
import mylocarson.javadeveloperslagos.activities.DetailsActivity;
import mylocarson.javadeveloperslagos.adapters.MyListAdapter;
import mylocarson.javadeveloperslagos.models.GitHubList;
import mylocarson.javadeveloperslagos.models.GitHubResponse;
import mylocarson.javadeveloperslagos.services.GitHubApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class MainActivityFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;




    @BindView(R.id.list)
    ListView users;

    @BindView(R.id.view_loading)
    ProgressView progressBar;


    public MainActivityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainActivityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainActivityFragment newInstance(String param1, String param2) {
        MainActivityFragment fragment = new MainActivityFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_main_activity, container, false);
        ButterKnife.bind(this, view);

        Call<GitHubResponse> responseCall = new GitHubApi().gitHubService().listUsers();
        responseCall.enqueue(new Callback<GitHubResponse>() {
            @Override
            public void onResponse(Call<GitHubResponse> call, Response<GitHubResponse> response) {
//                Log.i("LagosItemCount",response.body().getTotal_count());
                try{
                    final List<String[]> userList = new ArrayList<String[]>();
                    for(GitHubList listing : response.body().getItems()){
                        Log.i("Result", listing.getLogin());
                        String[] item = new String[4];
                        item[0] = listing.getLogin();
                        item[1] = listing.getAvatar_url();
                        item[2] = listing.getHtml_url();
                        userList.add(item);
                    }
                    Log.e("Users Count", userList.size() +"");
//                    title.setText("Page loaded");
                    MyListAdapter myListAdapter = new MyListAdapter(getContext(),userList);
                    users.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {

                            Intent intent = new Intent(getContext(), DetailsActivity.class);
                            intent.putExtra("user", userList.get(pos));
                            startActivity(intent);
                        }
                    });
                    users.setAdapter(myListAdapter);
                    progressBar.setVisibility(View.GONE);
                }catch (Exception e){
                    e.printStackTrace();
//                    Log.i("LagosItemCount",e.toString());
                    Snackbar.make(view, "An error occured while fetching users, please try again later.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }

            @Override
            public void onFailure(Call<GitHubResponse> call, Throwable t) {
                Log.e("Error",t.toString());
                Snackbar.make(view, "An error occured while fetching users, please try again later.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        return view;
    }


}
