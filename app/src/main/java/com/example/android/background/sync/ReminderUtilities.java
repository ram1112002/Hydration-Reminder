package com.example.android.background.sync;

import androidx.work.Constraints;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;


import java.util.concurrent.TimeUnit;

public class ReminderUtilities  {

    private static final int REMINDER_INTERVAL_MINUTES = 15;
    private static final int REMINDER_INTERVAL_SECONDS = (int) (TimeUnit.MINUTES.toSeconds(REMINDER_INTERVAL_MINUTES));
    private static final int SYNC_FLEXTIME_SECONDS = REMINDER_INTERVAL_SECONDS;

    private static final String REMINDER_JOB_TAG = "hydration_reminder_tag";

    private static boolean sInitialized;

    synchronized public static void periodRequest(){
        //If the job has already been initialized, return
//        if (sInitialized)return;

        Constraints constraint = new Constraints.Builder()
                .setRequiresCharging(true)
                .build();
        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(WorkManager.class,REMINDER_INTERVAL_SECONDS+SYNC_FLEXTIME_SECONDS, TimeUnit.SECONDS)
                .setInitialDelay(REMINDER_INTERVAL_SECONDS, TimeUnit.SECONDS)
                .setConstraints(constraint)
                .addTag(REMINDER_JOB_TAG)
                .build();

        androidx.work.WorkManager.getInstance().enqueue(periodicWorkRequest);
        sInitialized =true;
    }
    

    synchronized public static void scheduleChargingReminder() {
        //If the job has already been initialized, return
        if (sInitialized) return;


        Constraints constraint = new Constraints.Builder()
                .setRequiresCharging(true)
                .build();


    OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(WorkManager.class)
            .setInitialDelay(10, TimeUnit.SECONDS)
            .setConstraints(constraint)
            .addTag(REMINDER_JOB_TAG)
            .build();

        androidx.work.WorkManager.getInstance().enqueue(oneTimeWorkRequest);
        sInitialized = true;

    }




//        Driver driver = new GooglePlayDriver(context);
//
//        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);
//
//        //Use FirebaseJobDispatcher's newJobBuilder method to build a job which:
//        // - has WaterReminderFirebaseJobService as it's service
//        // - has the tag REMINDER_JOB_TAG
//        // - only triggers if the device is charging
//        // - has the lifetime of the job as forever
//        // - has the job recur
//        // - occurs every 15 minutes with a window of 15 minutes. You can do this using a
//        //   setTrigger, passing in a Trigger.executionWindow
//        // - replaces the current job if it's already running
//        // Finally, you should build the job.
//
//        Job constraintReminderJob = dispatcher.newJobBuilder()
//                /* The Service that will be used to write to preferences */
//                                    .setService(WaterReminderFirebaseJobService.class)
//                /*
//                 * Set the UNIQUE tag used to identify this Job.
//                 */
//                                    .setTag(REMINDER_JOB_TAG)
//                /*
//                 * Network constraints on which this Job should run. In this app, we're using the
//                 * device charging constraint so that the job only executes if the device is
//                 * charging.
//                 *
//                 * In a normal app, it might be a good idea to include a preference for this,
//                 * as different users may have different preferences on when you should be
//                 * syncing your application's data.
//                 */
//                                    .setConstraints(Constraint.DEVICE_CHARGING)
//                /*
//                 * setLifetime sets how long this job should persist. The options are to keep the
//                 * Job "forever" or to have it die the next time the device boots up.
//                 */
//                                    .setLifetime(Lifetime.FOREVER)
//                /*
//                 * We want these reminders to continuously happen, so we tell this Job to recur.
//                 */
//                                    .setRecurring(true)
//                /*
//                 * We want the reminders to happen every 15 minutes or so. The first argument for
//                 * Trigger class's static executionWindow method is the start of the time frame
//                 * when the
//                 * job should be performed. The second argument is the latest point in time at
//                 * which the data should be synced. Please note that this end time is not
//                 * guaranteed, but is more of a guideline for FirebaseJobDispatcher to go off of.
//                 */
//                                    .setTrigger(Trigger.executionWindow(
//                                            REMINDER_INTERVAL_SECONDS,
//                                            REMINDER_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS
//                                    ))
//                /*
//                 * If a Job with the tag with provided already exists, this new job will replace
//                 * the old one.
//                 */
//                                    .setReplaceCurrent(true)
//                /* Once the Job is ready, call the builder's build method to return the Job */
//                                    .build();
//
//        /* Schedule the Job with the dispatcher */
//        dispatcher.schedule(constraintReminderJob);
//        sInitialized= true;
    }

