package com.travisit.travisitbusiness.vvm.observer;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.travisit.travisitbusiness.databinding.FragmentFilterBinding;
import com.travisit.travisitbusiness.model.Category;
import com.travisit.travisitbusiness.vvm.AppActivity;
import com.travisit.travisitbusiness.vvm.adapter.FilterItemAdapter;
import com.travisit.travisitbusiness.vvm.vm.FilterVM;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FilterFragment extends Fragment {
    FragmentFilterBinding binding;
    View view;
    FilterVM vm;
    public FilterFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentFilterBinding.inflate(inflater, container, false);
        view=binding.getRoot();
        vm = ViewModelProviders.of(this).get(FilterVM.class);
        handleUserInteractions();
        return view;
    }
    private void handleUserInteractions(){
        ((AppActivity)getActivity()).changeBottomNavVisibility(View.INVISIBLE,false);
  //CheckBox Handling
        //Status Checkbox
        binding.allStatusCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    binding.onGoingStatusCb.setChecked(true);
                    binding.finishedStatusCb.setChecked(true);
                    binding.allStatusCb.setChecked(true);
                }else if(!b&&binding.onGoingStatusCb.isChecked()&&binding.finishedStatusCb.isChecked()){
                    binding.onGoingStatusCb.setChecked(false);
                    binding.finishedStatusCb.setChecked(false);
                }
            }
        });
        binding.onGoingStatusCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!b&& binding.allStatusCb.isChecked()){
                    binding.allStatusCb.setChecked(false);
                }else if(b&&binding.finishedStatusCb.isChecked()){
                    binding.allStatusCb.setChecked(true);
                }
            }
        });
        binding.finishedStatusCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!b&& binding.allStatusCb.isChecked()){
                    binding.allStatusCb.setChecked(false);
                } else if(b&&binding.onGoingStatusCb.isChecked()){
                    binding.allStatusCb.setChecked(true);
                }
            }
        });
        //Rate checkbox
        binding.topRatingStatusCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    binding.leastRatingCb.setChecked(false);
                }
            }
        });
        binding.leastRatingCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    binding.topRatingStatusCb.setChecked(false);
                }
            }
        });
        //get Categories
        vm.getCategories();
        vm.categoriesMutableLiveData.observe(getActivity(), new Observer<ArrayList<Category>>() {
            @Override
            public void onChanged(ArrayList<Category> categories) {
                initRecyclerView(categories);
            }
        });
        onClickAction();

    }
    private void onClickAction(){
        //backPressed
        binding.fFilterIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AppActivity)getActivity()).onBackPressed();
            }
        });
        //reset
        binding.fResetTvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });
        binding.fFilterMtbtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Save and make search operation
            }
        });
    }
    private void reset(){
        binding.allStatusCb.setChecked(false);
        binding.topRatingStatusCb.setChecked(false);
        binding.finishedStatusCb.setChecked(false);
        binding.topRatingStatusCb.setChecked(false);
        binding.leastRatingCb.setChecked(false);
        vm.getCategories();
        vm.categoriesMutableLiveData.observe(getActivity(), new Observer<ArrayList<Category>>() {
            @Override
            public void onChanged(ArrayList<Category> categories) {
                initRecyclerView(categories);
            }
        });
    }
    private void initRecyclerView(ArrayList<Category> categories) {
//        ArrayList<Category>cat=new ArrayList<>();
//        cat.add(new Category(0,""));
//        cat.add(new Category(0,""));
//        cat.add(new Category(0,""));
//        cat.add(new Category(0,""));
//        cat.add(new Category(0,""));
//        cat.add(new Category(0,""));
//        vm.categoriesMutableLiveData.setValue(cat);
        Collections.sort(categories, Category.categoryComparator);
        binding.branchesRv.setAdapter(new FilterItemAdapter(
                categories,
                getActivity(),
                new FilterItemAdapter.FilterSelectionPropagator() {
                    @Override
                    public void chipSelected(Category category) {
                        if (category.isSelected()) {
                            vm.selectedCategories.add(category.getId());
                        } else {
                            vm.selectedCategories.remove(category.getId());
                        }
                    }
                })
        );
        binding.branchesRv.setLayoutManager(new GridLayoutManager(
                getActivity(),
                3,
                RecyclerView.HORIZONTAL,
                false
        ));
    }
}