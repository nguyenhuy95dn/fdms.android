package com.framgia.fdms.data.source.api.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by MyPC on 18/05/2017.
 */

public class RequestCreatorRequest extends BaseRequest {
    @SerializedName("request[title]")
    private String mTitle;
    @SerializedName("request[description]")
    private String mDescription;
    @SerializedName("request[for_user_id]")
    private int mRequestFor;
    @SerializedName("request[assignee_id]")
    private int mAssignee;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public int getRequestFor() {
        return mRequestFor;
    }

    public void setRequestFor(int requestFor) {
        mRequestFor = requestFor;
    }

    public int getAssignee() {
        return mAssignee;
    }

    public void setAssignee(int assignee) {
        mAssignee = assignee;
    }
}
