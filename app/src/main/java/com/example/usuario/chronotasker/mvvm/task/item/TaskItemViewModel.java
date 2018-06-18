package com.example.usuario.chronotasker.mvvm.task.item;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.usuario.chronotasker.R;
import com.example.usuario.chronotasker.data.App;
import com.example.usuario.chronotasker.data.model.Category;
import com.example.usuario.chronotasker.data.model.Task;
import com.example.usuario.chronotasker.data.repository.TaskRepository;
import com.example.usuario.chronotasker.mvvm.base.navigator.INavigator;
import com.example.usuario.chronotasker.mvvm.base.navigator.NavigatorViewModel;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


public class TaskItemViewModel extends NavigatorViewModel {

    public static final String TAG = TaskItemViewModel.class.getSimpleName();


    public final MutableLiveData<String> title = new MutableLiveData<>();
    public final MutableLiveData<String> description = new MutableLiveData<>();
    public final MutableLiveData<String> startDateText = new MutableLiveData<>();
    public final MutableLiveData<String> endDateText = new MutableLiveData<>();
    public final MutableLiveData<Boolean> informal = new MutableLiveData<>();
    public final MutableLiveData<Boolean> normal = new MutableLiveData<>();
    public final MutableLiveData<Boolean> important = new MutableLiveData<>();
    public final MutableLiveData<Boolean> urgent = new MutableLiveData<>();

    private final DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");

    private MutableLiveData<DateTime> startDate;
    private MutableLiveData<DateTime> endDate;
    private MutableLiveData<Task> task;

    private TaskItemNavigator mNavigator;

    public TaskItemViewModel() {
        reset();
    }

    public String getTitle() {
        return title.getValue();
    }
    public String getDescription() {
        return description.getValue();
    }
    public String getStartDateText() {
        return startDateText.getValue();
    }
    public String getEndDateText() {
        return endDateText.getValue();
    }
    public boolean getInformal() {
        return informal.getValue().booleanValue();
    }
    public boolean getNormal() {
        return normal.getValue().booleanValue();
    }
    public boolean getImportant() {
        return important.getValue().booleanValue();
    }
    public boolean getUrgent() {
        return urgent.getValue().booleanValue();
    }
    public void setTitle(String text) {
        title.setValue(text);
    }
    public void setDescription(String text) {
        description.setValue(text);
    }
    public void setInformal(boolean value) {
        informal.setValue(value);
    }
    public void setNormal(boolean value) {
        normal.setValue(value);
    }
    public void setImportant(boolean value) {
        important.setValue(value);
    }
    public void setUrgent(boolean value) {
        urgent.setValue(value);
    }


    @Override
    public void setNavigator(INavigator navigator) {
        mNavigator = (TaskItemNavigator) navigator;
    }


    public boolean addTask() {
        if(isTitleFieldEmpty())
            mNavigator.onEmptyFieldError();
        else if (!isStartDateAfterEndDate() || !isEndDateAfterNow())
            mNavigator.onDateError();
        else {
            Category category = makeCategory();

            if(task == null)
                TaskRepository.getInstance().upsertTask(new Task(getTitle(),
                    R.drawable.ic_check_box, getStartDate(),
                    endDate == null ? getStartDate() : endDate.getValue(),
                    category.getPriority(), getDescription(),
                    null, null, null, null)
                );
            else
                TaskRepository.getInstance().upsertTask(new Task(task.getValue().getId(), getTitle(),
                        R.drawable.ic_check_box, getStartDate(),
                        endDate == null ? getStartDate() : endDate.getValue(),
                        category.getPriority(), getDescription(),
                        null, null, null, null)
                );

            mNavigator.onTaskSaved();
        }
        return true;
    }


    @NonNull
    private Category makeCategory() {
        Category category = new Category();

        if(getInformal())                               category.setFlag(Category.CATEGORY_INFORMAL);
        if(getNormal())                                 category.setFlag(Category.CATEGORY_NORMAL);
        if(getImportant())                              category.setFlag(Category.CATEGORY_IMPORTANT);
        if(getUrgent())                                 category.setFlag(Category.CATEGORY_URGENT);
        if(!getInformal() && !getNormal()
                && !getImportant() && !getUrgent())     category.setFlag(Category.CATEGORY_NORMAL);

        return category;
    }


    private boolean isTitleFieldEmpty() {
        return getTitle() == null || getTitle().isEmpty();
    }
    private boolean isEndDateAfterNow() {
        return endDate == null || endDate.getValue().isBeforeNow();
    }
    private boolean isStartDateAfterEndDate() {
        return endDate == null || endDate.getValue().isBefore(getStartDate());
    }


    private DateTime getEndDate() {
        if(endDate == null) {
            endDate = new MutableLiveData<>();
            endDate.setValue(new DateTime());
        }
        return endDate.getValue();
    }
    private DateTime getStartDate() {
        if(startDate == null) {
            startDate = new MutableLiveData<>();
            startDate.setValue(new DateTime());
        }
        return startDate.getValue();
    }


    public void onStartDateClick() {
        getStartDate();
        startDate.setValue(mNavigator.onStartDateClick());
    }
    public void onEndDateClick() {
        getEndDate();
        endDate.setValue(mNavigator.onEndDateClick());
    }


    public void setTask(Task oldtask) {
        task = new MutableLiveData<>();
        task.setValue(oldtask);

        setInformal(Category.hasFlag(oldtask.getPriority(), Category.CATEGORY_INFORMAL));
        setNormal(Category.hasFlag(oldtask.getPriority(), Category.CATEGORY_NORMAL));
        setImportant(Category.hasFlag(oldtask.getPriority(), Category.CATEGORY_IMPORTANT));
        setUrgent(Category.hasFlag(oldtask.getPriority(), Category.CATEGORY_URGENT));

        setTitle(oldtask.getTitle());
        setDescription(oldtask.getDescription());

        startDate = new MutableLiveData<>();
        endDate = new MutableLiveData<>();

        startDate.setValue(oldtask.getStartDate());
        endDate.setValue(oldtask.getEndDate());

        startDateText.setValue(formatter.print(startDate.getValue()));
        endDateText.setValue(formatter.print(endDate.getValue()));
    }


    @Override
    public void reset() {
        task = null;
        startDate = null;
        endDate = null;

        startDateText.setValue(App.getApp().getResources().getString(R.string.dpk_start_date));
        endDateText.setValue(App.getApp().getResources().getString(R.string.dpk_end_date));

        setTitle("");
        setDescription("");

        informal.setValue(false);
        normal.setValue(false);
        important.setValue(false);
        urgent.setValue(false);
    }


}
