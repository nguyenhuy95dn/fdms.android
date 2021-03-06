package com.framgia.fdms.screen.requestcreation;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import com.framgia.fdms.BR;
import com.framgia.fdms.R;
import com.framgia.fdms.data.model.Request;
import com.framgia.fdms.data.model.Status;
import com.framgia.fdms.data.model.User;
import com.framgia.fdms.data.source.api.request.RequestCreatorRequest;
import com.framgia.fdms.screen.selection.SelectionActivity;
import com.framgia.fdms.screen.selection.SelectionType;
import com.framgia.fdms.utils.Constant;

import static android.app.Activity.RESULT_OK;
import static com.framgia.fdms.screen.selection.SelectionViewModel.BUNDLE_DATA;
import static com.framgia.fdms.utils.Constant.NONE;
import static com.framgia.fdms.utils.Constant.OUT_OF_INDEX;
import static com.framgia.fdms.utils.Constant.RequestConstant.REQUEST_ASSIGNEE;
import static com.framgia.fdms.utils.Constant.RequestConstant.REQUEST_RELATIVE;

/**
 * Exposes the data to be used in the Requestcreation screen.
 */

public class RequestCreationViewModel extends BaseObservable
    implements RequestCreationContract.ViewModel {

    private ArrayAdapter<Status> mAdapterCategory;
    private RequestCreationContract.Presenter mPresenter;
    private AppCompatActivity mActivity;
    private String mRequestTitle;
    private String mRequestDescription;
    private Status mRequestFor;
    private Status mAssignee;
    private RequestCreatorRequest mRequest;

    private Context mContext;
    private String mTitleError;
    private String mDescriptionError;
    private int mProgressBarVisibility = View.GONE;
    private boolean mIsManager;

    public RequestCreationViewModel(AppCompatActivity activity) {
        mActivity = activity;
        mContext = activity.getApplicationContext();
        mRequest = new RequestCreatorRequest();
        mAdapterCategory =
            new ArrayAdapter<>(mActivity, R.layout.support_simple_spinner_dropdown_item);
    }

    @Override
    public void onStart() {
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
    }

    @Override
    public void setPresenter(RequestCreationContract.Presenter presenter) {
        mPresenter = presenter;
        mPresenter.getCurrentUser();
    }

    public AppCompatActivity getActivity() {
        return mActivity;
    }

    @Override
    public void onCreateRequestClick() {
        mPresenter.registerRequest(mRequest);
    }

    @Override
    public void onLoadError(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideProgressbar() {
        setProgressBarVisibility(View.GONE);
    }

    @Override
    public void showProgressbar() {
        setProgressBarVisibility(View.VISIBLE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        Bundle bundle = data.getExtras();
        Status status = bundle.getParcelable(BUNDLE_DATA);
        assert status != null;
        switch (requestCode) {
            case REQUEST_RELATIVE:
                if (status.getId() == OUT_OF_INDEX) {
                    return;
                }
                setRequestFor(status);
                break;
            case REQUEST_ASSIGNEE:
                setAssignee(status);
                break;
            default:
                break;
        }
    }

    @Override
    public void onGetRequestSuccess(Request request) {
        mActivity.setResult(RESULT_OK);
        mActivity.finish();
    }

    @Override
    public void onInputTitleError() {
        mTitleError = mContext.getString(R.string.msg_error_user_name);
        notifyPropertyChanged(BR.titleError);
    }

    @Override
    public void onInputDescriptionError() {
        mDescriptionError = mContext.getString(R.string.msg_error_user_name);
        notifyPropertyChanged(BR.descriptionError);
    }

    @Override
    public void onGetUserSuccess(User user) {
        if (user.getRole().equals(Constant.Role.BO_MANAGER)) {
            setManager(true);
            setRequestFor(new Status(user.getId(), user.getName()));
            setAssignee(new Status(OUT_OF_INDEX, NONE));
        } else {
            setManager(false);
        }
    }

    @Bindable
    public String getRequestTitle() {
        return mRequestTitle;
    }

    public void setRequestTitle(String requestTitle) {
        mRequest.setTitle(requestTitle);
        mRequestTitle = requestTitle;
    }

    @Bindable
    public String getRequestDescription() {
        return mRequestDescription;
    }

    public void setRequestDescription(String requestDescription) {
        mRequest.setDescription(requestDescription);
        mRequestDescription = requestDescription;
    }

    @Bindable
    public Status getRequestFor() {
        return mRequestFor;
    }

    public void setRequestFor(Status requestFor) {
        mRequestFor = requestFor;
        mRequest.setRequestFor(requestFor.getId());
        notifyPropertyChanged(BR.requestFor);
    }

    @Bindable
    public Status getAssignee() {
        return mAssignee;
    }

    public void setAssignee(Status assignee) {
        mAssignee = assignee;
        mRequest.setAssignee(assignee.getId());
        notifyPropertyChanged(BR.assignee);
    }

    @Bindable
    public String getTitleError() {
        return mTitleError;
    }

    @Bindable
    public String getDescriptionError() {
        return mDescriptionError;
    }

    @Bindable
    public int getProgressBarVisibility() {
        return mProgressBarVisibility;
    }

    public void setProgressBarVisibility(int progressBarVisibility) {
        mProgressBarVisibility = progressBarVisibility;
        notifyPropertyChanged(BR.progressBarVisibility);
    }

    @Bindable
    public boolean isManager() {
        return mIsManager;
    }

    private void setManager(boolean manager) {
        mIsManager = manager;
        notifyPropertyChanged(BR.manager);
    }

    public void onClickChooseRequestFor() {
        mActivity.startActivityForResult(
            SelectionActivity.getInstance(mContext, SelectionType.RELATIVE_STAFF),
            REQUEST_RELATIVE);
    }

    public void onClickAssignee() {
        mActivity.startActivityForResult(
            SelectionActivity.getInstance(mContext, SelectionType.ASSIGNEE), REQUEST_ASSIGNEE);
    }
}
