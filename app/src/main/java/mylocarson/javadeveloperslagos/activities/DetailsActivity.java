package mylocarson.javadeveloperslagos.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.rey.material.widget.Button;
import com.rey.material.widget.ProgressView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mylocarson.javadeveloperslagos.R;
import mylocarson.javadeveloperslagos.adapters.ProjectListAdapter;
import mylocarson.javadeveloperslagos.models.GitHubProjects;
import mylocarson.javadeveloperslagos.services.GitHubApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {

    Context context;
    @BindView(R.id.username)
    TextView username;

    @BindView(R.id.url)
    TextView url;

    @BindView(R.id.profile)
    ImageView userimage;

    @BindView(R.id.button_id)
    Button share_button;

    @BindView(R.id.listProject)
    ListView projectListView;

    @BindView(R.id.view_loading)
    ProgressView view_loading;

    String user = "";
    String profileurl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        context = this;
        ButterKnife.bind(this);

        String[] item = getIntent().getStringArrayExtra("user");

        fixViews(item);

    }

    void fixViews(String[] values){
        Picasso.with(context)
                .load(values[1])
                .resize(200, 200)
//                .placeholder(R.drawable.person)
//                .error(R.drawable.person)
                .into(userimage);
        user = values[0];
        profileurl = values[2];
        getSupportActionBar().setTitle(user);
        username.setText(user);
        url.setText(profileurl);
        showProjectList(user);
    }

    void showProjectList(String username){
        Call<GitHubProjects[]> responseCall = new GitHubApi().gitHubService().findRepos(username);
        responseCall.enqueue(new Callback<GitHubProjects[]>() {
            @Override
            public void onResponse(Call<GitHubProjects[]> call, Response<GitHubProjects[]> response) {
                Log.i("projects ",response.body().length + " ");
                try{
                    final List<String[]> projectList = new ArrayList<String[]>();
                    for(GitHubProjects project : response.body()){
                        Log.i("Result", project.getFull_name());
                        String[] item = new String[3];
                        item[0] = project.getName();
                        item[1] = project.getLanguage();
                        item[2] = project.getDescription();
                        projectList.add(item);
                    }

                    ProjectListAdapter myListAdapter = new ProjectListAdapter(context,projectList);

                    projectListView.setAdapter(myListAdapter);
                    view_loading.setVisibility(View.GONE);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<GitHubProjects[]> call, Throwable t) {
//                Log.e("Error",t.toString());
                Snackbar.make(projectListView, "Oops! We couldn't get this user's project list, please try again later.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.url)
    public void goToURL(){
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(profileurl));
        startActivity(i);
    }

    @OnClick(R.id.button_id)
    public void shareProfile() {
        // TODO submit data to server...
        String shareBody = "Check out this awesome developer @"+user+", "+profileurl;
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Lagos Developer");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "How would you like to share?"));
    }

}
