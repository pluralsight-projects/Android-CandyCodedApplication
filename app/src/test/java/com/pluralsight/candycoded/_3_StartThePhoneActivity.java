package com.pluralsight.candycoded;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@PrepareForTest({AppCompatActivity.class, Intent.class, Uri.class, InfoActivity.class})
@RunWith(PowerMockRunner.class)
public class _3_StartThePhoneActivity {

    public static final String LAYOUT_XML_FILE = "res/layout/activity_info.xml";
    private static InfoActivity infoActivity;
    private static boolean created_intent = false;
    private static boolean set_data = false;
    private static boolean called_uri_parse = false;
    private static boolean called_startActivity_correctly = false;

    // Mockito setup
    @BeforeClass
    public static void setup() throws Exception {
        // Spy on a MainActivity instance.
        infoActivity = PowerMockito.spy(new InfoActivity());
        // Create a fake Bundle to pass in.
        Bundle bundle = mock(Bundle.class);
        Uri mockUri = mock(Uri.class);
        Intent intent = PowerMockito.spy(new Intent(Intent.ACTION_DIAL));

        try {
            // Do not allow super.onCreate() to be called, as it throws errors before the user's code.
            PowerMockito.suppress(PowerMockito.methodsDeclaredIn(AppCompatActivity.class));

            PowerMockito.whenNew(Intent.class).withAnyArguments().thenReturn(intent);

            try {
                infoActivity.onCreate(bundle);
            } catch (Exception e) {
                //e.printStackTrace();
            }

            PowerMockito.mockStatic(Uri.class);
            PowerMockito.when(Uri.class, "parse", "tel:0123456789").thenReturn(mockUri);
            try {
                //infoActivity.createPhoneIntent(null);
                Method myMethod =  InfoActivity.class
                        .getMethod("createPhoneIntent", View.class);
                Object[] param = {null};
                myMethod.invoke(infoActivity, param);
            } catch (Throwable e) {
                //e.printStackTrace();
            }
            PowerMockito.verifyNew(Intent.class, Mockito.atLeastOnce()).
                    withArguments(Mockito.eq(Intent.ACTION_DIAL));
            created_intent = true;

            PowerMockito.verifyStatic(Uri.class);
            Uri.parse("tel:0123456789");
            called_uri_parse = true;

            verify(intent).setData(mockUri);
            set_data = true;

            Mockito.verify(infoActivity).startActivity(Mockito.eq(intent));
            called_startActivity_correctly = true;
        } catch (Throwable e) {
            //e.printStackTrace();
        }
    }

    @Test
    public void create_actiondial_phone_intent() throws Exception {
        createPhoneIntent_Exists();
        assertTrue("The Intent was not created correctly.", created_intent);
    }

    @Test
    public void phone_intent_set_data() throws Exception {
        createPhoneIntent_Exists();
        assertTrue("The Uri for the phone wasn't created.", called_uri_parse);
        assertTrue("The data was not set for the Intent.", set_data);
    }

    @Test
    public void phone_intent_start_activity() throws Exception {
        createPhoneIntent_Exists();
        assertTrue("The method startActivity() was not called.", called_startActivity_correctly);
    }

    @Test
    public void createPhoneIntent_Exists() throws Exception {
        Method myMethod = null;

        try {
            myMethod =  InfoActivity.class
                    .getMethod("createPhoneIntent", View.class);
        } catch (NoSuchMethodException e) {
            //e.printStackTrace();
        }

        assertNotNull("createPhoneIntent() method doesn't exist in InfoActivity class.", myMethod);
    }

    @Test
    public void test_xml() throws Exception {
        ArrayList<XMLTestHelpers.ViewContainer> viewContainers = readLayoutXML(LAYOUT_XML_FILE);
        XMLTestHelpers.ViewContainer addressView =
                new XMLTestHelpers.ViewContainer("@+id/text_view_phone", "createPhoneIntent", "true");
        boolean address_set_correct =  viewContainers.contains(addressView);

        Assert.assertTrue("In activity_info.xml, the TextView text_view_phone does not have " +
                        "the clickable and onClick properties set.",
                address_set_correct);
    }

    public ArrayList<XMLTestHelpers.ViewContainer> readLayoutXML(String layoutFileName) {
        InputStream inputStream = null;

        ArrayList<XMLTestHelpers.ViewContainer> viewContainers = new ArrayList<XMLTestHelpers.ViewContainer>();

        try {
            inputStream = this.getClass().getClassLoader().getResourceAsStream(layoutFileName);
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(inputStream, null);
            parser.nextTag();
            viewContainers = XMLTestHelpers.readFeed(parser);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return viewContainers;
    }
}

