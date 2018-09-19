package ${modulePackageName};

import com.passin.pmvp.di.scope.PageScope;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;


/**
 * 用完记得删除
 */
public class PleaseCopyToProvider {

   <#if needActivity == true>
    @PageScope
    @ContributesAndroidInjector(modules = {${pageName}Module.class})
    ${pageName}Activity contribute${pageName}Activity();
   </#if>

   
   <#if needFragment == true>

    @PageScope
    @ContributesAndroidInjector(modules = {${pageName}Module.class})
    ${pageName}Fragment contribute${pageName}Fragment();
	
   </#if>
	
	
}