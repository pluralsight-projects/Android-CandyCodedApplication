package com.pluralsight.candycoded;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by sarah on 9/28/17.
 */

public class XMLTestHelpers {

    public static class ViewContainer {
        public String id;
        public String onClick;
        public String clickable;

        public ViewContainer(String idProperty, String onClickProperty, String clickableProperty) {
            id = idProperty;
            onClick = onClickProperty;
            clickable = clickableProperty;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (!ViewContainer.class.isAssignableFrom(obj.getClass())) {
                return false;
            }
            final ViewContainer other = (ViewContainer) obj;
            if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
                return false;
            }
            if ((this.onClick == null) ? (other.onClick != null) : !this.onClick.equals(other.onClick)) {
                return false;
            }
            if ((this.clickable == null) ? (other.clickable != null) : !this.clickable.equals(other.clickable)) {
                return false;
            }
            return true;
        }
    }

    public static ArrayList<ViewContainer> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {

        ArrayList<ViewContainer> viewContainers = new ArrayList<ViewContainer>();

        parser.require(XmlPullParser.START_TAG, null, "android.support.constraint.ConstraintLayout");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("TextView")) {
                // TO DO
                String idProperty = parser.getAttributeValue(null, "android:id");
                String onClickProperty = parser.getAttributeValue(null, "android:onClick");
                String clickableProperty = parser.getAttributeValue(null, "android:clickable");

                ViewContainer viewContainer = new ViewContainer(idProperty, onClickProperty, clickableProperty);
                viewContainers.add(viewContainer);
                // This will go to the end of the TextView tag
                parser.next();
            } else {
                skip(parser);
            }
        }

        return viewContainers;
    }

    private static void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
