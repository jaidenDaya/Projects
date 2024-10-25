package com.example.assignment06;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.assignment06.databinding.FragmentCreateUserBinding;


public class CreateUserFragment extends Fragment {
    String role = "";
    int selectedID;
    public CreateUserFragment() {
        // Required empty public constructor
    }

    FragmentCreateUserBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCreateUserBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Create User");

        binding.group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.studentButton){
                    role = "Student";
                    selectedID = checkedId;
                } else if (checkedId == R.id.employeeButton){
                    role = "Employee";
                    selectedID = checkedId;
                } else if(checkedId == R.id.otherButton){
                    role = "Other";
                    selectedID = checkedId;
                }
            }
        });

        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.enterName.getText().toString();
                String email = binding.enterEmail.getText().toString();
                String roleSelected = role;

                if (name.isEmpty()) {
                    Toast.makeText(getActivity(), "Enter valid name", Toast.LENGTH_SHORT).show();
                } else if (email.isEmpty()) {
                    Toast.makeText(getActivity(), "Enter valid email", Toast.LENGTH_SHORT).show();
                } else if (role.equals("")) {
                    Toast.makeText(getActivity(), "Please Choose a role", Toast.LENGTH_SHORT).show();
                } else {
                    User user = new User(name, email, roleSelected);
                    mListener.goToProfile(user);
                }
            }
        });
    }

    CreateUserFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (CreateUserFragmentListener) context;
    }

    interface CreateUserFragmentListener{
        void goToProfile(User user);
    }
}