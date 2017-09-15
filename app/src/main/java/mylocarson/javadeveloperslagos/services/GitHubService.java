package mylocarson.javadeveloperslagos.services;

import mylocarson.javadeveloperslagos.models.GitHubProjects;
import mylocarson.javadeveloperslagos.models.GitHubResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by user on 15/09/2017.
 */

public interface GitHubService {
    @GET("/search/users?q=repos:>0+location:Lagos")
    Call<GitHubResponse> listUsers();

    @GET("/users/{username}/repos")
    Call<GitHubProjects[]> findRepos(@Path("username") String username);

}
