package com.example.android.background.sync;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class WorkManager extends Worker {



    public WorkManager(Context context, WorkerParameters workerParams) {
        super(context, workerParams);
    }

    /**
     * The entry point to your Job. Implementations should offload work to another thread of
     * execution as soon as possible.
     *
     * This is called by the Job Dispatcher to tell us we should start our job. Keep in mind this
     * method is run on the application's main thread, so we need to offload work to a background
     * thread.
     *
     * @return whether there is more work remaining.
    **/

    @NonNull
    @Override
    public Result doWork() {
        Context context = WorkManager.super.getApplicationContext();
        ReminderTasks.executeTasks(context, ReminderTasks.ACTION_CHARGING_REMINDER);
        return Result.success();
    }

    @Override
    public void onStopped() {
        super.onStopped();

    }
}
