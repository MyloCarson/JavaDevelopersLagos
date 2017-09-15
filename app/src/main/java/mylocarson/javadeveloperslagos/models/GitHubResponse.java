package mylocarson.javadeveloperslagos.models;

/**
 * Created by user on 15/09/2017.
 */

public class GitHubResponse {
    String total_count;

    public boolean isIncomplete_results() {
        return incomplete_results;
    }

    public void setIncomplete_results(boolean incomplete_results) {
        this.incomplete_results = incomplete_results;
    }

    boolean incomplete_results;
    GitHubList[] items;

    public String getTotal_count() {
        return total_count;
    }

    public void setTotal_count(String total_count) {
        this.total_count = total_count;
    }

    public GitHubList[] getItems() {
        return items;
    }

    public void setItems(GitHubList[] items) {
        this.items = items;
    }

}
