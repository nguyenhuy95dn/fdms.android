package com.framgia.fdms.screen.devicecreation;

import android.text.TextUtils;
import com.framgia.fdms.data.model.Device;
import com.framgia.fdms.data.source.DeviceRepository;
import com.framgia.fdms.data.source.api.error.BaseException;
import com.framgia.fdms.data.source.api.error.RequestError;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Listens to user actions from the UI ({@link CreateDeviceActivity}), retrieves the data and
 * updates
 * the UI as required.
 */
final class CreateDevicePresenter implements CreateDeviceContract.Presenter {
    private final CreateDeviceContract.ViewModel mViewModel;
    private CompositeDisposable mCompositeSubscription;
    private DeviceRepository mDeviceRepository;

    public CreateDevicePresenter(CreateDeviceContract.ViewModel viewModel,
        DeviceRepository deviceRepository) {
        mViewModel = viewModel;
        mDeviceRepository = deviceRepository;
        mCompositeSubscription = new CompositeDisposable();
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
        mCompositeSubscription.clear();
    }

    @Override
    public void registerDevice(Device device) {
        if (!validateDataInput(device)) {
            return;
        }
        Disposable subscription = mDeviceRepository.registerdevice(device)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe(new Consumer<Disposable>() {
                @Override
                public void accept(Disposable disposable) throws Exception {
                    mViewModel.showProgressbar();
                }
            })
            .subscribe(new Consumer<Device>() {
                @Override
                public void accept(Device device) throws Exception {
                    mViewModel.onRegisterSuccess();
                }
            }, new RequestError() {
                @Override
                public void onRequestError(BaseException error) {
                    mViewModel.hideProgressbar();
                    mViewModel.onLoadError(error.getMessage());
                }
            }, new Action() {
                @Override
                public void run() throws Exception {
                    mViewModel.showProgressbar();
                }
            });

        mCompositeSubscription.add(subscription);
    }

    @Override
    public void updateDevice(final Device localDevice) {
        if (!validateDataEditDevice(localDevice)) {
            return;
        }

        Disposable disposable = mDeviceRepository.updateDevice(localDevice)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe(new Consumer<Disposable>() {
                @Override
                public void accept(Disposable disposable) throws Exception {
                    mViewModel.setProgressBar(VISIBLE);
                }
            })
            .subscribe(new Consumer<Device>() {
                @Override
                public void accept(Device device) throws Exception {
                    localDevice.cloneDevice(device);
                    mViewModel.onUpdateSuccess(localDevice);
                }
            }, new RequestError() {
                @Override
                public void onRequestError(BaseException error) {
                    mViewModel.onUpdateError();
                }
            }, new Action() {
                @Override
                public void run() throws Exception {
                    mViewModel.setProgressBar(GONE);
                }
            });
        mCompositeSubscription.add(disposable);
    }

    @Override
    public void getDeviceCode(int deviceCategoryId, int branchId) {
        Disposable subscription = mDeviceRepository.getDeviceCode(deviceCategoryId, branchId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe(new Consumer<Disposable>() {
                @Override
                public void accept(Disposable disposable) throws Exception {
                    mViewModel.showProgressbar();
                }
            })
            .subscribe(new Consumer<Device>() {
                @Override
                public void accept(Device device) throws Exception {
                    if (device != null && device.getDeviceCode() != null) {
                        mViewModel.onGetDeviceCodeSuccess(device.getDeviceCode());
                    }
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    mViewModel.hideProgressbar();
                    mViewModel.onLoadError(throwable.getMessage());
                }
            }, new Action() {
                @Override
                public void run() {
                    mViewModel.hideProgressbar();
                }
            });
        mCompositeSubscription.add(subscription);
    }

    @Override
    public boolean validateDataInput(Device device) {
        boolean isValid = true;
        if (device.getDeviceCategoryId() <= 0) {
            isValid = false;
            mViewModel.onInputCategoryError();
        }
        if (device.getBoughtDate() == null) {
            isValid = false;
            mViewModel.onInputBoughtDateError();
        }
        if (TextUtils.isEmpty(device.getModelNumber())) {
            isValid = false;
            mViewModel.onInputModellNumberError();
        }
        if (TextUtils.isEmpty(device.getProductionName())) {
            isValid = false;
            mViewModel.onInputProductionNameError();
        }
        if (TextUtils.isEmpty(device.getSerialNumber())) {
            isValid = false;
            mViewModel.onInputSerialNumberError();
        }
        if (TextUtils.isEmpty(device.getOriginalPrice())) {
            isValid = false;
            mViewModel.onInputOriginalPriceError();
        }
        return isValid;
    }

    @Override
    public boolean validateDataEditDevice(Device device) {
        boolean isValid = true;
        if (TextUtils.isEmpty(device.getProductionName())) {
            mViewModel.onInputProductionNameError();
            isValid = false;
        }
        return isValid;
    }
}
