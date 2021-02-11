package com.travisit.travisitbusiness.vvm.vm;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.travisit.travisitbusiness.data.Client;
import com.travisit.travisitbusiness.model.Category;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class FilterVM extends ViewModel {
    public ArrayList<Integer> selectedCategories = new ArrayList<>();
    public MutableLiveData<ArrayList<Category>> categoriesMutableLiveData = new MutableLiveData<>();
    CompositeDisposable compositeDisposable;
    public void getCategories() {
        Observable<JsonObject> observable = Client.getINSTANCE().getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(observable.subscribe(o -> {
            ArrayList<Category> categories = parseCategories(o);
            categoriesMutableLiveData.setValue(categories);
        }, e -> Log.d("PVMError", e.getMessage())));
    }
    private ArrayList<Category> parseCategories(JsonObject jsonObject) {
        ArrayList<Category> categories = new ArrayList<Category>();
        JsonArray jsonArray = jsonObject.get("category").getAsJsonArray();
        Log.d("businessXXXXXX", jsonArray.toString());

        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject categoryObject = jsonArray.get(i).getAsJsonObject();
            Category category = new Category(
                    categoryObject.get("id").getAsInt(),
                    categoryObject.get("name").getAsString()
            );
            categories.add(category);
        }
        return categories;
    }
}
