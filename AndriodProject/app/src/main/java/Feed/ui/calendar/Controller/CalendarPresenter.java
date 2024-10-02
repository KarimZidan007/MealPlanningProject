package Feed.ui.calendar.Controller;

import androidx.lifecycle.LiveData;

import java.util.List;

import Feed.Controllers.MealsIPresenter;
import Model.MealDate;
import Repository.MealsRepository;

public class CalendarPresenter implements MealsIPresenter.getPlannedMeals, MealsIPresenter.addPlannedMeal {

    private final MealsRepository repository;

    public CalendarPresenter(MealsRepository repository) {
        this.repository = repository;
    }

    @Override
    public void insertPlannedMeal(MealDate meal) {
        repository.insertPlannedMeal(meal);
    }

    @Override
    public LiveData<List<MealDate>> getPlannedMeals(String date) {
        return repository.getPlannedMeals(date);
    }

    @Override
    public void deletePlannedMeal(MealDate meal) {
        repository.delPlannedMeal(meal);
    }
}