package com.travisit.travisitbusiness;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;

import com.travisit.travisitbusiness.databinding.FragmentBusinessPaymantsListBinding;
import com.travisit.travisitbusiness.model.PaymentItem;
import com.travisit.travisitbusiness.vvm.AppActivity;
import com.travisit.travisitbusiness.vvm.adapter.PaymentAdapter;
import com.travisit.travisitbusiness.vvm.observer.IOnBackPressed;

import java.util.ArrayList;

public class BusinessPaymantsListFragment extends Fragment implements IOnBackPressed {

    FragmentBusinessPaymantsListBinding binding;
    public BusinessPaymantsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentBusinessPaymantsListBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppActivity)getActivity()).setOnBackPressedListener(this::onBackPressed);
        ((AppActivity)getActivity()).changeBottomNavVisibility(View.GONE,true);
        setupRV();
        userHandler();
    }
    private void setupRV(){
        ArrayList<PaymentItem>arr=new ArrayList<>();
        arr.add(new PaymentItem());
        arr.add(new PaymentItem());
        arr.add(new PaymentItem());

        ArrayList<PaymentItem>arr2=new ArrayList<>();
        arr2.add(new PaymentItem());
        arr2.add(new PaymentItem());
        arr2.add(new PaymentItem());
        arr2.add(new PaymentItem());
        arr2.add(new PaymentItem());
        arr2.add(new PaymentItem());
        arr2.add(new PaymentItem());
        arr2.add(new PaymentItem());
        arr2.add(new PaymentItem());
        arr2.add(new PaymentItem());
        PaymentAdapter adapter1=new PaymentAdapter(arr2,getActivity());
        binding.paymentBookingsRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.paymentBookingsRV.setAdapter(adapter1);
        PaymentAdapter adapter2=new PaymentAdapter(arr2,getActivity());
        binding.paymentReceivedRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.paymentReceivedRV.setAdapter(adapter2);
    }
    int backClicked=0;
    public int getScreenHeight() {
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }
    private void setRVHeight(){
//        Booking RV
        if (binding.paymentBookingsRV.getAdapter().getItemCount()>=3){
            binding.paymentBookingsRV.getLayoutParams().height=getScreenHeight() / 3;
        }else if(binding.paymentBookingsRV.getAdapter().getItemCount()==0){
            binding.paymentBookingsSeeMoreTv.setVisibility(View.GONE);
        } else {
            binding.paymentBookingsRV.getLayoutParams().height=ViewGroup.LayoutParams.WRAP_CONTENT;
        }
//        Receive RV
        if (binding.paymentReceivedRV.getAdapter().getItemCount()>=3){
            binding.paymentReceivedRV.getLayoutParams().height=getScreenHeight() / 3;
        }else if(binding.paymentReceivedRV.getAdapter().getItemCount()==0){
            binding.paymentReceivedSeeMoreTv.setVisibility(View.GONE);
        }else {
            binding.paymentReceivedRV.getLayoutParams().height=ViewGroup.LayoutParams.WRAP_CONTENT;
        }
    }
    private void userHandler(){
        binding.businessPaymentScrollView.setScrollingEnabled(true);
        setRVHeight();
        binding.paymentBookingsRV.setLayoutFrozen(true);
        binding.paymentReceivedRV.setLayoutFrozen(true);
        binding.paymentBookingsSeeMoreTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backClicked=1;
                Animation animEnter = AnimationUtils.loadAnimation(getContext(), R.anim.slide_from_out_left_to_center);
                binding.getRoot().setAnimation(animEnter);
                new CountDownTimer(100, 1) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }
                    @Override
                    public void onFinish() {
                        setScroll(false);
                        binding.paymentReceivedRV.setVisibility(View.GONE);
                        binding.paymentBookingsSeeMoreTv.setVisibility(View.GONE);
                        binding.paymentReceivedSeeMoreTv.setVisibility(View.GONE);
                        binding.paymentReceivedTitle.setVisibility(View.GONE);
                        binding.paymentBookingsRV.setLayoutFrozen(false);
                        binding.paymentReceivedRV.setLayoutFrozen(false);
                        binding.paymentBookingsRV.getLayoutParams().height=ViewGroup.LayoutParams.WRAP_CONTENT;
                    }
                }.start();

            }
        });
        binding.paymentReceivedSeeMoreTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animEnter = AnimationUtils.loadAnimation(getContext(), R.anim.slide_from_out_left_to_center);
                binding.getRoot().setAnimation(animEnter);
                new CountDownTimer(100, 1) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        setScroll(false);
                        binding.paymentBookingsSeeMoreTv.setVisibility(View.GONE);
                        binding.paymentReceivedSeeMoreTv.setVisibility(View.GONE);
                        binding.bookingTitle.setVisibility(View.GONE);
                        binding.paymentBookingsRV.setVisibility(View.GONE);
                        binding.paymentBookingsRV.setLayoutFrozen(false);
                        binding.paymentReceivedRV.setLayoutFrozen(false);
                        binding.paymentReceivedRV.getLayoutParams().height=ViewGroup.LayoutParams.WRAP_CONTENT;
                        backClicked=1;
                    }
                }.start();

            }
        });
        binding.paymentIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AppActivity)getActivity()).onBackPressed();
            }
        });
    }
    View child=null;
    ViewGroup parent=null;
    private void setScroll(boolean isScroll){
        if (isScroll){
            parent.removeView(child);
            binding.businessPaymentScrollView.addView(child);
            parent.addView(binding.businessPaymentScrollView,parent.indexOfChild(binding.businessPaymentScrollView));
        }else {
            child = binding.businessPaymentScrollView.getChildAt(0);// since scrollView can only have one direct child
            parent = (ViewGroup) binding.businessPaymentScrollView.getParent();
            binding.businessPaymentScrollView.removeView(child); // remove child from scrollview
            parent.addView(child,parent.indexOfChild(binding.businessPaymentScrollView));// add scroll child at the position of scrollview
            parent.removeView(binding.businessPaymentScrollView);
        }

//        binding.businessPaymentScrollView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return isScroll;
//            }
//        });
    }
    @Override
    public void onBackPressed() {
        if (backClicked==1){
            Animation animEnter = AnimationUtils.loadAnimation(getContext(), R.anim.slide_from_out_right_to_center);
            binding.getRoot().setAnimation(animEnter);
            new CountDownTimer(100,1){
                @Override
                public void onTick(long millisUntilFinished) {

                }
                @Override
                public void onFinish() {
                    backClicked=0;
                    setScroll(true);
                    binding.paymentReceivedRV.setVisibility(View.VISIBLE);
                    binding.paymentBookingsSeeMoreTv.setVisibility(View.VISIBLE);
                    binding.paymentReceivedSeeMoreTv.setVisibility(View.VISIBLE);
                    binding.paymentReceivedTitle.setVisibility(View.VISIBLE);
                    binding.bookingTitle.setVisibility(View.VISIBLE);
                    binding.paymentBookingsRV.setVisibility(View.VISIBLE);
                    binding.paymentBookingsRV.setLayoutFrozen(true);
                    binding.paymentReceivedRV.setLayoutFrozen(true);
                    setRVHeight();
                }
            }.start();

        }else{
            ((AppActivity)getActivity()).setOnBackPressedListener(null);
            ((AppActivity)getActivity()).onBackPressed();
        }
    }
}