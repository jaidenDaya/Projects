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
import com.example.assignment06.databinding.FragmentEditUserBinding;


public class EditUserFragment extends Fragment {

    private static final String ARG_PARAM_USER = "ARG_PARAM_USER";
    private User mUser;
    String role;
    int selectedID;
    public EditUserFragment() {
        // Required empty public constructor
    }

    public static EditUserFragment newInstance(User user) {
        EditUserFragment fragment = new EditUserFragment();
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.enterName.setText(mUser.getName());
        binding.enterEmail.setText(mUser.getEmail());
        if(mUser.getRole().equals("Student")){
            binding.studentButton.setChecked(true);
        } else if (mUser.getRole().equals("Employee")) {
            binding.employeeButton.setChecked(true);
        } else {
            binding.otherButton.setChecked(true);
        }

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

        binding.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancelEdit();
            }
        });

        binding.submitButton.setOnClickListener(new View.OnClickListener() {
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
                    mListener.gotoProfile(user);
                }
            }
        });
    }

    FragmentEditUserBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEditUserBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    EditUserFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (EditUserFragmentListener) context;
    }

    interface EditUserFragmentListener{
        void gotoProfile(User user);
        void cancelEdit();
    }

}