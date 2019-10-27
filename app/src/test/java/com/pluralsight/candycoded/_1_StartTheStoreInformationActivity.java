package com.pluralsight.candycoded;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@PrepareForTest({AppCompatActivity.class, MainActivity.class, Intent.class, InfoActivity.class})
@RunWith(PowerMockRunner.class)
public class _1_StartTheStoreInformationActivity {
    private static MainActivity activity;

    private static boolean onOptionsItemSelected_result = true;
    private static boolean called_Intent = false;
    private static boolean called_Intent_correctly = false;
    private static boolean called_startActivity = false;

    // Mockito setup
    @BeforeClass
    public static void setup() throws Exception {
        // Spy on a MainActivity instance.
        activity = PowerMockito.spy(new MainActivity());
        // Create a fake Bundle to pass in.
        Bundle bundle = Mockito.mock(Bundle.class);
        // Create a spy Intent to return from new Intent().
        Intent intent = PowerMockito.spy(new Intent(activity, InfoActivity.class));

        try {
            // Do not allow super.onCreate() to be called, as it throws errors before the user's code.
            PowerMockito.suppress(PowerMockito.methodsDeclaredIn(AppCompatActivity.class));

            // Return a mocked Intent from the call to its constructor.
            PowerMockito.whenNew(Intent.class).withAnyArguments().thenReturn(intent);

            // We expect calling onCreate() to throw an Exception due to our mocking. Ignore it.
            try {
                activity.onCreate(bundle);
            } catch (Exception e) {
                //e.printStackTrace();
            }

            try {
                onOptionsItemSelected_result = activity.onOptionsItemSelected(null);
            } catch (Throwable e) {
                //e.printStackTrace();
            }

            // Check if new Intent() was called with any arguments.
            try {
                PowerMockito.verifyNew(Intent.class, Mockito.atLeastOnce()).withNoArguments();
                called_Intent = true;
            } catch (Throwable e) {
                //e.printStackTrace();
            }

            try {
                PowerMockito.verifyNew(Intent.class, Mockito.atLeastOnce()).withArguments(Mockito.any(MainActivity.class), Mockito.any(Class.class));
                called_Intent = true;
            } catch (Throwable e) {
                //e.printStackTrace();
            }

            // Check if new Intent() was called with the correct arguments.
            PowerMockito.verifyNew(Intent.class, Mockito.atLeastOnce()).withArguments(Mockito.eq(activity), Mockito.eq(InfoActivity.class));
            called_Intent_correctly = true;

            // Check if startActivity() was called with the correct argument.
            Mockito.verify(activity).startActivity(Mockito.eq(intent));
            called_startActivity = true;

        } catch (Throwable e) {
            //e.printStackTrace();
        }
    }

    @Test
    public void mainactivity_onoptionitemselected_return_super() throws Exception {
        override_mainactivity_onoptionitemselected();
        assertFalse("onOptionsItemSelected() does not return call to super.", onOptionsItemSelected_result);
    }

    @Test
    public void create_intent_infoactivity() throws Exception {
        override_mainactivity_onoptionitemselected();
        assertTrue("The Intent was not created.", called_Intent);
        assertTrue("The Intent was created but with the wrong parameters. @intent-infoactivity", called_Intent_correctly);
    }

    @Test
    public void startactivity_infoactivity() throws Exception {
        override_mainactivity_onoptionitemselected();
        assertTrue("The method startActivity() was not called.", called_startActivity);
    }

    @Test
    public void override_mainactivity_onoptionitemselected() throws Exception {
        // Determine if the method OnOptionsItemSelected() is implemented in MainActivity
        // or just in the Base class
        Class<?> myClass = null;

        try {
            myClass = MainActivity.class
                    .getMethod("onOptionsItemSelected", MenuItem.class)
                    .getDeclaringClass();
        } catch (NoSuchMethodException e) {
            //e.printStackTrace();
        }

        assertEquals("onOptionsItemSelected() method doesn't exist in MainActivity class.",
                myClass, MainActivity.class);

        assertEquals("onOptionsItemSelected() method doesn't exist in MainActivity class.",
                myClass, MainActivity.class);
    }
}
