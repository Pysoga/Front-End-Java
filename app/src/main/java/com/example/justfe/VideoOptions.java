package com.example.justfe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class VideoOptions extends Fragment {

    //    Class instance
    IOptions io;
    QualityOptions qualityOptions;
    AudioOptions audioOptions;
    CaptionOptions captionOptions;
    SpeedOptions speedOptions;

    //    Instance for on click
    LinearLayout optionsQuality, optionsAudio, optionsSpeed, optionsCaptions;

    //    Method for interface
    void setIOptions(IOptions io) {
        this.io = io;
    }
//    Set fragment interface

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.video_options, container, false);

//        Create instance
        optionsQuality = (LinearLayout) view.findViewById(R.id.ll_quality);
        optionsAudio = (LinearLayout) view.findViewById(R.id.ll_audio);
        optionsCaptions = (LinearLayout) view.findViewById(R.id.ll_captions);
        optionsSpeed = (LinearLayout) view.findViewById(R.id.ll_speed);

//        Create class instance
        qualityOptions = new QualityOptions();
        audioOptions = new AudioOptions();
        captionOptions = new CaptionOptions();
        speedOptions = new SpeedOptions();

//        On click change fragment
        if (io != null) {
            optionsQuality.setOnClickListener((View v) -> {
                io.runFragment(qualityOptions).setCustomAnimations(R.anim.slide_left_enter, R.anim.slide_left_exit)
                        .addToBackStack(null).commit();
            });

            optionsAudio.setOnClickListener((View v) -> {
                io.runFragment(audioOptions).setCustomAnimations(R.anim.slide_left_enter, R.anim.slide_left_exit)
                        .addToBackStack(null).commit();
            });

            optionsCaptions.setOnClickListener((View v) -> {
                io.runFragment(captionOptions).setCustomAnimations(R.anim.slide_left_enter, R.anim.slide_left_exit)
                        .addToBackStack(null).commit();
            });

            optionsSpeed.setOnClickListener((View v) -> {
                io.runFragment(speedOptions).setCustomAnimations(R.anim.slide_left_enter, R.anim.slide_left_exit)
                        .addToBackStack(null).commit();
            });
        }
        return view;
    }
}
