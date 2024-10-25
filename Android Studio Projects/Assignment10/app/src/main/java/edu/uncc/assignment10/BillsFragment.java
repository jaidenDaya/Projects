package edu.uncc.assignment10;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.uncc.assignment10.databinding.FragmentBillsBinding;

public class BillsFragment extends Fragment {
    public BillsFragment() {
        // Required empty public constructor
    }

    String sortAttribute = "Date", sortOrder = "ASC";

    public void setSortItems(String sortAttribute, String sortOrder) {
        this.sortAttribute = sortAttribute;
        this.sortOrder = sortOrder;
    }

    FragmentBillsBinding binding;
    BillsAdapter adapter;
    private ArrayList<Bill> mBills = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBillsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBills.clear();
        mBills.addAll(mListener.getAllBills());

        adapter = new BillsAdapter(getActivity(), mBills);
        binding.listView.setAdapter(adapter);

        binding.textViewSortedBy.setText("Sorted By " + sortAttribute + " (" + sortOrder + ")");

        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bill bill = mBills.get(position);
                mListener.goToBillSummary(bill);
            }
        });

        if (sortAttribute.equals("Date")) {
            if (sortOrder.equals("ASC")) {
                Collections.sort(mBills, new Comparator<Bill>() {
                    @Override
                    public int compare(Bill o1, Bill o2) {
                        return o1.getBillDate().compareTo(o2.getBillDate());
                    }
                });
            } else {
                Collections.sort(mBills, new Comparator<Bill>() {
                    @Override
                    public int compare(Bill o1, Bill o2) {
                        return o2.getBillDate().compareTo(o1.getBillDate());
                    }
                });
            }
        } else if (sortAttribute.equals("Category")) {
            if (sortOrder.equals("ASC")) {
                Collections.sort(mBills, new Comparator<Bill>() {
                    @Override
                    public int compare(Bill o1, Bill o2) {
                        return o1.getCategory().compareTo(o2.getCategory());
                    }
                });
            } else {
                Collections.sort(mBills, new Comparator<Bill>() {
                    @Override
                    public int compare(Bill o1, Bill o2) {
                        return o2.getCategory().compareTo(o1.getCategory());
                    }
                });
            }
        } else if (sortAttribute.equals("Discount")) {
            if (sortOrder.equals("ASC")) {
                Collections.sort(mBills, new Comparator<Bill>() {
                    @Override
                    public int compare(Bill o1, Bill o2) {
                        return Double.compare(o1.getDiscount(), o2.getDiscount());
                    }
                });
            } else {
                Collections.sort(mBills, new Comparator<Bill>() {
                    @Override
                    public int compare(Bill o1, Bill o2) {
                        return Double.compare(o2.getDiscount(), o1.getDiscount());
                    }
                });
            }
        }
        adapter.notifyDataSetChanged();

        binding.buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.clearAllBills();
            }
        });

        binding.buttonNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoCreateBill();
            }
        });

        binding.buttonSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSortSelection();
            }
        });
    }

    class BillsAdapter extends ArrayAdapter<Bill> {

        public BillsAdapter(@NonNull Context context, @NonNull List<Bill> objects) {
            super(context, R.layout.bill_list_item, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if(convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.bill_list_item, parent, false);
            }
            TextView textViewBillCategory = convertView.findViewById(R.id.bill_category);
            TextView textViewBillAmount = convertView.findViewById(R.id.bill_amount);
            TextView textViewBillDiscount = convertView.findViewById(R.id.bill_discount);
            TextView textViewBillTotal = convertView.findViewById(R.id.bill_total);
            TextView textViewBillDate = convertView.findViewById(R.id.bill_date);
            TextView textViewBillName = convertView.findViewById(R.id.bill_name);

            Bill bill = getItem(position);
            textViewBillCategory.setText(bill.getCategory());
            textViewBillAmount.setText(String.valueOf(bill.getAmount()));
            textViewBillDiscount.setText(String.valueOf(bill.getDiscount()));
            textViewBillDate.setText(String.valueOf(bill.getBillDate()));
            textViewBillName.setText(bill.getName());

            double amount = bill.getAmount();
            double discount = bill.getDiscount();
            double total = amount - (amount * discount);
            textViewBillTotal.setText(String.valueOf(total));

            return convertView;
        }
    }

    BillsListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof BillsListener) {
            mListener = (BillsListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement BillsListener");
        }
    }

    interface BillsListener {
        void goToBillSummary(Bill bill);
        ArrayList<Bill> getAllBills();
        void gotoCreateBill();
        void gotoSortSelection();
        void clearAllBills();
    }

}