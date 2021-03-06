package com.framgia.fdms.data.source;

import com.framgia.fdms.data.model.Device;
import com.framgia.fdms.data.model.Status;
import io.reactivex.Observable;
import java.util.List;

/**
 * Created by Hoang Van Nha on 5/23/2017.
 * <></>
 */

public interface DeviceReturnDataSource {
    interface LocalDataSource {

    }

    interface RemoteDataSource {
        Observable<List<Status>> getBorrowers();

        Observable<List<Device>> getDevicesOfBorrower();

        Observable<String> returnDevice(List<Integer> listDeviceId);

        Observable<List<Device>> getListDevicesOfBorrower(int userId);
    }
}
