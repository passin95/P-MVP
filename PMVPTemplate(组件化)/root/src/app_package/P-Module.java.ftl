package ${modulePackageName};

import android.arch.lifecycle.LifecycleOwner;
import dagger.Module;
import dagger.Provides;
import com.passin.pmvp.di.scope.PageScope;





@Module
public class ${pageName}Module {

   <#if needActivity == true>
   
	<#if needPresenter == true>
    @PageScope
    @Provides
    ${pageName}View provide${pageName}View(${pageName}Activity activity){
        return activity;
    }
	</#if>


    @PageScope
    @Provides
    LifecycleOwner provideLifecycleOwner(${pageName}Activity activity){
        return activity;
    }
   </#if>

   
   <#if needFragment == true>

   	<#if needPresenter == true>
    @PageScope
    @Provides
    ${pageName}View provide${pageName}View(${pageName}Fragment fragment){
        return fragment;
    }
	</#if>

    @PageScope
    @Provides
    LifecycleOwner provideLifecycleOwner(${pageName}Fragment fragment){
        return fragment;
    }
   </#if>
	
	
}