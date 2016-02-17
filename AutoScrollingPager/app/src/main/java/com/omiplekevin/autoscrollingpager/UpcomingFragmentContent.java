package com.omiplekevin.autoscrollingpager;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class UpcomingFragmentContent extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private MainActivity.ContentDetail mParam1;

    private OnFragmentInteractionListener mListener;

    // TODO: Rename and change types and number of parameters
    public static UpcomingFragmentContent newInstance(MainActivity.ContentDetail param1) {
        UpcomingFragmentContent fragment = new UpcomingFragmentContent();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public UpcomingFragmentContent() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = (MainActivity.ContentDetail)getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming_item, container, false);
        TextView date = (TextView) view.findViewById(R.id.eventDate);
        TextView content = (TextView) view.findViewById(R.id.eventContent);
        date.setText(mParam1.date);
        content.setText(mParam1.content);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(String uri);
    }

}
