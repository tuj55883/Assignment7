package edu.temple.assignment7;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class ControlFragment extends Fragment {

    Button play;
    Button pause;
    Button stop;

    public ControlFragment() {

    }


    public static ControlFragment newInstance(String param1, String param2) {
        ControlFragment fragment = new ControlFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_audio_controls, container, false);
        // Inflate the layout for this fragment
        play = (Button)view.findViewById(R.id.playButton);
        pause = (Button) view.findViewById(R.id.pauseButton);
        stop = (Button) view.findViewById(R.id.stopButton);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ControlFragment.ControlFragmentInterface) getActivity()).playClicked();
                Log.d("here","here");
            }
        });


        return view;
    }


    interface ControlFragmentInterface{
        public void playClicked() ;
        public void pauseClicked() ;
        public void stopClicked() ;

    }

}
