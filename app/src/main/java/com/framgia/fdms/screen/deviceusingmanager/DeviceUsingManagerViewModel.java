package com.framgia.fdms.screen.deviceusingmanager;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;
import android.view.View;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.framgia.fdms.BR;
import com.framgia.fdms.data.model.DeviceUsingHistory;
import com.framgia.fdms.widget.OnSearchMenuItemClickListener;
import java.util.List;

import static android.view.View.GONE;
import static com.framgia.fdms.utils.Constant.DRAWER_IS_CLOSE;

/**
 * Exposes the data to be used in the DeviceUsing screen.
 */

public class DeviceUsingManagerViewModel extends BaseObservable
    implements DeviceUsingManagerContract.ViewModel, SearchView.OnQueryTextListener,
    FloatingSearchView.OnSearchListener, FloatingSearchView.OnClearSearchActionListener,
    OnSearchMenuItemClickListener, DrawerLayout.DrawerListener {

    private DeviceUsingManagerContract.Presenter mPresenter;
    private DeviceUsingHistoryFilter mFilterModel;
    private String mDrawerStatus = DRAWER_IS_CLOSE;
    private int mEmptyViewVisible = GONE;
    private boolean mIsLoadingMore;
    private int mProgressBarVisibility;

    private DeviceUsingHistoryAdapter mAdapter;

    private RecyclerView.OnScrollListener mScrollListenner = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (dy <= 0) {
                return;
            }
            LinearLayoutManager layoutManager =
                (LinearLayoutManager) recyclerView.getLayoutManager();
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();
            if (!mIsLoadingMore && (visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                setLoadingMore(true);
                mPresenter.loadMoreData();
            }
        }
    };

    public DeviceUsingManagerViewModel() {

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
    public void setPresenter(DeviceUsingManagerContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onGetDeviceUsingHistorySuccess(List<DeviceUsingHistory> deviceUsingHistories) {
        //Do this on DeviceUsingBaseViewModel
    }

    @Override
    public void onGetDeviceUsingHistoryFailed() {
        //Do this on DeviceUsingBaseViewModel
    }

    @Override
    public void onClearFilterClick() {
        // TODO: 9/16/2017  
    }

    @Override
    public void onChooseStatusClick() {
        // TODO: 9/16/2017
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        // TODO: 9/16/2017  
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        // TODO: 9/16/2017  
        return false;
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(View drawerView) {

    }

    @Override
    public void onDrawerClosed(View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

    @Override
    public void onClearSearchClicked() {

    }

    @Override
    public void onSuggestionClicked(SearchSuggestion searchSuggestion) {

    }

    @Override
    public void onSearchAction(String s) {

    }

    @Override
    public void onActionMenuItemSelected(FloatingSearchView searchView, MenuItem item) {

    }

    @Bindable
    public DeviceUsingHistoryFilter getFilterModel() {
        return mFilterModel;
    }

    public void setFilterModel(DeviceUsingHistoryFilter filterModel) {
        mFilterModel = filterModel;
        notifyPropertyChanged(BR.filterModel);
    }

    @Bindable
    public String getDrawerStatus() {
        return mDrawerStatus;
    }

    public void setDrawerStatus(String drawerStatus) {
        mDrawerStatus = drawerStatus;
        notifyPropertyChanged(BR.drawerStatus);
    }

    @Bindable
    public int getEmptyViewVisible() {
        return mEmptyViewVisible;
    }

    public void setEmptyViewVisible(int emptyViewVisible) {
        mEmptyViewVisible = emptyViewVisible;
        notifyPropertyChanged(BR.emptyViewVisible);
    }

    @Bindable
    public DeviceUsingHistoryAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(DeviceUsingHistoryAdapter adapter) {
        mAdapter = adapter;
        notifyPropertyChanged(BR.adapter);
    }

    @Bindable
    public boolean isLoadingMore() {
        return mIsLoadingMore;
    }

    public void setLoadingMore(boolean loadingMore) {
        mIsLoadingMore = loadingMore;
        notifyPropertyChanged(BR.loadingMore);
    }

    @Bindable
    public RecyclerView.OnScrollListener getScrollListenner() {
        return mScrollListenner;
    }

    public void setScrollListenner(RecyclerView.OnScrollListener scrollListenner) {
        mScrollListenner = scrollListenner;
        notifyPropertyChanged(BR.scrollListenner);
    }

    @Bindable
    public int getProgressBarVisibility() {
        return mProgressBarVisibility;
    }

    public void setProgressBarVisibility(int progressBarVisibility) {
        mProgressBarVisibility = progressBarVisibility;
        notifyPropertyChanged(BR.progressBarVisibility);
    }
}
