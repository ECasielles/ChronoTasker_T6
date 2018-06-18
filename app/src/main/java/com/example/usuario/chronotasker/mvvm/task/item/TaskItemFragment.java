package com.example.usuario.chronotasker.mvvm.task.item;

import android.app.DatePickerDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.example.usuario.chronotasker.R;
import com.example.usuario.chronotasker.data.model.Task;
import com.example.usuario.chronotasker.databinding.FragmentTaskItemBinding;
import com.example.usuario.chronotasker.mvvm.base.BaseFragment;
import com.example.usuario.chronotasker.mvvm.home.HomeActivity;

import org.joda.time.DateTime;

import java.util.Objects;


public class TaskItemFragment extends BaseFragment<FragmentTaskItemBinding, TaskItemViewModel>
        implements TaskItemNavigator {

    //CONSTANTES
    public static final String TAG = TaskItemFragment.class.getSimpleName();


    public static TaskItemFragment getInstance(Bundle bundle) {
        TaskItemFragment taskViewFragment = new TaskItemFragment();
        if(bundle != null)
            taskViewFragment.setArguments(bundle);
        return taskViewFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_task_item, container, false);

        mBinding.executePendingBindings();

        if(getArguments() != null) {
            Bundle bundle = getArguments();
            Task task = bundle.getParcelable(Task.TAG);
            mViewModel.setTask(task);
        }

        mBinding.setViewModel(mViewModel);

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mParent = (ViewGroup) getView();
    }


    @Override
    public DateTime onStartDateClick() {
        DateTime dateTime = new DateTime();
        new DatePickerDialog(
                Objects.requireNonNull(getContext()),
                R.style.ThemeOverlay_AppCompat_Dialog,
                this::onStartDateSet,
                dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth()
        ).show();
        return dateTime;
    }


    @Override
    public DateTime onEndDateClick() {
        DateTime dateTime = new DateTime();
        new DatePickerDialog(
                Objects.requireNonNull(getContext()),
                R.style.ThemeOverlay_AppCompat_Dialog,
                this::onEndDateSet,
                dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth()
        ).show();
        return dateTime;
    }


    private void onStartDateSet(DatePicker datePicker, int year, int month, int day) {
        mBinding.btnStartDate.setText(String.format("%d/%d/%d", day, month + 1, year));
    }

    private void onEndDateSet(DatePicker datePicker, int year, int month, int day) {
        mBinding.btnEndDate.setText(String.format("%s/%d/%d", day, month + 1, year));
    }

    @Override
    public void onTaskSaved() {
        ((HomeActivity)mFragmentEventHandler).onBackPressed();
        ((HomeActivity)mFragmentEventHandler).refreshCollection();
    }


    @Override
    public void onDateError() {
        Snackbar.make(mParent, R.string.error_date_range, Snackbar.LENGTH_SHORT).show();
    }


    @Override
    public void onEmptyFieldError() {
        Snackbar.make(mParent, R.string.error_title_empty, Snackbar.LENGTH_SHORT).show();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mViewModel.reset();
    }

    @Override
    public TaskItemViewModel makeViewModel() {
        return new TaskItemViewModel();
    }


    @Override
    public String getFragmentTag() {
        return TAG;
    }


    @Override
    public String getViewModelTag() {
        return TaskItemViewModel.TAG;
    }


}
