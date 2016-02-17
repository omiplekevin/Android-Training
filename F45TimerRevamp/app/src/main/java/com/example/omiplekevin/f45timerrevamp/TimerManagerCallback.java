package com.example.omiplekevin.f45timerrevamp;

/**
 * Created by OMIPLEKEVIN on 11/09/2015.
 *
 */
public interface TimerManagerCallback {

    void tick(long baseMilli, long elapsedMilli);
    void stop();
    void pause();
    void resume();

}
