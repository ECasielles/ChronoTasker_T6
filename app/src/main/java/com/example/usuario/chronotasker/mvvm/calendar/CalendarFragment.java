package com.example.usuario.chronotasker.mvvm.calendar;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.usuario.chronotasker.R;
import com.example.usuario.chronotasker.databinding.FragmentCalendarBinding;
import com.example.usuario.chronotasker.mvvm.base.BaseFragment;
import com.example.usuario.chronotasker.mvvm.base.OnFragmentActionListener;
import com.squareup.timessquare.CalendarPickerView;

import org.joda.time.DateTime;

import java.util.Date;

public class CalendarFragment extends BaseFragment<FragmentCalendarBinding, CalendarViewModel>
        implements CalendarNavigator, OnFragmentActionListener {

    public static String TAG = CalendarFragment.class.getSimpleName();


    public static CalendarFragment getInstance() {
        return new CalendarFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_calendar, container, false);

        mBinding.setLifecycleOwner(this);
        mBinding.setViewModel(mViewModel);

        Date startDate = new DateTime().dayOfMonth().withMinimumValue().toDate();
        Date endDate = new DateTime().dayOfMonth().withMaximumValue().toDate();
        mBinding.calendarView.init(startDate, endDate).withSelectedDate(new Date()).inMode(CalendarPickerView.SelectionMode.RANGE);

        mBinding.executePendingBindings();

        return mBinding.getRoot();
    }


    @Override
    public void onStartDateSelected(DateTime startDate) {

    }

    @Override
    public void onEndDateSelected(DateTime endDate) {

    }

    @Override
    public void onDateRangeSelected(DateTime startDate, DateTime endDate) {

    }


    @Override
    public CalendarViewModel makeViewModel() {
        return new CalendarViewModel();
    }


    @Override
    public String getFragmentTag() {
        return TAG;
    }


    @Override
    public String getViewModelTag() {
        return CalendarViewModel.TAG;
    }

}
