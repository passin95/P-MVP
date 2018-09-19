<?xml version="1.0" encoding="utf-8"?>
<android.support.design.widget.CoordinatorLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    xmlns:app="http://schemas.android.com/apk/res-auto">

    <include layout="@layout/toolbar_base"/>
    
    <ScrollView
        android:id="@+id/scrollView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layout_behavior="@string/appbar_scrolling_view_behavior">
        
        <android.support.constraint.ConstraintLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent">
            
        </android.support.constraint.ConstraintLayout>
        
    </ScrollView>

</android.support.design.widget.CoordinatorLayout>