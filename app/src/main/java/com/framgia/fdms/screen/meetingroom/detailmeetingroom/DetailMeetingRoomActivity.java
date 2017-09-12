package com.framgia.fdms.screen.meetingroom.detailmeetingroom;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.framgia.fdms.R;
import com.framgia.fdms.data.model.MeetingRoom;
import com.framgia.fdms.data.source.DeviceRepository;
import com.framgia.fdms.data.source.api.service.FDMSServiceClient;
import com.framgia.fdms.data.source.remote.DeviceRemoteDataSource;
import com.framgia.fdms.databinding.ActivityDetailMeetingRoomBinding;
import com.framgia.fdms.utils.Constant;

/**
 * DetailMeetingRoom Screen.
 */
public class DetailMeetingRoomActivity extends AppCompatActivity {

    private DetailMeetingRoomContract.ViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MeetingRoom meetingRoom = this.getIntent()
                .getExtras()
                .getParcelable(Constant.BundleConstant.BUNDLE_MEETING_ROOM);
        mViewModel = new DetailMeetingRoomViewModel(this, meetingRoom);

        DetailMeetingRoomContract.Presenter presenter = new DetailMeetingRoomPresenter(mViewModel,
                new DeviceRepository(new DeviceRemoteDataSource(FDMSServiceClient.getInstance())));
        mViewModel.setPresenter(presenter);

        ActivityDetailMeetingRoomBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_detail_meeting_room);
        binding.setViewModel((DetailMeetingRoomViewModel) mViewModel);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewModel.onStart();
    }

    @Override
    protected void onStop() {
        mViewModel.onStop();
        super.onStop();
    }
}