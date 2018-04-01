package com.passin.pmvp.base.delegate;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date : 2018/3/18 21:47
 * </pre>
 */

public interface ActivityDelegate {

    String ACTIVITY_DELEGATE = "activity_delegate";

    void onCreate(@Nullable Bundle savedInstanceState);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onSaveInstanceState(Bundle outState);

    void onDestroy();

}
