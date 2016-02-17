package com.example.omiplekevin.latencyresolverf45;

public interface MatrixScreenConnection{

	abstract void setTimerActive(boolean isActive);

	abstract void sendBroadcast(String message);

	abstract void displayBroadcast(String message);
}
