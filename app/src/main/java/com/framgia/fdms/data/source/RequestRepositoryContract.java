package com.framgia.fdms.data.source;

import com.framgia.fdms.data.model.AssignmentRequest;
import com.framgia.fdms.data.model.Dashboard;
import com.framgia.fdms.data.model.Request;
import com.framgia.fdms.data.model.Respone;
import com.framgia.fdms.data.model.Status;
import com.framgia.fdms.data.source.api.request.RequestCreatorRequest;
import io.reactivex.Observable;
import java.util.List;

/**
 * Created by beepi on 11/05/2017.
 */

public interface RequestRepositoryContract {
    /**
     * Use on remote request to get list request
     */
    Observable<List<Request>> getRequests(int requestType, int requestStatusId, int relativeId,
        int page, int perPage);

    Observable<List<Status>> getStatus();

    Observable<List<Dashboard>> getDashboardRequest();

    Observable<Request> registerRequest(RequestCreatorRequest request);

    Observable<List<Request>> getTopRequest(int topRequest);

    Observable<Respone<Request>> updateActionRequest(int requestId, int statusId);

    Observable<Respone<Request>> updateRequest(Request request);

    Observable<Request> getRequest(int requetsId);

    Observable<Request> registerAssignment(AssignmentRequest request);
}
