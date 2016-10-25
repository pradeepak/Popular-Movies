package com.nanodegree.lkn573.popularmovies.Movies;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nanodegree.lkn573.popularmovies.Core.CoreFragment;
import com.nanodegree.lkn573.popularmovies.R;

public class NetworkErrorFragment extends CoreFragment implements View.OnClickListener{

    Button retryButton;

    // TODO: Rename and change types of parameters

    private onRetryListener mListener;

    public NetworkErrorFragment() {
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
        return inflater.inflate(R.layout.fragment_network_error, container, false);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
         retryButton = (Button) getActivity().findViewById(R.id.retryButton);
        retryButton.setOnClickListener(this);
        super.onActivityCreated(savedInstanceState);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (onRetryListener) context;
        } else {
            throw new RuntimeException(context.toString()
                + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        mListener.retryNetworkClicked();

    }

    public interface onRetryListener {

        // TODO: Update argument type and name
        void retryNetworkClicked();
    }
}
