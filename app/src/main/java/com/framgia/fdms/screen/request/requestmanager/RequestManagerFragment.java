package com.framgia.fdms.screen.request.requestmanager;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.framgia.fdms.R;
import com.framgia.fdms.data.source.RequestRepository;
import com.framgia.fdms.data.source.StatusRepository;
import com.framgia.fdms.data.source.UserRepository;
import com.framgia.fdms.data.source.api.service.FDMSServiceClient;
import com.framgia.fdms.data.source.local.UserLocalDataSource;
import com.framgia.fdms.data.source.local.sharepref.SharePreferenceImp;
import com.framgia.fdms.data.source.remote.RequestRemoteDataSource;
import com.framgia.fdms.data.source.remote.StatusRemoteDataSource;
import com.framgia.fdms.databinding.FragmentRequestManagerBinding;
import com.framgia.fdms.screen.request.BaseRequestFragment;

/**
 * RequestManager Screen.
 */
public class RequestManagerFragment extends BaseRequestFragment {

    private RequestManagerContract.ViewModel mViewModel;

    public static RequestManagerFragment newInstance() {
        return new RequestManagerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new RequestManagerViewModel(this);

        RequestManagerContract.Presenter presenter = new RequestManagerPresenter(mViewModel,
            RequestRepository.getInstant(
                new RequestRemoteDataSource(FDMSServiceClient.getInstance())),
            new UserRepository(new UserLocalDataSource(new SharePreferenceImp(getContext()))));
        mViewModel.setPresenter(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {

        FragmentRequestManagerBinding binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_request_manager, container, false);
        binding.setViewModel((RequestManagerViewModel) mViewModel);
        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mViewModel.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewModel.onStart();
    }

    @Override
    public void onStop() {
        mViewModel.onStop();
        super.onStop();
    }

    @Override
    protected void refreshData() {
        mViewModel.refreshData();
    }
}
