package mylocarson.javadeveloperslagos.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user on 15/09/2017.
 */

public class GitHubApi {

    private GitHubService gitHubService;

    public GitHubApi(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        gitHubService = retrofit.create(GitHubService.class);
    }

    public GitHubService gitHubService(){
        return gitHubService;
    }

}
