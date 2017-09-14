package com.framgia.fdms.screen.meetingroom.listmeetingroom;

import android.app.Dialog;
import android.app.DialogFragment;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import com.framgia.fdms.R;
import com.framgia.fdms.data.model.MeetingRoom;
import com.framgia.fdms.databinding.DialogConfirmMeetingRoomBinding;

import static com.framgia.fdms.utils.Constant.BundleConstant.BUNDLE_ACTION_CALLBACK;
import static com.framgia.fdms.utils.Constant.BundleConstant.BUNDLE_MEETING_ROOM;
import static com.framgia.fdms.utils.Constant.BundleConstant.BUNDLE_TITLE;

/**
 * Created by Vinh on 14/09/2017.
 */

public class MeetingRoomDialog extends DialogFragment implements MeetingRoomDialogContract {
    private ObservableField<String> mMessageError = new ObservableField<>();
    private MeetingRoom mMeetingRoom, mTempMeetingRoom = new MeetingRoom();
    private ObservableField<String> mTitle = new ObservableField<>();
    private MeetingRoomDialogContract.ActionCallback mActionCallback;

    public static MeetingRoomDialog newInstant(MeetingRoom meetingRoom, String title,
        MeetingRoomDialogContract.ActionCallback callback) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_MEETING_ROOM, meetingRoom);
        bundle.putString(BUNDLE_TITLE, title);
        bundle.putParcelable(BUNDLE_ACTION_CALLBACK, callback);
        MeetingRoomDialog meetingRoomDialog = new MeetingRoomDialog();
        meetingRoomDialog.setArguments(bundle);
        return meetingRoomDialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        mMeetingRoom = bundle.getParcelable(BUNDLE_MEETING_ROOM);
        mActionCallback = bundle.getParcelable(BUNDLE_ACTION_CALLBACK);
        mTempMeetingRoom.setName(mMeetingRoom.getName());
        mTempMeetingRoom.setDescription(mMeetingRoom.getDescription());
        mTempMeetingRoom.setId(mMeetingRoom.getId());
        mTitle.set(bundle.getString(BUNDLE_TITLE));
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        DialogConfirmMeetingRoomBinding binding =
            DataBindingUtil.inflate(LayoutInflater.from(getActivity()),
                R.layout.dialog_confirm_meeting_room, null, false);
        binding.setMeetingroom(mTempMeetingRoom);
        binding.setDialog(this);
        builder.setView(binding.getRoot());
        return builder.create();
    }

    @Override
    public void onCancelClick() {
        dismiss();
    }

    @Override
    public void onSubmitClick() {
        //TODO: Add, Edit MeetingRoom
    }

    public ObservableField<String> getMessageError() {
        return mMessageError;
    }

    public void setMessageError(ObservableField<String> messageError) {
        mMessageError = messageError;
    }

    public ObservableField<String> getTitle() {
        return mTitle;
    }

    public void setTitle(ObservableField<String> title) {
        mTitle = title;
    }
}