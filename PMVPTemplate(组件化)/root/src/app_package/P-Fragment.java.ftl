package ${fragmentPackageName};

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.afollestad.materialdialogs.MaterialDialog;

<#if needIView == true>

import com.passin.pmvp.util.PmvpUtils;
</#if>
import javax.inject.Inject;

import com.passin.pmvp.base.BaseFragment;



/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: ${.now?string("yyyy/MM/dd")}
 * </pre>
 */
public class ${pageName}Fragment extends BaseFragment<#if needIView == true> implements ${pageName}View </#if>{

    @BindView(R2.id.toolbar_title)
    TextView mTvToolbarTitle;

	<#if needPresenter == true>
	@Inject
    ${pageName}Presenter mPresenter;
	</#if>
	
	private MaterialDialog mDialog;

    public static ${pageName}Fragment newInstance() {
        ${pageName}Fragment fragment = new ${pageName}Fragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.${fragmentLayoutName}, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mTvToolbarTitle.setText("");
    }

	<#if needIView == true>

    @Override
    public void showLoading() {
        mDialog = DialogUtils.displayProgreesDialog(this, ",请稍候...", false);
    }

    @Override
    public void hideLoading() {
        DialogUtils.dismissDialog(mDialog);
    }

    @Override
    public void showMessage(@NonNull String message) {
        PmvpUtils.snackbarText(message);
    }

	@Override
    public void killMyself() {
        pop();
    }

	</#if>
}
