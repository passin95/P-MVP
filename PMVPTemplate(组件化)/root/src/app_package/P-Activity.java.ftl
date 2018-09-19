package ${ativityPackageName};

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.afollestad.materialdialogs.MaterialDialog;
<#if needIView == true>
import com.passin.pmvp.util.PmvpUtils;
</#if>
import com.passin.pmvp.base.BaseActivity;
import javax.inject.Inject;





/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: ${.now?string("yyyy/MM/dd")}
 * </pre>
 */
public class ${pageName}Activity extends BaseActivity<#if needIView == true> implements ${pageName}View </#if>{

    <#if needPresenter == true>
	@Inject
    ${pageName}Presenter mPresenter;
	</#if>
	
	private MaterialDialog mDialog;

	
	public static void startActivity(Context context) {
        Intent intent = new Intent(context, ${pageName}Activity.class);
        context.startActivity(intent);
    }

	
    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.${activityLayoutName};
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

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
        finish();
    }
	
	</#if>

	

}
