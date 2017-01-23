package co.samsao.reporter.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * POJO representing a github public repository.
 */
public class Repository {

    @SerializedName("full_name")
    private String fullName;

    @SerializedName("updated_at")
    private Date updatedTime;

    private String language;

    @SerializedName("default_branch")
    private String defaultBranch;

    @SerializedName("forks_count")
    private int forksCount;

    private String description;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName_p) {
        fullName = fullName_p;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime_p) {
        updatedTime = updatedTime_p;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language_p) {
        language = language_p;
    }

    public String getDefaultBranch() {
        return defaultBranch;
    }

    public void setDefaultBranch(String defaultBranch_p) {
        defaultBranch = defaultBranch_p;
    }

    public int getForksCount() {
        return forksCount;
    }

    public void setForksCount(int forksCount_p) {
        forksCount = forksCount_p;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description_p) {
        description = description_p;
    }
}
