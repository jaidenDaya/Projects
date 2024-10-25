package com.example.assignment06;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.assignment06.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {
    private static final String ARG_PARAM_USER = "ARG_PARAM_USER";
    private User mUser;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public void setUser(User user){
        this.mUser = user;
    }
    public static ProfileFragment newInstance(User user) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_USER, user);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUser = (User) getArguments().getSerializable(ARG_PARAM_USER);
        }
    }

    FragmentProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Profile");
        binding.profileName.setText(mUser.getName());
        binding.profileEmail.setText(mUser.getEmail());
        binding.profileRole.setText(mUser.getRole());

        binding.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User(mUser.getName(), mUser.getEmail(), mUser.getRole());
                mListener.gotoEditUser(user);
            }
        });
    }

   ProfileFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (ProfileFragmentListener) context;
    }

    interface ProfileFragmentListener{
        void gotoEditUser(User user);
    }


}